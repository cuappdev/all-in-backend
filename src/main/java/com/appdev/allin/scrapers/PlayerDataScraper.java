package com.appdev.allin.scrapers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import com.appdev.allin.exceptions.NotFoundException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.appdev.allin.contract.OpposingTeam;
import com.appdev.allin.player.Player;
import com.appdev.allin.player.PlayerService;
import com.appdev.allin.playerData.PlayerData;
import com.appdev.allin.playerData.PlayerDataService;

@Component
public class PlayerDataScraper {
    private static final Logger logger = LoggerFactory.getLogger(PlayerDataScraper.class);

    private final PlayerService playerService;
    private final PlayerDataService playerDataService;
    private static final String BASE_URL = "https://cornellbigred.com";
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36";

    // Set to track processed game URLs to avoid duplicates
    private final Set<String> processedGameUrls = new HashSet<>();

    // Set to track unmatched teams
    private final Set<String> unmatchedTeams = new HashSet<>();

    public PlayerDataScraper(PlayerService playerService, PlayerDataService playerDataService) {
        this.playerService = playerService;
        this.playerDataService = playerDataService;
    }

    public void populate() throws IOException {
        int endYear = Year.now().getValue();
        int startYear = endYear - 4;

        for (int year = startYear; year <= endYear; year++) {
            // Format: 2023-24
            String yearFormat = "%d-%d".formatted(year, (year + 1) % 100);
            String scheduleUrl = "%s/sports/mens-basketball/schedule/%s".formatted(BASE_URL, yearFormat);

            try {
                logger.info("Scraping schedule for season: {}", yearFormat);
                scrapeGameStats(scheduleUrl);
            } catch (Exception e) {
                logger.error("Failed to scrape season {}: {}", yearFormat, e.getMessage());
            }
        }
    }

    public void scrapeGameStats(String scheduleUrl) throws IOException {
        logger.info("Scraping game stats from URL: {}", scheduleUrl);
        try {
            Document scheduleDoc = Jsoup.connect(scheduleUrl)
                    .userAgent(USER_AGENT)
                    .get();

            // Find all box score links from the schedule page
            Elements gameLinks = scheduleDoc.select("a[href*=/boxscore/]");
            logger.info("Found {} game links", gameLinks.size());

            for (Element gameLink : gameLinks) {
                String gameUrl = BASE_URL + gameLink.attr("href");

                // Skip if we've already processed this game URL
                if (!processedGameUrls.add(gameUrl)) {
                    logger.info("Skipping already processed game: {}", gameUrl);
                    continue;
                }

                try {
                    Document gameDoc = Jsoup.connect(gameUrl)
                            .userAgent(USER_AGENT)
                            .get();
                    processGameStats(gameDoc);
                } catch (IOException e) {
                    logger.error("Error scraping individual game {}: {}", gameUrl, e.getMessage());
                }
                System.gc();
            }
        } catch (IOException e) {
            logger.error("Error scraping schedule from URL {}: {}", scheduleUrl, e.getMessage());
            throw e;
        }
    }

    private void processGameStats(Document gameDoc) throws IOException {
        // Get date (format MM/dd/yy)
        String date = gameDoc.select("dl dt:containsOwn(Date) + dd").text().trim();
        if (date.isEmpty()) {
            throw new IllegalStateException("No date found in game document");
        }
        LocalDate gameDate = LocalDate.parse(date, dateFormatter);
        logger.info("Processing game for date: {}", gameDate);

        // Get opponent (filtering out Cornell)
        String opponent = gameDoc.select("table.sidearm-table tbody tr").stream()
                .filter(row -> !row.select("td span").text().contains("Cornell"))
                .findFirst()
                .map(row -> row.select("td span:not(.sr-only)").last().text())
                .orElseThrow(() -> new IllegalStateException("No opponent found"));

        OpposingTeam opposingTeam = findOpposingTeam(opponent);
        logger.info("Processing game against: {}", opposingTeam);

        // Get the Cornell stats table only
        Element cornellTable = gameDoc
                .select("table.overall-stats.full.hide-caption.highlight-hover.highlight-column-hover")
                .stream()
                .filter(table -> !table.select("caption").text().contains(opponent))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Cornell stats table not found"));

        // Process only Cornell players
        Elements playerRows = cornellTable.select("tbody tr:not(:contains(TEAM))");
        logger.info("Found {} Cornell player rows", playerRows.size());

        int savedCount = 0;
        for (Element row : playerRows) {
            try {
                String playerName = row.select("td a.boxscore_player_link").text();
                if (playerName.isEmpty()) {
                    continue;
                }

                // Remove any asterisk from the player name (starter indicator)
                playerName = playerName.replace("*", "").trim();

                Player player = findPlayerByName(playerName);
                if (player == null) {
                    logger.warn("Player not found: {}", playerName);
                    continue;
                }

                // Check if stats already exist for this player and game
                List<PlayerData> existingStats = playerDataService.getPlayerDataByDateAndPlayer(player, gameDate);
                if (existingStats.isEmpty()) {
                    PlayerData gameStats = new PlayerData();
                    gameStats.setPlayer(player);
                    gameStats.setGameDate(gameDate);
                    gameStats.setOpposingTeam(opposingTeam);

                    // Parse and set stats
                    gameStats.setMinutes(parseIntStat(row, "MIN"));
                    gameStats.setPoints(parseIntStat(row, "PTS"));
                    gameStats.setFieldGoals(parseFirstNumber(row, "FG"));
                    gameStats.setThreePointers(parseFirstNumber(row, "3PT"));
                    gameStats.setFreeThrows(parseFirstNumber(row, "FT"));
                    gameStats.setRebounds(parseIntStat(row, "REB"));
                    gameStats.setAssists(parseIntStat(row, "A"));
                    gameStats.setSteals(parseIntStat(row, "STL"));
                    gameStats.setBlocks(parseIntStat(row, "BLK"));
                    gameStats.setTurnovers(parseIntStat(row, "TO"));
                    gameStats.setFouls(parseIntStat(row, "PF"));

                    PlayerData saved = playerDataService.createPlayerData(gameStats);
                    logger.info("Saved stats for player {} with ID {}", playerName, saved.getId());
                    savedCount++;
                } else {
                    logger.info("Stats already exist for player {} on {}", playerName, gameDate);
                }
            } catch (Exception e) {
                logger.error("Error processing player row: {}", e.getMessage());
            }
        }

        logger.info("Saved {} player stats for game on {} against {}", savedCount, gameDate, opponent);
    }

