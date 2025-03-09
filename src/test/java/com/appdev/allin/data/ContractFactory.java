package com.appdev.allin.data;

import com.appdev.allin.contract.Contract;
import com.appdev.allin.contract.Event;
import com.appdev.allin.contract.OpposingTeam;
import com.appdev.allin.contract.Rarity;
import com.appdev.allin.player.Player;

import com.appdev.allin.user.UserService;
import com.appdev.allin.user.User;
import com.github.javafaker.Faker;

import java.time.Clock;
import java.time.LocalDate;

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
        LocalDate now = LocalDate.now(clock);

        Player player = PlayerFactory.createFakePlayer();
        Double buyPrice = faker.number().randomDouble(2, 100, 5000); // Random buy price between 100 and 5000
        Rarity rarity = Rarity.getRandomRarity(); // Random rarity
        OpposingTeam opposingTeam = OpposingTeam.getRandomOpposingTeam();
        String opposingTeamImage = faker.internet().avatar(); // Random image URL
        Event event = Event.getRandomEvent();
        Integer eventThreshold = faker.number().numberBetween(1, 100); // Random event threshold
        Double value = faker.number().randomDouble(2, 100, 5000); // Random value
        LocalDate creationTime = now.minusDays(faker.number().numberBetween(1, 365));
        LocalDate expirationTime = creationTime.plusDays(faker.number().numberBetween(1, 365));
        Boolean expired = expirationTime.isBefore(now);

        Boolean forSale = faker.bool().bool();
        Double sellPrice = forSale ? faker.number().randomDouble(2, 100, 5000) : null; // If for sale, set price

        return new Contract(player, owner, buyPrice, rarity, opposingTeam, opposingTeamImage, event, eventThreshold,
                creationTime, value, expirationTime, expired, forSale, sellPrice);
    }

}
