package com.example.allin.contract.util;

import java.time.LocalDate;
import java.util.List;

import com.example.allin.contract.Contract;
import com.example.allin.contract.Event;
import com.example.allin.contract.OpposingTeam;
import com.example.allin.player.Player;
import com.example.allin.playerData.PlayerData;
import com.example.allin.playerData.PlayerDataRepo;
import com.example.allin.user.User;
import com.example.allin.contract.Rarity;

import org.apache.commons.math3.distribution.NormalDistribution;

public class ContractGenerator {

  PlayerDataRepo playerDataRepo;

  public ContractGenerator(PlayerDataRepo playerDataRepo) {
    this.playerDataRepo = playerDataRepo;
  }

  public final static Double COMMON_PROBABILITY_THRESHOLD = 0.7;
  public final static Double RARE_PROBABILITY_THRESHOLD = 0.55;
  public final static Double EPIC_PROBABILITY_THRESHOLD = 0.4;
  public final static Double LEGENDARY_PROBABILITY_THRESHOLD = 0.25;
  public final static Double PROB_RANGE_OVERLAP = 0.05;

  public final static Double ALPHA = 1.0;
  
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
    
    double common_prob_radius = (COMMON_PROBABILITY_THRESHOLD - RARE_PROBABILITY_THRESHOLD) / 2.0 + PROB_RANGE_OVERLAP / 2.0;
    double rare_prob_radius = Math.min((RARE_PROBABILITY_THRESHOLD - EPIC_PROBABILITY_THRESHOLD) / 2.0 + PROB_RANGE_OVERLAP / 2.0, (COMMON_PROBABILITY_THRESHOLD - RARE_PROBABILITY_THRESHOLD) / 2.0 + PROB_RANGE_OVERLAP / 2.0);
    double epic_prob_radius = Math.min((EPIC_PROBABILITY_THRESHOLD - LEGENDARY_PROBABILITY_THRESHOLD) / 2.0 + PROB_RANGE_OVERLAP / 2.0, (RARE_PROBABILITY_THRESHOLD - EPIC_PROBABILITY_THRESHOLD) / 2.0 + PROB_RANGE_OVERLAP / 2.0);
    double legendary_prob_radius = (EPIC_PROBABILITY_THRESHOLD - LEGENDARY_PROBABILITY_THRESHOLD) / 2.0 + PROB_RANGE_OVERLAP / 2.0;
    
    COMMON_PROB_UPPER_BOUND = COMMON_PROBABILITY_THRESHOLD + common_prob_radius;
    RARE_PROB_UPPER_BOUND = RARE_PROBABILITY_THRESHOLD + rare_prob_radius;
    EPIC_PROB_UPPER_BOUND = EPIC_PROBABILITY_THRESHOLD + epic_prob_radius;
    LEGENDARY_PROB_UPPER_BOUND = LEGENDARY_PROBABILITY_THRESHOLD + legendary_prob_radius;
    COMMON_PROB_LOWER_BOUND = COMMON_PROBABILITY_THRESHOLD - common_prob_radius;
    RARE_PROB_LOWER_BOUND = RARE_PROBABILITY_THRESHOLD - rare_prob_radius;
    EPIC_PROB_LOWER_BOUND = EPIC_PROBABILITY_THRESHOLD - epic_prob_radius;
    LEGENDARY_PROB_LOWER_BOUND = LEGENDARY_PROBABILITY_THRESHOLD - legendary_prob_radius;

    double common_payout_radius = Math.min((common_payout_center - 1.0 / (COMMON_PROB_UPPER_BOUND)), (common_payout_center - 1.0 / (COMMON_PROB_LOWER_BOUND)));
    double rare_payout_radius = Math.min((rare_payout_center - 1.0 / (RARE_PROB_UPPER_BOUND)), (rare_payout_center - 1.0 / (RARE_PROB_LOWER_BOUND)));
    double epic_payout_radius = Math.min((epic_payout_center - 1.0 / (EPIC_PROB_UPPER_BOUND)), (epic_payout_center - 1.0 / (EPIC_PROB_LOWER_BOUND)));
    double legendary_payout_radius = Math.min((legendary_payout_center - 1.0 / (LEGENDARY_PROB_UPPER_BOUND)), (legendary_payout_center - 1.0 / (LEGENDARY_PROB_LOWER_BOUND)));

