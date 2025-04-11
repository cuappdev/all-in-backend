package com.appdev.allin.scrapers;

import java.io.IOException;
import java.util.Objects;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appdev.allin.gameData.GameData;
import com.appdev.allin.gameData.GameDataRepo;

public class GameDataScraper {
    private static final Logger logger = LoggerFactory.getLogger(GameDataScraper.class);

    private final GameDataRepo gameDataRepo;

    public GameDataScraper(GameDataRepo gameDataRepo) {
        this.gameDataRepo = Objects.requireNonNull(gameDataRepo);
    }

    private static final String SCHEDULE_URL = "https://cornellbigred.com/sports/mens-basketball/schedule/2024-25";

    public void populateUpcomingGames() throws IOException {
        logger.info("Scraping game schedule data from URL: {}", SCHEDULE_URL);
        try {
            Document doc = Jsoup.connect(SCHEDULE_URL).get();

            Elements gameElements = doc.select("div.sidearm-schedule-game-opponent-name");
            Elements dateElements = doc.select("div.sidearm-schedule-game-opponent-date");
            Elements locationElements = doc.select("div.sidearm-schedule-game-location");
            Elements logoElements = doc.select("div.sidearm-schedule-game-opponent-logo img");

            logger.info("Found {} upcoming games on the schedule page.", gameElements.size());

            for (int i = 0; i < gameElements.size(); i++) {
                Element gameElement = gameElements.get(i);
                String opponentName = gameElement.text().trim();
                String fullLocation = locationElements.get(i).text().trim();

                String gameDate = "";
                if (i < dateElements.size()) {
                    Element dateElement = dateElements.get(i);
                    gameDate = dateElement.text().trim();
                }

                String logoUrl = "";
                if (i < logoElements.size()) {
                    Element logoElement = logoElements.get(i);
                    logoUrl = logoElement.attr("data-src").isEmpty() ? logoElement.attr("src")
                            : logoElement.attr("data-src");
                    logoUrl = "https://cornellbigred.com" + logoUrl;
                }

                if (opponentName.isEmpty() || gameDate.isEmpty() || fullLocation.isEmpty() || logoUrl.isEmpty()) {
                    logger.warn("Bad data for game {} with opponent: {}", i + 1, opponentName);
                    continue;
                }

                logger.info("Upcoming Game {}: {} on {} in {}, Logo URL: {}", i + 1, opponentName, gameDate,
                        fullLocation, logoUrl);
                GameData gameData = new GameData(opponentName, gameDate, fullLocation, logoUrl);
                if (gameDataRepo.findByOpposingTeamAndGameDateTime(opponentName, gameDate) == null) {
                    gameDataRepo.save(gameData);
                } else {
                    logger.info("Game {} with opponent: {} already exists in the database", i + 1, opponentName);
                }
                System.gc();
            }

            logger.info("Game schedule scraping completed");

        } catch (IOException e) {
            logger.error("Error scraping game schedule data: {}", e.getMessage());
            throw e;
        }
    }

    public void run() {
        try {
            populateUpcomingGames();
        } catch (IOException e) {
            logger.error("Failed to populate game schedule data: {}", e.getMessage());
        }
    }
}