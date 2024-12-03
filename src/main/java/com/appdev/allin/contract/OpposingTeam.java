package com.appdev.allin.contract;

import java.util.concurrent.ThreadLocalRandom;

public enum OpposingTeam {
  Lehigh,
  Morrisville,
  Fordham,
  George,
  Fullerton,
  Utah,
  Monmouth,
  Lafayette,
  Syracuse,
  Siena,
  Robert,
  Colgate,
  Baylor,
  Columbia,
  Penn,
  Brown,
  Wells,
  Princeton,
  Dartmouth,
  Harvard,
  Yale,
  Ohio;

  public static OpposingTeam getRandomOpposingTeam() {
    return values()[(int) (ThreadLocalRandom.current().nextDouble() * values().length)];
  }
}
