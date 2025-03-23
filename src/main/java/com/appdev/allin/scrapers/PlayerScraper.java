package com.appdev.allin.scrapers;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appdev.allin.player.Player;
import com.appdev.allin.player.PlayerRepo;
import com.appdev.allin.player.Position;

public class PlayerScraper {
    private static final Logger logger = LoggerFactory.getLogger(PlayerScraper.class);

    private final PlayerRepo playerRepo;

    private static final String ROSTER_URL = "https://cornellbigred.com/sports/mens-basketball/roster?view=2";

    public PlayerScraper(PlayerRepo playerRepo) {
        this.playerRepo = playerRepo;
    }

    public void populate() throws IOException {
        logger.info("Scraping player data from URL: {}", ROSTER_URL);
        try {
            Document doc = Jsoup.connect(ROSTER_URL).get();

            Elements playerElements = doc.select("ul.sidearm-roster-players > li.sidearm-roster-player");

            logger.info("Found {} players on the roster page.", playerElements.size());

            for (Element playerElement : playerElements) {
                String numberStr = extractText(playerElement, "span.sidearm-roster-player-jersey-number");
                Integer number = extractIntegerFromString(numberStr);

                String fullName = extractText(playerElement, "h3 a");
                String[] nameParts = fullName.split(" ", 2);
                String firstName = nameParts.length > 0 ? nameParts[0].trim() : "";
                String lastName = nameParts.length > 1 ? nameParts[1].trim() : "";

                String positionStr = extractText(playerElement,
                        "div.sidearm-roster-player-position span.sidearm-roster-player-position-long-short.hide-on-medium");
                Position[] positions = extractPositionsFromString(positionStr);

                // Can do if we want player class
                // String playerClass = extractText(playerElement,
                // "span.sidearm-roster-player-academic-year");

                String height = extractText(playerElement, "span.sidearm-roster-player-height");

                String weightStr = extractText(playerElement, "span.sidearm-roster-player-weight");
                Integer weight = extractIntegerFromString(weightStr);

                String hometown = extractText(playerElement, "span.sidearm-roster-player-hometown");
                String highSchool = extractText(playerElement, "span.sidearm-roster-player-highschool");

                Element imageElement = playerElement.selectFirst("div.sidearm-roster-player-image img");
                String imageUrl = imageElement != null ? imageElement.attr("data-src") : "";

                if (number == null || firstName.isEmpty() || lastName.isEmpty()) {
                    logger.warn("Bad data for player {} {}", firstName, lastName);
                    continue;
                }

                Player existingPlayer = playerRepo.findByNumber(number);
                if (existingPlayer != null) {
                    logger.warn("Player already exists.");
                    continue;
                }

                Player player = new Player(firstName, lastName, positions, number, height, weight, hometown, highSchool,
                        imageUrl);

                if (playerRepo.findByNumber(player.getNumber()) == null) {
                    playerRepo.save(player);
                    logger.info("Saved player: {} {}", firstName, lastName);
                } else {
                    logger.warn("Player already exists: {}", player);
                }
            }

            logger.info("Player data scraping completed");

        } catch (IOException e) {
            logger.error("Error scraping player data: {}", e.getMessage());
            throw e;
        }
    }

    public void run() {
        try {
            populate();
        } catch (IOException e) {
            logger.error("Failed to populate player data: {}", e.getMessage());
        }
    }

    private String extractText(Element element, String cssQuery) {
        Element selectedElement = element.selectFirst(cssQuery);
        String text = (selectedElement != null) ? selectedElement.text().trim() : "";
        return text.replace("\"", "");
    }

    private Integer extractIntegerFromString(String text) {
        try {
            return Integer.parseInt(text.replaceAll("[^\\d]", ""));
        } catch (NumberFormatException e) {
            logger.warn("Failed retrieving integer: '{}'", text);
            return null;
        }
    }

    private Position[] extractPositionsFromString(String positionStr) {
        // Split positions e.g. "G/F"
        String[] posStrings = positionStr.split("/");
        return Arrays.stream(posStrings)
                .map(String::trim)
                .map(this::mapPosition)
                .filter(Objects::nonNull)
                .toArray(Position[]::new);
    }

    private Position mapPosition(String pos) {
        switch (pos.toUpperCase()) {
            case "G" -> {
                return Position.GUARD;
            }
            case "F" -> {
                return Position.FORWARD;
            }
            case "C" -> {
                return Position.CENTER;
            }
            default -> {
                logger.warn("Unknown pos: '{}'", pos);
                return null;
            }
        }
    }
}
