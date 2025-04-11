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
import com.appdev.allin.player.PlayerService;
import com.appdev.allin.player.Position;
import com.appdev.allin.utils.ImageProcessor;

public class PlayerScraper {
    private static final Logger logger = LoggerFactory.getLogger(PlayerScraper.class);

    private final PlayerService playerService;

    private static final String BASE_URL = "https://cornellbigred.com/sports/mens-basketball/roster?view=2";

    public PlayerScraper(PlayerService playerService) {
        this.playerService = playerService;
    }

    public void populate() throws IOException {
        logger.info("Scraping player data from URL: {}", BASE_URL);
        try {
            Document doc = Jsoup.connect(BASE_URL).get();

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
                String imageUrl = imageElement != null ? "https://cornellbigred.com" + imageElement.attr("data-src") : "";
                

                String bucketUrl = "";
                if (!imageUrl.isEmpty()) {
                    String b64Image = "data:image/webp;base64," + ImageProcessor.urlToBase64(imageUrl);

                    if (b64Image != null && !b64Image.isEmpty()) {
                        bucketUrl = ImageProcessor.uploadImage(b64Image, 250, 250);

                    }
                }

                if (number == null || firstName.isEmpty() || lastName.isEmpty()) {
                    logger.warn("Bad data for player {} {}", firstName, lastName);
                    continue;
                }

                Player existingPlayer = playerService.getPlayerByNumber(number);
                if (existingPlayer != null) {
                    logger.warn("Player already exists.");
                    continue;
                }

                Player player = new Player(firstName, lastName, positions, number, height, weight, hometown, highSchool,
                        bucketUrl);

                if (playerService.getPlayerByNumber(player.getNumber()) == null) {
                    playerService.savePlayer(player);
                    logger.info("Saved player: {} {}", firstName, lastName);
                } else {
                    logger.warn("Player already exists: {}", player);
                }
                System.gc();
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
            return Integer.valueOf(text.replaceAll("[^\\d]", ""));
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