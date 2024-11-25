package com.appdev.allin.contract;

import java.util.concurrent.ThreadLocalRandom;

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
    return values()[(int) (ThreadLocalRandom.current().nextDouble() * values().length)];
  }
}
