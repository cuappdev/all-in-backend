package com.appdev.allin.data;

import com.appdev.allin.contract.Contract;
import com.appdev.allin.contract.Event;
import com.appdev.allin.contract.OpposingTeam;
import com.appdev.allin.contract.Rarity;
import com.appdev.allin.player.Player;
import com.appdev.allin.contract.Contract;
import com.appdev.allin.contract.Event;

import com.appdev.allin.user.UserService;
import com.appdev.allin.user.User;
import com.github.javafaker.Faker;

import java.time.Clock;
import java.time.LocalDate;

import com.appdev.allin.data.PlayerFactory;
import com.appdev.allin.user.UserRepo;
import com.appdev.allin.user.UserService;
import com.github.javafaker.Faker;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

// needs tests
public class ContractFactory {
    private static final Faker faker = new Faker();

    private PlayerFactory playerFactory = new PlayerFactory();

    @Autowired UserService userService;

    private Clock clock;


    public ContractFactory(){
        this.clock = Clock.systemDefaultZone();
    }

    public ContractFactory(PlayerFactory playerFactory, UserService userService) {

        this.playerFactory = playerFactory;
        this.userService = userService;
        this.clock = Clock.systemDefaultZone();
    }

    public Contract createRandomContract(User owner) {
        LocalDate now = LocalDate.now(clock);


        Player player = playerFactory.createFakePlayer();
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
