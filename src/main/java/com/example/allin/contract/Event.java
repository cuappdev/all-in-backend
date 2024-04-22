package com.example.allin.contract;

public enum Event {
  Points,
  Minutes,
  Field_Goals,
  Three_Pointers,
  Free_Throws,
  Rebounds,
  Assists,
  Steals,
  Blocks,
  Turnovers,
  Fouls;

  public static Event getRandomEvent() {
    return values()[(int) (Math.random() * values().length)];
  }
}