    private int parseIntStat(Element row, String label) {
        String text = row.select("td[data-label=" + label + "]").text().trim();
        if (text.isEmpty() || text.equals("N/A") || text.contains("+")) {
            return 0;
        }
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            logger.warn("Could not parse stat {}: {}", label, text);
            return 0;
        }
    }

    private int parseFirstNumber(Element row, String label) {
        String text = row.select("td[data-label=" + label + "]").text().split("-")[0].trim();
        if (text.isEmpty() || text.equals("N/A") || text.contains("+")) {
            return 0;
        }
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            logger.warn("Could not parse first number for {}: {}", label, text);
            return 0;
        }
    }

    private OpposingTeam findOpposingTeam(String scrapedTeamName) {
        String processedName = scrapedTeamName.trim();

        try {
            // Try to find a matching team
            for (OpposingTeam team : OpposingTeam.values()) {
                if (team.matches(processedName)) {
                    return team;
                }
            }

            // If no match found, add to unmatched set and return a default
            unmatchedTeams.add(processedName);
            logger.warn("No matching team found for: {}", scrapedTeamName);
            return OpposingTeam.valueOf("Yale"); // temporary default to allow processing to continue
        } catch (Exception e) {
            unmatchedTeams.add(processedName);
            logger.warn("Error processing team name: {}", scrapedTeamName);
            return OpposingTeam.valueOf("Yale"); // temporary default
        }
    }

    Player findPlayerByName(String fullName) {
        try {
            // Check if format is "Last, First"
            if (fullName.contains(",")) {
                String[] nameParts = fullName.split(",", 2);
                String lastName = nameParts[0].trim();
                String firstName = nameParts[1].trim();
                
                logger.debug("Looking up player: firstName='{}', lastName='{}'", firstName, lastName);
                try {
                    return playerService.getPlayerByFirstNameAndLastName(firstName, lastName);
                } catch (NotFoundException e) {
                    logger.warn("Player not found: {}", fullName);
                }
            } 
            // Format is "First Last"
            else {
                String[] nameParts = fullName.split(" ");
                if (nameParts.length >= 2) {
                    String firstName = nameParts[0].trim();
                    // Last name might be multiple words (e.g., "Ragland Jr.")
                    StringBuilder lastName = new StringBuilder();
                    for (int i = 1; i < nameParts.length; i++) {
                        if (i > 1) lastName.append(" ");
                        lastName.append(nameParts[i]);
                    }
                    
                    logger.debug("Looking up player by First Last format: firstName='{}', lastName='{}'", 
                                firstName, lastName.toString());
                    try {
                        return playerService.getPlayerByFirstNameAndLastName(firstName, lastName.toString());
                    } catch (NotFoundException e) {
                        logger.warn("Player not found: {}", fullName);
                    }
                }
            }
            
            return null;
        } catch (Exception e) {
            logger.warn("Error parsing player name: {}", fullName);
            return null;
        }
    }

    public void run() {
        try {
            populate();
            long count = playerDataService.getAllPlayerData().size();
            logger.info("Total player data records after scraping: {}", count);
        } catch (IOException e) {
            logger.error("Failed to populate player stats: {}", e.getMessage());
        }
    }
}