package com.appdev.allin.contract;

import java.util.concurrent.ThreadLocalRandom;

public enum Event {
    POINTS,
    MINUTES,
    FIELD_GOALS,
    THREE_POINTERS,
    FREE_THROWS,
    REBOUNDS,
    ASSISTS,
    STEALS,
    BLOCKS,
    TURNOVERS,
    FOULS;

    public static Event getRandomEvent() {
        return values()[(int) (ThreadLocalRandom.current().nextDouble() * values().length)];
    }
}
