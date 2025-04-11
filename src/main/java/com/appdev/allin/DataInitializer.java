package com.appdev.allin;

import com.appdev.allin.player.PlayerRepo;
import com.appdev.allin.playerData.PlayerDataRepo;
import com.appdev.allin.gameData.GameDataRepo;

import com.appdev.allin.scrapers.PlayerScraper;
import com.appdev.allin.scrapers.PlayerDataScraper;
import com.appdev.allin.scrapers.GameDataScraper;

// import java.util.concurrent.Executors;
// import java.util.concurrent.ScheduledExecutorService;
// import java.util.concurrent.TimeUnit;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

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
                // PlayerScraper playerScraper = new PlayerScraper(playerRepo);
                // playerScraper.run();

                // PlayerDataScraper playerDataScraper = new PlayerDataScraper(playerRepo,
                // playerDataRepo);
                // playerDataScraper.run();

                // GameDataScraper gameDataScraper = new GameDataScraper(gameDataRepo);
                // gameDataScraper.run();
                // Uncomment the above lines to schedule the scrapers to run periodically

                // ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

                // scheduler.scheduleAtFixedRate(() -> {
                // try {
                // PlayerScraper playerScraper = new PlayerScraper(playerRepo);
                // playerScraper.run();

                // PlayerDataScraper playerDataScraper = new PlayerDataScraper(playerRepo,
                // playerDataRepo);
                // playerDataScraper.run();

                // GameDataScraper gameDataScraper = new GameDataScraper(gameDataRepo);
                // gameDataScraper.run();
                // } catch (Exception e) {
                // e.printStackTrace();
                // }
                // }, 0, 1, TimeUnit.DAYS); // Initial delay: 0, Repeat every 1 day
        }
}
