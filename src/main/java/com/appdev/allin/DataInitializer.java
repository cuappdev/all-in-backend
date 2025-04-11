package com.appdev.allin;

import java.text.NumberFormat;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.appdev.allin.gameData.GameDataRepo;
import com.appdev.allin.player.PlayerRepo;
import com.appdev.allin.playerData.PlayerDataRepo;
import com.appdev.allin.scrapers.GameDataScraper;
import com.appdev.allin.scrapers.PlayerDataScraper;
import com.appdev.allin.scrapers.PlayerScraper;

@Component
public class DataInitializer {

        private final PlayerRepo playerRepo;
        private final PlayerDataRepo playerDataRepo;
        private final GameDataRepo gameDataRepo;

        public DataInitializer(PlayerRepo playerRepo, PlayerDataRepo playerDataRepo, GameDataRepo gameDataRepo) {
                this.playerRepo = playerRepo;
                this.playerDataRepo = playerDataRepo;
                this.gameDataRepo = gameDataRepo;
        }

        @EventListener(ApplicationReadyEvent.class)
        public void initializeData() {
                logMemoryUsage("Before player scraper");
                PlayerScraper playerScraper = new PlayerScraper(playerRepo);
                playerScraper.run();
                
                logMemoryUsage("After player scraper");

                PlayerDataScraper playerDataScraper = new PlayerDataScraper(playerRepo,
                playerDataRepo);
                playerDataScraper.run();

                logMemoryUsage("After player data scraper");

                GameDataScraper gameDataScraper = new GameDataScraper(gameDataRepo);
                gameDataScraper.run();
                logMemoryUsage("After all scrapers");

                // Uncoment the above lines to schedule the scrapers to run periodically

                // ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

                // scheduler.scheduleAtFixedRate(() -> {
                // try {
                //         PlayerScraper playerScraper = new PlayerScraper(playerRepo);
                //         playerScraper.run();

                //         PlayerDataScraper playerDataScraper = new PlayerDataScraper(playerRepo,
                //         playerDataRepo);
                //         playerDataScraper.run();

                //         GameDataScraper gameDataScraper = new GameDataScraper(gameDataRepo);
                //         gameDataScraper.run();
                // } catch (Exception e) {
                // e.printStackTrace();
                // }
                // }, 0, 1, TimeUnit.DAYS); // Initial delay: 0, Repeat every 1 day
        }


        private void logMemoryUsage(String point) {
                Runtime runtime = Runtime.getRuntime();
                NumberFormat format = NumberFormat.getInstance();
                
                long maxMemory = runtime.maxMemory();
                long allocatedMemory = runtime.totalMemory();
                long freeMemory = runtime.freeMemory();
                long usedMemory = allocatedMemory - freeMemory;
                
                System.out.println("Memory Usage at " + point);
                System.out.println("Max Memory: " + format.format(maxMemory / 1024 / 1024) + " MB");
                System.out.println("Allocated Memory: " + format.format(allocatedMemory / 1024 / 1024) + " MB");
                System.out.println("Used Memory: " + format.format(usedMemory / 1024 / 1024) + " MB");
                System.out.println("Free Memory: " + format.format(freeMemory / 1024 / 1024) + " MB");
        }
}
