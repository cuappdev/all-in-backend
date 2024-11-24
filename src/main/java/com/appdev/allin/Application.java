package com.appdev.allin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.appdev.allin.player.PlayerRepo;
import com.appdev.allin.playerData.PlayerDataRepo;
// import com.appdev.allin.playerData.util.PopulatePlayerData;

import com.appdev.allin.gameData.GameDataRepo;

import scrapers.PlayerDataScraper;
import scrapers.PlayerStatsScraper;
import scrapers.GameDataScraper;


@SpringBootApplication
public class Application {

        @Autowired
        PlayerRepo playerRepo;

        @Autowired
        PlayerDataRepo playerDataRepo;

        @Autowired
        GameDataRepo gameDataRepo;


        public static void main(String[] args) {
                SpringApplication.run(Application.class, args);
        }

        @Bean
        public CommandLineRunner populateGameData(GameDataRepo gameDataRepo) {
                return args -> {
                        System.out.println("Starting GameDataScraper!");
                        GameDataScraper gameDataScraper = new GameDataScraper(gameDataRepo);
                        gameDataScraper.run();
                };
        }
        
        @Bean
        public CommandLineRunner populatePlayers(PlayerRepo playerRepo, PlayerDataRepo playerDataRepo) {
                return (args) -> {
                        // Scrape player data (name, height, age, etc)
                        PlayerDataScraper playerDataScraper = new PlayerDataScraper(playerRepo);
                        playerDataScraper.populate();

                        // Scrape player stats (points, rebounds, assists, etc)
                        PlayerStatsScraper playerStatsScraper = new PlayerStatsScraper(playerRepo, playerDataRepo);
                        playerStatsScraper.run();
                };
        }
        
}