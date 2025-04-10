package com.appdev.allin.factories;

import com.appdev.allin.user.User;
import com.github.javafaker.Faker;

public class UserFactory {
  private static final Faker faker = new Faker();

  public static User createRandomUser() {
    String uid = faker.idNumber().valid();
    String username = faker.name().username();
    String email = faker.internet().emailAddress();
    String image = faker.internet().avatar();

    return new User(uid, username, email, image);
  }
}
