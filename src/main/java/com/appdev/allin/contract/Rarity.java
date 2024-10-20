package com.appdev.allin.contract;

public enum Rarity {
    Common,
    Rare,
    Epic,
    Legendary;

    public static Rarity getRandomRarity() {
        double random = Math.random();
        if (random < 0.55) {
            return Common;
        } else if (random < 0.8) {
            return Rare;
        } else if (random < 0.95) {
            return Epic;
        } else {
            return Legendary;
        }
    }
}
