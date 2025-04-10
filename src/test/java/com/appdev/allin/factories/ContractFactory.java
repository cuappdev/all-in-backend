package com.appdev.allin.factories;

import com.appdev.allin.contract.Contract;
import com.appdev.allin.contract.Event;
import com.appdev.allin.contract.OpposingTeam;
import com.appdev.allin.contract.Rarity;
import com.appdev.allin.player.Player;

import com.appdev.allin.user.UserService;
import com.appdev.allin.user.User;
import com.github.javafaker.Faker;

import java.time.Clock;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

public class ContractFactory {
    private static final Faker faker = new Faker();

    @Autowired
    UserService userService;

    private Clock clock;

    public ContractFactory() {
        this.clock = Clock.systemDefaultZone();
    }

    public ContractFactory(UserService userService) {
        this.userService = userService;
        this.clock = Clock.systemDefaultZone();
    }

    public Contract createRandomContract(User owner) {
        LocalDateTime now = LocalDateTime.now(clock);

        Player player = PlayerFactory.createFakePlayer();
        Integer buyPrice = faker.number().randomDigit();
        Rarity rarity = Rarity.getRandomRarity(); // Random rarity
        OpposingTeam opposingTeam = OpposingTeam.getRandomOpposingTeam();
        String opposingTeamImage = faker.internet().avatar(); // Random image URL
        Event event = Event.getRandomEvent();
        Integer eventThreshold = faker.number().numberBetween(1, 100); // Random event threshold
        Integer value = faker.number().numberBetween(1, 100); // Random value
        LocalDateTime creationTime = now.minusDays(faker.number().numberBetween(1, 365));
        LocalDateTime expirationTime = creationTime.plusDays(faker.number().numberBetween(1, 365));
        Boolean expired = expirationTime.isBefore(now);

        Boolean forSale = faker.bool().bool();
        Integer sellPrice = forSale ? faker.number().randomDigit() : null; // If for sale, set price

        return new Contract(player, owner, buyPrice, rarity, opposingTeam, opposingTeamImage, event, eventThreshold,
                creationTime, value, expirationTime, expired, forSale, sellPrice);
    }

}
