package com.appdev.allin.data;
import com.appdev.allin.user.User;
import com.github.javafaker.Faker;

public class UserFactory {
    private static final Faker faker = new Faker();

    public static User createRandomUser() {
        String username = faker.name().username();
        String email = faker.internet().emailAddress();
        String image = faker.internet().avatar();
        Double balance = faker.number().randomDouble(2, 10, 1000);

        return new User(username, email, image, balance);
    }
}
