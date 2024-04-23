package com.example.allin.contract;

public enum Rarity {
  Common,
  Rare,
  Epic,
  Legendary;

  public static Rarity getRandomRarity() {
    // legendary have 5% chance of being selected
    // common has 55%
    // rare has 25%
    // epic has 15
    double random = Math.random();
    if (random < 0.55) {
      return Common;
    } else if (random < 0.8) {
      return Rare;
    } else if (random < 0.95) {
      return Epic;
    }
    else {
      return Legendary;
    }
  }
}
