package com.example.allin.data;
import com.example.allin.player.Player;
import com.example.allin.player.Position;

public class PlayerFactory {
  
  public static Player fakeTemplate() {
    return new Player("LeBron", "James", new Position[]{Position.Center}, 23, "6'9", 250, "Akron, OH", "St. Vincent-St. Mary", "src/main/resources/static/images/players/default.jpg");
  }

  public static Player fake() {
    // return a player with random values
    return new Player("LeBron", "James", new Position[]{Position.Center}, 23, "6'9", 250, "Akron, OH", "St. Vincent-St. Mary", "src/main/resources/static/images/players/default.jpg");
  }

}
