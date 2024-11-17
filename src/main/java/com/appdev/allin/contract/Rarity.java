package com.appdev.allin.contract;

import java.util.concurrent.ThreadLocalRandom;

public enum Rarity {
    COMMON,
    RARE,
    EPIC,
    LEGENDARY;

    public static Rarity getRandomRarity() {
        double random = ThreadLocalRandom.current().nextDouble();
        if (random < 0.55) {
            return COMMON;
        } else if (random < 0.8) {
            return RARE;
        } else if (random < 0.95) {
            return EPIC;
        } else {
            return LEGENDARY;
        }
    }
}
