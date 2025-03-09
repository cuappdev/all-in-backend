package com.appdev.allin;

import java.util.List;

import org.apache.commons.math3.distribution.NormalDistribution;

import com.appdev.allin.player.Player;
import com.appdev.allin.playerData.PlayerData;
import com.appdev.allin.playerData.PlayerDataRepo;
import com.appdev.allin.user.User;
import com.appdev.allin.contract.Contract;
import com.appdev.allin.contract.Rarity;
import com.appdev.allin.contract.Event;

public abstract class ContractGeneratorTest {

        PlayerDataRepo playerDataRepo;

        public ContractGeneratorTest(PlayerDataRepo playerDataRepo) {
                this.playerDataRepo = playerDataRepo;
        }

        public static final Double COMMON_PROBABILITY_THRESHOLD = 0.7;
        public static final Double RARE_PROBABILITY_THRESHOLD = 0.55;
        public static final Double EPIC_PROBABILITY_THRESHOLD = 0.4;
        public static final Double LEGENDARY_PROBABILITY_THRESHOLD = 0.25;
        public static final Double PROB_RANGE_OVERLAP = 0.05;

        public static final Double ALPHA = 1.0;

        public static Double COMMON_PROB_UPPER_BOUND;
        public static Double RARE_PROB_UPPER_BOUND;
        public static Double EPIC_PROB_UPPER_BOUND;
        public static Double LEGENDARY_PROB_UPPER_BOUND;
        public static Double COMMON_PROB_LOWER_BOUND;
        public static Double RARE_PROB_LOWER_BOUND;
        public static Double EPIC_PROB_LOWER_BOUND;
        public static Double LEGENDARY_PROB_LOWER_BOUND;

        public static Double COMMON_PAYOUT_UPPER_BOUND;
        public static Double RARE_PAYOUT_UPPER_BOUND;
        public static Double EPIC_PAYOUT_UPPER_BOUND;
        public static Double LEGENDARY_PAYOUT_UPPER_BOUND;
        public static Double COMMON_PAYOUT_LOWER_BOUND;
        public static Double RARE_PAYOUT_LOWER_BOUND;
        public static Double EPIC_PAYOUT_LOWER_BOUND;
        public static Double LEGENDARY_PAYOUT_LOWER_BOUND;

