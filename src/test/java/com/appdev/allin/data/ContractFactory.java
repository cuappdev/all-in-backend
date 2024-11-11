package com.appdev.allin.data;
import com.appdev.allin.contract.OpposingTeam;
import com.appdev.allin.contract.Rarity;
import com.appdev.allin.user.User;
import com.appdev.allin.player.Player;
import com.appdev.allin.contract.Contract;
import com.appdev.allin.contract.Event;
import com.appdev.allin.data.PlayerFactory;
import com.appdev.allin.user.UserRepo;
import com.appdev.allin.user.UserService;
import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;

//needs tests
public class ContractFactory {
    private static final Faker faker = new Faker();
    private final Random random = new Random();

    private static final PlayerFactory playerFactory = new PlayerFactory();

    private final UserFactory userFactory = new UserFactory();

    @Autowired UserService userService;




    public Contract createRandomContract(User owner) {
        Player player = playerFactory.createFakePlayer();
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
    public Contract createRandomContract() throws Exception {
        Player player = playerFactory.createFakePlayer();
        List<User> users = userService.getAllUsers();
        User owner;
        if (users.isEmpty()){
            throw new Exception("No initialized users!");
        }else{
            owner = users.get(0);
        }
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

