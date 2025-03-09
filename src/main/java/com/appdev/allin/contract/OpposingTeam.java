package com.appdev.allin.contract;

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
        return values()[(int) (Math.random() * values().length)];
    }

    // Helper method to find matching team
    public boolean matches(String teamName) {
        String normalizedTeamName = teamName.toLowerCase().trim();
        for (String alias : aliases) {
            if (normalizedTeamName.contains(alias.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}