    COMMON_PAYOUT_UPPER_BOUND = common_payout_center + common_payout_radius;
    RARE_PAYOUT_UPPER_BOUND = rare_payout_center + rare_payout_radius;
    EPIC_PAYOUT_UPPER_BOUND = epic_payout_center + epic_payout_radius;
    LEGENDARY_PAYOUT_UPPER_BOUND = legendary_payout_center + legendary_payout_radius;
    COMMON_PAYOUT_LOWER_BOUND = common_payout_center - common_payout_radius;
    RARE_PAYOUT_LOWER_BOUND = rare_payout_center - rare_payout_radius;
    EPIC_PAYOUT_LOWER_BOUND = epic_payout_center - epic_payout_radius;
    LEGENDARY_PAYOUT_LOWER_BOUND = legendary_payout_center - legendary_payout_radius;
  }

  public Contract generateContract(User user, Player player, Double buyPrice, Rarity rarity) {
    Event event = Event.getRandomEvent();
    OpposingTeam opposingTeam = OpposingTeam.getRandomOpposingTeam();
    Double eventProb = 0.5;
    Double ratio = 2.0;
    generateBounds();
    
    switch (rarity) {
      case Common:
        // Probability of event hitting ranges from COMMON_PROB_LOWER_BOUND - COMMON_PROB_UPPER_BOUND
        eventProb = Math.random() * (COMMON_PROB_UPPER_BOUND - COMMON_PROB_LOWER_BOUND) + COMMON_PROB_LOWER_BOUND;
        // Payout/buy_in ratio ranges from COMMON_PAYOUT_LOWER_BOUND - COMMON_PAYOUT_UPPER_BOUND
        ratio = Math.random() * (COMMON_PAYOUT_UPPER_BOUND - COMMON_PAYOUT_LOWER_BOUND) + COMMON_PAYOUT_LOWER_BOUND;
        break;
      case Rare:
        // Probability of event hitting ranges from RARE_PROB_LOWER_BOUND - RARE_PROB_UPPER_BOUND
        eventProb = Math.random() * (RARE_PROB_UPPER_BOUND - RARE_PROB_LOWER_BOUND) + RARE_PROB_LOWER_BOUND;
        // Payout/buy_in ratio ranges from RARE_PAYOUT_LOWER_BOUND - RARE_PAYOUT_UPPER_BOUND
        ratio = Math.random() * (RARE_PAYOUT_UPPER_BOUND - RARE_PAYOUT_LOWER_BOUND) + RARE_PAYOUT_LOWER_BOUND;
        break;
      case Epic:
        // Probability of event hitting ranges from EPIC_PROB_LOWER_BOUND - EPIC_PROB_UPPER_BOUND
        eventProb = Math.random() * (EPIC_PROB_UPPER_BOUND - EPIC_PROB_LOWER_BOUND) + EPIC_PROB_LOWER_BOUND;
        // Payout/buy_in ratio ranges from EPIC_PAYOUT_LOWER_BOUND - EPIC_PAYOUT_UPPER_BOUND
        ratio = Math.random() * (EPIC_PAYOUT_UPPER_BOUND - EPIC_PAYOUT_LOWER_BOUND) + EPIC_PAYOUT_LOWER_BOUND;
        break;
      case Legendary:
        // Probability of event hitting ranges from LEGENDARY_PROB_LOWER_BOUND - LEGENDARY_PROB_UPPER_BOUND
        eventProb = Math.random() * (LEGENDARY_PROB_UPPER_BOUND - LEGENDARY_PROB_LOWER_BOUND) + LEGENDARY_PROB_LOWER_BOUND;
        // Payout/buy_in ratio ranges from LEGENDARY_PAYOUT_LOWER_BOUND - LEGENDARY_PAYOUT_UPPER_BOUND
        ratio = Math.random() * (LEGENDARY_PAYOUT_UPPER_BOUND - LEGENDARY_PAYOUT_LOWER_BOUND) + LEGENDARY_PAYOUT_LOWER_BOUND;
        break;
      default:
        break;
    }

    Integer eventThreshold = normalizeEventThreshold(player, event, eventProb);

    return new Contract(player, user, buyPrice, rarity, opposingTeam,
        "src/main/resources/static/images/teams/" + opposingTeam + ".png", event,
        eventThreshold, LocalDate.now(),
        buyPrice * ratio, null,
        false, null);
  }
  

  // public Contract generateContract(User user, Player player, Double buyPrice, Rarity rarity) {
  //   // event and opposing_team is randomly picked
  //   // Value should be greater than the buy_price (each rarity has a different
  //   // multiplier with legendary being the highest)
  //   // event_threshold will be determined from player averages

  //   Event event = Event.getRandomEvent();
  //   OpposingTeam opposingTeam = OpposingTeam.getRandomOpposingTeam();

  //   // Probability of event happening * payout/buy_in ratio = alpha
  //   // Lower alpha means more profit for us, higher alpha means more profit for the
  //   // users
  //   Double alpha = 0.95;
  //   Double eventProb = 1.0;
  //   Double ratio = 1.0;

  //   switch (rarity) {
  //     case Common:
  //       // Probability of event hitting ranges from 60-80%
  //       eventProb = Math.random() * 0.2 + 0.6;
  //       ratio = alpha / eventProb;
  //       break;
  //     case Rare:
  //       // Probability of event hitting ranges from 45-65%
  //       eventProb = Math.random() * 0.2 + 0.45;
  //       ratio = alpha / eventProb;
  //       break;
  //     case Epic:
  //       // Probability of event hitting ranges from 30-50%
  //       eventProb = Math.random() * 0.2 + 0.3;
  //       ratio = alpha / eventProb;
  //       break;
  //     case Legendary:
  //       // Probability of event hitting ranges from 15-35%
  //       eventProb = Math.random() * 0.2 + 0.15;
  //       ratio = alpha / eventProb;
  //       break;
  //     default:
  //       break;
  //   }

  //   Integer eventThreshold = normalizeEventThreshold(player, event, eventProb);

  //   return new Contract(player, user, buyPrice, rarity, opposingTeam,
  //       "src/main/resources/static/images/teams/" + opposingTeam + ".png", event,
  //       eventThreshold, LocalDate.now(),
  //       buyPrice * ratio, null,
  //       false, null);
  // }

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
    return new Double[] { eventAvg, eventSD };
  }

  public Integer normalizeEventThreshold(Player player, Event event, Double eventProb) {
    // Normalize the event threshold based on player averages
    Double[] eventMetrics = getPlayerDataByEvent(player, event);
    Double eventAvg = eventMetrics[0];
    Double eventSD = eventMetrics[1];
    NormalDistribution X = new NormalDistribution(eventAvg, Math.pow(eventSD, 2));
    Integer threshold = (int) Math.round(X.inverseCumulativeProbability(1 - eventProb));
    return threshold;
  }
}
