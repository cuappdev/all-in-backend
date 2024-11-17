package scrapers;


import com.appdev.allin.player.Player;
import com.appdev.allin.player.PlayerRepo;
import com.appdev.allin.playerData.PlayerData;
import com.appdev.allin.playerData.PlayerDataRepo;
import com.appdev.allin.contract.OpposingTeam;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class PlayerStatsScraper {
   private static final Logger logger = LoggerFactory.getLogger(PlayerStatsScraper.class);
  
   private final PlayerRepo playerRepo;
   private final PlayerDataRepo playerDataRepo;
   private static final String BASE_URL = "https://cornellbigred.com";
   private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");
   private static final Pattern SHOTS_PATTERN = Pattern.compile("(\\d+)-\\d+");


   public PlayerStatsScraper(PlayerRepo playerRepo, PlayerDataRepo playerDataRepo) {
       this.playerRepo = playerRepo;
       this.playerDataRepo = playerDataRepo;
   }


   public void populate() throws IOException {
       int startYear = 2018;
       int endYear = 2023;


       for (int year = startYear; year <= endYear; year++) {
           String seasonUrl = String.format("%s/sports/mens-basketball/stats/%d-%d?path=mbball", BASE_URL, year, year + 1);
           scrapeSeasonGames(seasonUrl, year);
       }
   }


   private void scrapeSeasonGames(String seasonUrl, int year) throws IOException {
       logger.info("Scraping season data from URL: {}", seasonUrl);
       try {
           Document seasonDoc = Jsoup.connect(seasonUrl)
                                   .timeout(10000)
                                   .get();
           Elements gameRows = seasonDoc.select("tbody tr");


           for (Element gameRow : gameRows) {
               Element scoreLink = gameRow.selectFirst("a[href*='boxscore']");
               if (scoreLink != null) {
                   String gameUrl = BASE_URL + scoreLink.attr("href");
                   try {
                       scrapeGameStats(gameUrl);
                   } catch (IOException e) {
                       logger.error("Failed to scrape game at URL {}: {}", gameUrl, e.getMessage());
                   }
               }
           }
       } catch (IOException e) {
           logger.error("Error scraping season data for year {}: {}", year, e.getMessage());
           throw e;
       }
   }


   public void scrapeGameStats(String gameUrl) throws IOException {
       logger.info("Scraping game stats from URL: {}", gameUrl);
       try {
           Document doc = Jsoup.connect(gameUrl)
                              .timeout(10000)
                              .get();
           scrapeGameStats(doc);
       } catch (IOException e) {
           logger.error("Error scraping game stats from URL {}: {}", gameUrl, e.getMessage());
           throw e;
       }
   }


   void scrapeGameStats(Document gameDoc) throws IOException {
       try {
           String dateStr = gameDoc.select(".date").text();
           LocalDate gameDate = LocalDate.parse(dateStr, dateFormatter);


           Elements teamRows = gameDoc.select("tr:not(.highlight) td");
           if (teamRows.isEmpty()) {
               logger.error("No team data found in document");
               return;
           }


           String opponentName = teamRows.get(1).text();
           OpposingTeam opponent = mapOpponentNameToEnum(opponentName);


           Elements playerRows = gameDoc.select("tr:not(.highlight)").not(":has(td.highlight)");


           for (Element playerRow : playerRows) {
               try {
                   processPlayerRow(playerRow, gameDate, opponent);
               } catch (Exception e) {
                   logger.error("Error processing player row: {}", e.getMessage());
               }
           }
       } catch (DateTimeParseException e) {
           logger.error("Error parsing game date: {}", e.getMessage());
           throw new IOException("Invalid date format", e);
       }
   }


   private void processPlayerRow(Element playerRow, LocalDate gameDate, OpposingTeam opponent) {
       Elements columns = playerRow.select("td");
       if (columns.size() < 13) return; // Need at least 13 columns for all stats


       Element playerNameLink = playerRow.select("a").first();
       if (playerNameLink == null) return;


       String playerName = playerNameLink.text();
       Player player = findPlayerByName(playerName);


       if (player == null) {
           logger.warn("Player not found: {}", playerName);
           return;
       }


       PlayerData gameStats = new PlayerData();
       gameStats.setPlayer(player);
       gameStats.setGameDate(gameDate);
       gameStats.setOpposingTeam(opponent);


       // Parse stats with index safety checks
       gameStats.setMinutes(parseMinutes(getColumnText(columns, 1)));
       gameStats.setFieldGoals(parseMadeShots(getColumnText(columns, 2)));
       gameStats.setThreePointers(parseMadeShots(getColumnText(columns, 3)));
       gameStats.setFreeThrows(parseMadeShots(getColumnText(columns, 4)));
       gameStats.setRebounds(parseIntStat(getColumnText(columns, 6)));
       gameStats.setFouls(parseIntStat(getColumnText(columns, 7)));
       gameStats.setAssists(parseIntStat(getColumnText(columns, 8)));
       gameStats.setTurnovers(parseIntStat(getColumnText(columns, 9)));
       gameStats.setBlocks(parseIntStat(getColumnText(columns, 10)));
       gameStats.setSteals(parseIntStat(getColumnText(columns, 11)));
       gameStats.setPoints(parseIntStat(getColumnText(columns, 12)));


       playerDataRepo.save(gameStats);
       logger.info("Saved game stats for player: {} on date: {}", playerName, gameDate);
   }


   private String getColumnText(Elements columns, int index) {
       return index < columns.size() ? columns.get(index).text() : "";
   }


   private OpposingTeam mapOpponentNameToEnum(String opponentName) {
       String cleanName = opponentName.replace("University", "")
                                    .replace("State", "")
                                    .replace("College", "")
                                    .trim();
      
       for (OpposingTeam team : OpposingTeam.values()) {
           if (cleanName.toUpperCase().contains(team.name().toUpperCase())) {
               return team;
           }
       }
      
       logger.warn("Could not map opponent name '{}' to enum", opponentName);
       throw new IllegalArgumentException("Unknown opponent: " + opponentName);
   }


   Player findPlayerByName(String fullName) {
       String[] nameParts = fullName.split(" ", 2);
       if (nameParts.length != 2) {
           logger.warn("Invalid player name format: {}", fullName);
           return null;
       }
       String firstName = nameParts[0].trim();
       String lastName = nameParts[1].trim();
       return playerRepo.findByFirstNameAndLastName(firstName, lastName);
   }


   Integer parseMinutes(String minutes) {
       if (minutes == null || minutes.trim().isEmpty()) {
           return 0;
       }
       try {
           return Integer.parseInt(minutes.trim());
       } catch (NumberFormatException e) {
           logger.warn("Could not parse minutes: {}", minutes);
           return 0;
       }
   }


   Integer parseMadeShots(String shotString) {
       if (shotString == null || shotString.trim().isEmpty()) {
           return 0;
       }
       Matcher matcher = SHOTS_PATTERN.matcher(shotString);
       if (matcher.find()) {
           return Integer.parseInt(matcher.group(1));
       }
       return 0;
   }


   Integer parseIntStat(String stat) {
       try {
           return Integer.parseInt(stat.trim());
       } catch (NumberFormatException e) {
           return 0;
       }
   }


   public void run() {
       try {
           populate();
       } catch (IOException e) {
           logger.error("Failed to populate player stats: {}", e.getMessage());
       }
   }
}
