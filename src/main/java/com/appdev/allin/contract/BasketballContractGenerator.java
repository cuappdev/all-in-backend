package com.appdev.allin.contract;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

import com.appdev.allin.player.Player;
import com.appdev.allin.playerData.PlayerDataRepo;
import com.appdev.allin.user.User;

public class BasketballContractGenerator extends ContractGenerator {

        public BasketballContractGenerator(PlayerDataRepo playerDataRepo) {
                super(playerDataRepo);
        }

        @Override
        public Contract generateContract(User user, Player player, Integer buyPrice, Rarity rarity) {
                Event event = Event.getRandomEvent();
                OpposingTeam opposingTeam = OpposingTeam.getRandomOpposingTeam();
                Double eventProb = 0.5;
                Double ratio = 2.0;

                // Determine event probability and ratio based on rarity
                switch (rarity) {
                        case COMMON:
                                // Probability of event hitting ranges from COMMON_PROB_LOWER_BOUND -
                                // COMMON_PROB_UPPER_BOUND
                                eventProb = ThreadLocalRandom.current().nextDouble()
                                                * (COMMON_PROB_UPPER_BOUND - COMMON_PROB_LOWER_BOUND)
                                                + COMMON_PROB_LOWER_BOUND;
                                // Payout/buy_in ratio ranges from COMMON_PAYOUT_LOWER_BOUND -
                                // COMMON_PAYOUT_UPPER_BOUND
                                ratio = ThreadLocalRandom.current().nextDouble()
                                                * (COMMON_PAYOUT_UPPER_BOUND - COMMON_PAYOUT_LOWER_BOUND)
                                                + COMMON_PAYOUT_LOWER_BOUND;
                                break;
                        case RARE:
                                // Probability of event hitting ranges from RARE_PROB_LOWER_BOUND -
                                // RARE_PROB_UPPER_BOUND
                                eventProb = ThreadLocalRandom.current().nextDouble()
                                                * (RARE_PROB_UPPER_BOUND - RARE_PROB_LOWER_BOUND)
                                                + RARE_PROB_LOWER_BOUND;
                                // Payout/buy_in ratio ranges from RARE_PAYOUT_LOWER_BOUND -
                                // RARE_PAYOUT_UPPER_BOUND
                                ratio = ThreadLocalRandom.current().nextDouble()
                                                * (RARE_PAYOUT_UPPER_BOUND - RARE_PAYOUT_LOWER_BOUND)
                                                + RARE_PAYOUT_LOWER_BOUND;
                                break;
                        case EPIC:
                                // Probability of event hitting ranges from EPIC_PROB_LOWER_BOUND -
                                // EPIC_PROB_UPPER_BOUND
                                eventProb = ThreadLocalRandom.current().nextDouble()
                                                * (EPIC_PROB_UPPER_BOUND - EPIC_PROB_LOWER_BOUND)
                                                + EPIC_PROB_LOWER_BOUND;
                                // Payout/buy_in ratio ranges from EPIC_PAYOUT_LOWER_BOUND -
                                // EPIC_PAYOUT_UPPER_BOUND
                                ratio = ThreadLocalRandom.current().nextDouble()
                                                * (EPIC_PAYOUT_UPPER_BOUND - EPIC_PAYOUT_LOWER_BOUND)
                                                + EPIC_PAYOUT_LOWER_BOUND;
                                break;
                        case LEGENDARY:
                                // Probability of event hitting ranges from LEGENDARY_PROB_LOWER_BOUND -
                                // LEGENDARY_PROB_UPPER_BOUND
                                eventProb = ThreadLocalRandom.current().nextDouble()
                                                * (LEGENDARY_PROB_UPPER_BOUND - LEGENDARY_PROB_LOWER_BOUND)
                                                + LEGENDARY_PROB_LOWER_BOUND;
                                // Payout/buy_in ratio ranges from LEGENDARY_PAYOUT_LOWER_BOUND -
                                // LEGENDARY_PAYOUT_UPPER_BOUND
                                ratio = ThreadLocalRandom.current().nextDouble()
                                                * (LEGENDARY_PAYOUT_UPPER_BOUND
                                                                - LEGENDARY_PAYOUT_LOWER_BOUND)
                                                + LEGENDARY_PAYOUT_LOWER_BOUND;
                                break;
                        default:
                                break;
                }

                Integer eventThreshold = normalizeEventThreshold(player, event, eventProb);

                // Generate the basketball-specific contract
                return new Contract(
                                player,
                                user,
                                buyPrice,
                                rarity,
                                opposingTeam,
                                "src/main/resources/static/images/teams/" + opposingTeam.toString().toLowerCase()
                                                + ".png",
                                event,
                                eventThreshold,
                                LocalDateTime.now(),
                                buyPrice * 2,
                                null,
                                false,
                                false,
                                null);
        }
}
