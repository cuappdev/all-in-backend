package com.appdev.allin.data;
import com.appdev.allin.player.Player;
import com.appdev.allin.contract.OpposingTeam;
import com.appdev.allin.playerData.PlayerData;
import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.util.Random;

//need to test this
public class PlayerDataFactory {
    private static final Faker faker = new Faker();
    private final Random random = new Random();

    public static PlayerData createRandomPlayerData(Player player, OpposingTeam opposingTeam) {
        LocalDate gameDate = LocalDate.now().minusDays(faker.number().numberBetween(1, 365));  // Random date within the past year
        Integer points = faker.number().numberBetween(0, 50); // Points scored
        Integer minutes = faker.number().numberBetween(10, 48); // Minutes played
        Integer fieldGoals = faker.number().numberBetween(0, points / 2); // Field goals made (estimate based on points)
        Integer threePointers = faker.number().numberBetween(0, fieldGoals / 3); // Three-pointers made
        Integer freeThrows = faker.number().numberBetween(0, points - fieldGoals * 2); // Free throws
        Integer rebounds = faker.number().numberBetween(0, 15);
        Integer assists = faker.number().numberBetween(0, 15);
        Integer steals = faker.number().numberBetween(0, 5);
        Integer blocks = faker.number().numberBetween(0, 5);
        Integer turnovers = faker.number().numberBetween(0, 7);
        Integer fouls = faker.number().numberBetween(0, 5);

        return new PlayerData(player, gameDate, opposingTeam, points, minutes, fieldGoals, threePointers, freeThrows,
                rebounds, assists, steals, blocks, turnovers, fouls);
    }
}
