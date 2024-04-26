package com.example.allin;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import com.example.allin.contract.Contract;
import com.example.allin.contract.util.ContractGenerator;
import com.example.allin.contract.Rarity;
import com.example.allin.player.Player;
import com.example.allin.player.Position;
import com.example.allin.user.User;
import com.example.allin.playerData.PlayerDataRepo;

class ContractGeneratorTest {

	@Test
  public void testGenerateContract() {
    
    PlayerDataRepo playerDataRepo = mock(PlayerDataRepo.class);
    ContractGenerator contractGenerator = new ContractGenerator(playerDataRepo);
    
    System.out.println("Testing ContractGenerator.generateContract()");
    System.out.println("Common Probability Threshold: " + ContractGenerator.COMMON_PROBABILITY_THRESHOLD);
    System.out.println("Rare Probability Threshold: " + ContractGenerator.RARE_PROBABILITY_THRESHOLD);
    System.out.println("Epic Probability Threshold: " + ContractGenerator.EPIC_PROBABILITY_THRESHOLD);
    System.out.println("Legendary Probability Threshold: " + ContractGenerator.LEGENDARY_PROBABILITY_THRESHOLD + "\n\n");

    ContractGenerator.generateBounds();

    System.out.println("Printing Probability and Payout Ranges\n");
    System.out.println("Common Probability: [" + ContractGenerator.COMMON_PROB_LOWER_BOUND + ", " + ContractGenerator.COMMON_PROB_UPPER_BOUND + "]");
    System.out.println("Common Payout: [" + ContractGenerator.COMMON_PAYOUT_LOWER_BOUND + ", " + ContractGenerator.COMMON_PAYOUT_UPPER_BOUND + "]\n");
    System.out.println("Rare Probability: [" + ContractGenerator.RARE_PROB_LOWER_BOUND + ", " + ContractGenerator.RARE_PROB_UPPER_BOUND + "]");
    System.out.println("Rare Payout: [" + ContractGenerator.RARE_PAYOUT_LOWER_BOUND + ", " + ContractGenerator.RARE_PAYOUT_UPPER_BOUND + "]\n");
    System.out.println("Epic Probability: [" + ContractGenerator.EPIC_PROB_LOWER_BOUND + ", " + ContractGenerator.EPIC_PROB_UPPER_BOUND + "]");
    System.out.println("Epic Payout: [" + ContractGenerator.EPIC_PAYOUT_LOWER_BOUND + ", " + ContractGenerator.EPIC_PAYOUT_UPPER_BOUND + "]\n");
    System.out.println("Legendary Probability: [" + ContractGenerator.LEGENDARY_PROB_LOWER_BOUND + ", " + ContractGenerator.LEGENDARY_PROB_UPPER_BOUND + "]");
    System.out.println("Legendary Payout: [" + ContractGenerator.LEGENDARY_PAYOUT_LOWER_BOUND + ", " + ContractGenerator.LEGENDARY_PAYOUT_UPPER_BOUND + "]\n");

		double common_alpha = (ContractGenerator.COMMON_PROB_UPPER_BOUND + ContractGenerator.COMMON_PROB_LOWER_BOUND) * (ContractGenerator.COMMON_PAYOUT_UPPER_BOUND + ContractGenerator.COMMON_PAYOUT_LOWER_BOUND) / 4.0;
		double rare_alpha = (ContractGenerator.RARE_PROB_UPPER_BOUND + ContractGenerator.RARE_PROB_LOWER_BOUND) * (ContractGenerator.RARE_PAYOUT_UPPER_BOUND + ContractGenerator.RARE_PAYOUT_LOWER_BOUND) / 4.0;
		double epic_alpha = (ContractGenerator.EPIC_PROB_UPPER_BOUND + ContractGenerator.EPIC_PROB_LOWER_BOUND) * (ContractGenerator.EPIC_PAYOUT_UPPER_BOUND + ContractGenerator.EPIC_PAYOUT_LOWER_BOUND) / 4.0;
		double legendary_alpha = (ContractGenerator.LEGENDARY_PROB_UPPER_BOUND + ContractGenerator.LEGENDARY_PROB_LOWER_BOUND) * (ContractGenerator.LEGENDARY_PAYOUT_UPPER_BOUND + ContractGenerator.LEGENDARY_PAYOUT_LOWER_BOUND) / 4.0;

		System.out.println("Alphas:");
		System.out.println("Common: " + common_alpha);
		System.out.println("Rare: " + rare_alpha);
		System.out.println("Epic: " + epic_alpha);
		System.out.println("Legendary: " + legendary_alpha + "\n");

    System.out.println("Generating a Contract\n");
    
    User user = new User("Tony", "akm99", "src/main/resources/static/images/users/default.jpg", 1000.0);
    Player player = new Player("LeBron", "James", new Position[]{Position.Center}, 23, "6'9", 250, "Akron, OH", "St. Vincent-St. Mary", "src/main/resources/static/images/players/default.jpg");
    Contract contract = contractGenerator.generateContract(user, player, 100.0, Rarity.Common);
    System.out.println("Contract: " + contract.toString());
  }

}
