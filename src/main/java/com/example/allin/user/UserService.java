package com.example.allin.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    /**
     * @return all users
     */
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = new ArrayList<User>();
        users.add(new User("email", "pass", "image", 1.0, "session", "refresh", true));
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    /**
     * @param id (String): the id of the user
     * @return the user with the given id
     */
    public ResponseEntity<User> getUserById(final String id) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(new User("email", "pass", "image", 1.0, "session", "refresh", true));
    }

    /**
     * @param user (User): The user object to create
     * @return the user object created
     */
    public ResponseEntity<User> createUser(final User user) {
        System.out.println(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    /**
     * @param id (String): The id of the user to update
     * @param user (User): The updated user
     * @return the user object updated
     */
    public ResponseEntity<User> updateUser(final String id, final User user) {
        System.out.println(user);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    /**
     * @param id (String): The id of the user to delete
     * @return the user was deleted
     */
    public ResponseEntity<String> deleteUser(final String id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User deleted");
    }
}
