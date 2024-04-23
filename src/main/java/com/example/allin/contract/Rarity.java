package com.example.allin.contract;

public enum Rarity {
  Common,
  Rare,
  Epic,
  Legendary;

  public static Rarity getRandomRarity() {
    return values()[(int) (Math.random() * values().length)];
  }
}