        public static void generateBounds() {
                double common_payout_center = 1 / COMMON_PROBABILITY_THRESHOLD;
                double rare_payout_center = 1 / RARE_PROBABILITY_THRESHOLD;
                double epic_payout_center = 1 / EPIC_PROBABILITY_THRESHOLD;
                double legendary_payout_center = 1 / LEGENDARY_PROBABILITY_THRESHOLD;

                double common_prob_radius = (COMMON_PROBABILITY_THRESHOLD - RARE_PROBABILITY_THRESHOLD) / 2.0
                                + PROB_RANGE_OVERLAP / 2.0;
                double rare_prob_radius = Math.min(
                                (RARE_PROBABILITY_THRESHOLD - EPIC_PROBABILITY_THRESHOLD) / 2.0
                                                + PROB_RANGE_OVERLAP / 2.0,
                                (COMMON_PROBABILITY_THRESHOLD - RARE_PROBABILITY_THRESHOLD) / 2.0
                                                + PROB_RANGE_OVERLAP / 2.0);
                double epic_prob_radius = Math.min(
                                (EPIC_PROBABILITY_THRESHOLD - LEGENDARY_PROBABILITY_THRESHOLD) / 2.0
                                                + PROB_RANGE_OVERLAP / 2.0,
                                (RARE_PROBABILITY_THRESHOLD - EPIC_PROBABILITY_THRESHOLD) / 2.0
                                                + PROB_RANGE_OVERLAP / 2.0);
                double legendary_prob_radius = (EPIC_PROBABILITY_THRESHOLD - LEGENDARY_PROBABILITY_THRESHOLD) / 2.0
                                + PROB_RANGE_OVERLAP / 2.0;

                COMMON_PROB_UPPER_BOUND = COMMON_PROBABILITY_THRESHOLD + common_prob_radius;
                RARE_PROB_UPPER_BOUND = RARE_PROBABILITY_THRESHOLD + rare_prob_radius;
                EPIC_PROB_UPPER_BOUND = EPIC_PROBABILITY_THRESHOLD + epic_prob_radius;
                LEGENDARY_PROB_UPPER_BOUND = LEGENDARY_PROBABILITY_THRESHOLD + legendary_prob_radius;
                COMMON_PROB_LOWER_BOUND = COMMON_PROBABILITY_THRESHOLD - common_prob_radius;
                RARE_PROB_LOWER_BOUND = RARE_PROBABILITY_THRESHOLD - rare_prob_radius;
                EPIC_PROB_LOWER_BOUND = EPIC_PROBABILITY_THRESHOLD - epic_prob_radius;
                LEGENDARY_PROB_LOWER_BOUND = LEGENDARY_PROBABILITY_THRESHOLD - legendary_prob_radius;

                double common_payout_radius = Math.min(
                                (common_payout_center - 1.0 / (COMMON_PROB_UPPER_BOUND)),
                                (common_payout_center - 1.0 / (COMMON_PROB_LOWER_BOUND)));
                double rare_payout_radius = Math.min(
                                (rare_payout_center - 1.0 / (RARE_PROB_UPPER_BOUND)),
                                (rare_payout_center - 1.0 / (RARE_PROB_LOWER_BOUND)));
                double epic_payout_radius = Math.min(
                                (epic_payout_center - 1.0 / (EPIC_PROB_UPPER_BOUND)),
                                (epic_payout_center - 1.0 / (EPIC_PROB_LOWER_BOUND)));
                double legendary_payout_radius = Math.min(
                                (legendary_payout_center - 1.0 / (LEGENDARY_PROB_UPPER_BOUND)),
                                (legendary_payout_center - 1.0 / (LEGENDARY_PROB_LOWER_BOUND)));

                COMMON_PAYOUT_UPPER_BOUND = common_payout_center + common_payout_radius;
                RARE_PAYOUT_UPPER_BOUND = rare_payout_center + rare_payout_radius;
                EPIC_PAYOUT_UPPER_BOUND = epic_payout_center + epic_payout_radius;
                LEGENDARY_PAYOUT_UPPER_BOUND = legendary_payout_center + legendary_payout_radius;
                COMMON_PAYOUT_LOWER_BOUND = common_payout_center - common_payout_radius;
                RARE_PAYOUT_LOWER_BOUND = rare_payout_center - rare_payout_radius;
                EPIC_PAYOUT_LOWER_BOUND = epic_payout_center - epic_payout_radius;
                LEGENDARY_PAYOUT_LOWER_BOUND = legendary_payout_center - legendary_payout_radius;
        }

        public abstract Contract generateContract(User user, Player player, Double buyPrice, Rarity rarity);

        public Double[] getPlayerDataByEvent(Player player, Event event) {
                List<PlayerData> playerData = playerDataRepo.findByPlayer(player);
                Integer N = playerData.size();
                Double eventTotal = 0.0;
                for (PlayerData data : playerData) {
                        eventTotal += data.getEvent(event);
                }
                Double eventAvg = eventTotal / N;
                Double eventSD = 0.0;
                for (PlayerData data : playerData) {
                        eventSD += Math.pow(data.getEvent(event) - eventAvg, 2);
                }
                eventSD = Math.sqrt(eventSD / N);
                if (eventSD == 0.0) {
                        eventSD = 1.0;
                }
                return new Double[] { eventAvg, eventSD };
        }

        public Integer normalizeEventThreshold(Player player, Event event, Double eventProb) {
                // Normalize the event threshold based on player averages
                Double[] eventMetrics = getPlayerDataByEvent(player, event);
                Double eventAvg = eventMetrics[0];
                Double eventSD = eventMetrics[1];
                NormalDistribution X = new NormalDistribution(eventAvg, eventSD);
                Integer threshold = (int) Math.ceil(X.inverseCumulativeProbability(1 - eventProb));
                return threshold;
        }
}
