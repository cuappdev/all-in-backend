package com.appdev.allin.data;
import com.appdev.allin.player.Player;
import com.appdev.allin.player.Position;
import com.github.javafaker.Faker;
import java.util.Random;


public class PlayerFactory {
  private static final Faker faker = new Faker();
  private static final Random random = new Random();


  public static Player createFakePlayer() {
    String firstName = faker.name().firstName();
    String lastName = faker.name().lastName();
    Position[] positions = generateRandomPositions();
    Integer number = faker.number().numberBetween(1, 99);  // Jersey numbers typically range from 1-99
    String height = generateRandomHeight();
    Integer weight = faker.number().numberBetween(150, 250);  // Weight in pounds, adjustable as needed
    String hometown = faker.address().city() + ", " + faker.address().state();
    String highSchool = faker.educator().secondarySchool();
     return new Player(firstName, lastName, positions, number, height, weight, hometown, highSchool);
    }

  private static Position[] generateRandomPositions() {
      Position[] allPositions = Position.values();
      int count = random.nextInt(2) + 1; // Players can have 1 or 2 positions
      Position[] positions = new Position[count];
      for (int i = 0; i < count; i++) {
          positions[i] = allPositions[random.nextInt(allPositions.length)];
      }
      return positions;
  }

  private static String generateRandomHeight() {
    int feet = faker.number().numberBetween(5, 7); // Heights between 5 and 7 feet
    int inches = faker.number().numberBetween(0, 11);
    return feet + "'" + inches + "\"";
}

}
