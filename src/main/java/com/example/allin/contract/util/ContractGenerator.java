package com.example.allin.contract.util;

import java.time.LocalDate;

import com.example.allin.contract.Contract;
import com.example.allin.contract.Event;
import com.example.allin.contract.OpposingTeam;
import com.example.allin.player.Player;
import com.example.allin.user.User;
import com.example.allin.contract.Rarity;

public class ContractGenerator {
  // opposing_team will be randomly picked
  // Randomly pick event
  // Value should be greater than the buy_price (each rarity has a different
  // multiplier with legendary being the highest)
  // event_threshold will be determined from player averages

  public static Contract generateContract(User user, Player player, Double buyPrice, Rarity rarity) {
    return new Contract(player, user, buyPrice, rarity, OpposingTeam.Baylor, "asd", Event.Points, 1, LocalDate.now(),
        buyPrice, null,
        false, buyPrice);
  }
}
