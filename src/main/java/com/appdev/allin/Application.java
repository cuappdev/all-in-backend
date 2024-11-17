package com.appdev.allin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.appdev.allin.player.PlayerRepo;
import com.appdev.allin.playerData.PlayerDataRepo;
import com.appdev.allin.playerData.util.PopulatePlayerData;

import scrapers.PlayerDataScraper;

@SpringBootApplication
public class Application {

    @Autowired PlayerRepo playerRepo;

    @Autowired PlayerDataRepo playerDataRepo;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
    public CommandLineRunner populatePlayers(PlayerRepo playerRepo, PlayerDataRepo playerDataRepo) {
            return (args) -> {
                    PlayerDataScraper playerDataScraper = new PlayerDataScraper(playerRepo);
                    playerDataScraper.populate();


                    PopulatePlayerData populatePlayerData = new PopulatePlayerData(playerRepo, playerDataRepo);
                    populatePlayerData.run();
            };
    }
}

