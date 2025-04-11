package com.appdev.allin;

import java.text.NumberFormat;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.appdev.allin.gameData.GameDataService;
import com.appdev.allin.player.PlayerService;
import com.appdev.allin.playerData.PlayerDataService;
import com.appdev.allin.scrapers.GameDataScraper;
import com.appdev.allin.scrapers.PlayerDataScraper;
import com.appdev.allin.scrapers.PlayerScraper;

@Component
public class DataInitializer {

        private final PlayerService playerService;
        private final PlayerDataService playerDataService;
        private final GameDataService gameDataService;

        public DataInitializer(PlayerService playerService, PlayerDataService playerDataService, GameDataService gameDataService) {
                this.playerService = playerService;
                this.playerDataService = playerDataService;
                this.gameDataService = gameDataService;
        }

        @EventListener(ApplicationReadyEvent.class)
        public void initializeData() {
                logMemoryUsage("Before player scraper");
                PlayerScraper playerScraper = new PlayerScraper(playerService);
                playerScraper.run();
                
                logMemoryUsage("After player scraper");

                PlayerDataScraper playerDataScraper = new PlayerDataScraper(playerService,
                playerDataService);
                playerDataScraper.run();

                logMemoryUsage("After player data scraper");

                GameDataScraper gameDataScraper = new GameDataScraper(gameDataService);
                gameDataScraper.run();
                logMemoryUsage("After all scrapers");

                // Uncoment the above lines to schedule the scrapers to run periodically

                // ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

                // scheduler.scheduleAtFixedRate(() -> {
                // try {
                //         PlayerScraper playerScraper = new PlayerScraper(playerService);
                //         playerScraper.run();

                //         PlayerDataScraper playerDataScraper = new PlayerDataScraper(playerService,
                //         playerDataService);
                //         playerDataScraper.run();

                //         GameDataScraper gameDataScraper = new GameDataScraper(gameDataService);
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
