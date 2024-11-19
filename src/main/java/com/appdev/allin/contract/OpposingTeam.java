package com.appdev.allin.contract;

public enum OpposingTeam {
    Baylor("Baylor"),
    Brown("Brown"),
    Colgate("Colgate"),
    Columbia("Columbia"),
    Dartmouth("Dartmouth"),
    Fordham("Fordham"),
    Fullerton("Fullerton"),
    George("George"),
    Harvard("Harvard"),
    Lafayette("Lafayette"),
    LaSalle("LaSalle"),
    Lehigh("Lehigh"),
    Marywood("Marywood"),
    Monmouth("Monmouth"),
    Morrisville("Morrisville"),
    Ohio("Ohio"),
    Penn("Penn"),
    Princeton("Princeton"),
    Robert("Robert"),
    Samford("Samford"),
    Siena("Siena"),
    Syracuse("Syracuse"),
    Utah("Utah"),
    Wells("Wells"),
    Yale("Yale"),
    BINGHAMTON("Binghamton"),
    BOSTON_COLLEGE("Boston College"),
    BRYANT("Bryant"),
    CANISIUS("Canisius"),
    COPPIN_ST("Coppin St."),
    COPPIN_STATE("Coppin State"),
    DEPAUL("DePaul"),
    DELAWARE("Delaware"),
    ELMIRA("Elmira"),
    HARTFORD("Hartford"),
    ITHACA("Ithaca"),
    JOHNSON__WALES("Johnson & Wales"),
    KEUKA("Keuka"),
    LONGWOOD("Longwood"),
    MIAMI_FL("Miami (FL)"),
    NJIT("NJIT"),
    NAVY("Navy"),
    NIAGARA("Niagara"),
    PURCHASE("Purchase"),
    SMU("SMU"),
    SUNY_CANTON("SUNY Canton"),
    SUNY_DELHI("SUNY Delhi"),
    SAINT_FRANCIS_PA("Saint Francis (PA)"),
    TOLEDO("Toledo"),
    TOWSON("Towson"),
    UCONN("UConn"),
    VIRGINIA_TECH("Virginia Tech"),
    WAKE_FOREST("Wake Forest");

    private final String[] aliases;

    OpposingTeam(String... aliases) {
        this.aliases = aliases;
    }

    public String[] getAliases() {
        return aliases;
    }

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