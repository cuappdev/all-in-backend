package com.appdev.allin.contract;

import java.util.concurrent.ThreadLocalRandom;

public enum OpposingTeam {
    LEHIGH,
    MORRISVILLE,
    FORDHAM,
    GEORGE,
    FULLERTON,
    UTAH,
    MONMOUTH,
    LAFAYETTE,
    SYRACUSE,
    SIENA,
    ROBERT,
    COLGATE,
    BAYLOR,
    COLUMBIA,
    PENN,
    BROWN,
    WELLS,
    PRINCETON,
    DARTMOUTH,
    HARVARD,
    YALE,
    OHIO;

    public static OpposingTeam getRandomOpposingTeam() {
        return values()[(int) (ThreadLocalRandom.current().nextDouble() * values().length)];
    }

    // Helper method to find matching team
    public boolean matches(String teamName) {
        return this.name().equalsIgnoreCase(teamName);
    }
}
