package com.example.allin.data;
import com.example.allin.contract.OpposingTeam;
import com.example.allin.contract.Rarity;
import com.example.allin.user.User;
import com.example.allin.player.Player;
import com.example.allin.contract.Contract;
import com.example.allin.contract.Event;
import com.example.allin.data.PlayerFactory;
import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.util.Random;

//needs tests
public class ContractFactory {
    private final Faker faker = new Faker();
    private final Random random = new Random();

    private final PlayerFactory playerFactory = new PlayerFactory();
    private final UserFactory userFactory = new UserFactory();


    public Contract createRandomContract() {
        Player player = playerFactory.createFakePlayer();
        User owner = userFactory.createRandomUser();
        Double buyPrice = faker.number().randomDouble(2, 100, 5000);  // Random buy price between 100 and 5000
        Rarity rarity = Rarity.getRandomRarity();  // Random rarity
        OpposingTeam opposingTeam = OpposingTeam.getRandomOpposingTeam();
        String opposingTeamImage = faker.internet().avatar();  // Random image URL
        Event event = Event.getRandomEvent();
        Integer eventThreshold = faker.number().numberBetween(1, 100);  // Random event threshold between 1 and 100
        LocalDate creationTime = LocalDate.now().minusDays(faker.number().numberBetween(1, 365));  // Within the past year
        Double value = faker.number().randomDouble(2, 100, 5000);  // Random value between 100 and 5000
        Boolean expired = faker.bool().bool();
        Boolean forSale = faker.bool().bool();
        Double sellPrice = forSale ? faker.number().randomDouble(2, 100, 5000) : null;  // If for sale, random sell price

        return new Contract(player, owner, buyPrice, rarity, opposingTeam, opposingTeamImage, event, eventThreshold,
                creationTime, value, expired, forSale, sellPrice);
    }
}

