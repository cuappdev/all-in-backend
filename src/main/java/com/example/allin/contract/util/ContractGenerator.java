package com.example.allin.contract.util;

import java.time.LocalDate;

import com.example.allin.contract.Contract;
import com.example.allin.contract.Event;
import com.example.allin.contract.OpposingTeam;
import com.example.allin.player.Player;
import com.example.allin.user.User;
import com.example.allin.contract.Rarity;

import org.apache.commons.math3.distribution.NormalDistribution;

public class ContractGenerator {

  public static Contract generateContract(User user, Player player, Double buyPrice, Rarity rarity) {
    // event and opposing_team is randomly picked
    // Value should be greater than the buy_price (each rarity has a different
    // multiplier with legendary being the highest)
    // event_threshold will be determined from player averages

    Event event = Event.getRandomEvent();
    OpposingTeam opposingTeam = OpposingTeam.getRandomOpposingTeam();

    // Probability of event happening * payout/buy_in ratio = alpha
    // Lower alpha means more profit for us, higher alpha means more profit for the
    // users
    Double alpha = 0.95;
    Double eventProb = 1.0;
    Double ratio = 1.0;

    switch (rarity) {
      case Common:
        // Probability of event hitting ranges from 60-80%
        eventProb = Math.random() * 0.2 + 0.6;
        ratio = alpha / eventProb;
        break;
      case Rare:
        // Probability of event hitting ranges from 45-65%
        eventProb = Math.random() * 0.2 + 0.45;
        ratio = alpha / eventProb;
        break;
      case Epic:
        // Probability of event hitting ranges from 30-50%
        eventProb = Math.random() * 0.2 + 0.3;
        ratio = alpha / eventProb;
        break;
      case Legendary:
        // Probability of event hitting ranges from 15-35%
        eventProb = Math.random() * 0.2 + 0.15;
        ratio = alpha / eventProb;
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

  public static Integer normalizeEventThreshold(Player player, Event event, Double eventProb) {
    // Normalize the event threshold based on player averages
    Double eventAvg = 0.0;
    Double eventSD = 1.0;
    NormalDistribution X = new NormalDistribution(eventAvg, eventSD);
    Integer threshold = (int) Math.round(X.inverseCumulativeProbability(1 - eventProb));
    return threshold;

  }
}
