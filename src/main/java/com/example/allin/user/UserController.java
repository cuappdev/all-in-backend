package com.example.allin.user;

import org.springframework.web.bind.annotation.RestController;

import com.example.allin.exceptions.NotFoundException;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/users/")
  public ResponseEntity<List<User>> getAllUsers() {
    List<User> users = userService.getAllUsers();
    return ResponseEntity.ok(users);
  }

  @GetMapping("/user/{user_id}/")
  public ResponseEntity<User> getUserById(@PathVariable final Integer user_id) {
    try {
      User user = userService.getUserById(user_id);
      return ResponseEntity.ok(user);
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/users/")
  public ResponseEntity<User> createUser(@RequestBody final User user) {
    return ResponseEntity.status(201).body(userService.createUser(user));
  }

  @PatchMapping("/user/{user_id}/")
  public ResponseEntity<User> updateUser(@PathVariable final Integer user_id, @RequestBody final User user) {
    try {
      User updatedUser = userService.updateUser(user_id, user);
      return ResponseEntity.ok(updatedUser);
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/user/{user_id}/")
  public ResponseEntity<User> deleteUser(@PathVariable final Integer user_id) {
    try {
      User deletedUser = userService.deleteUser(user_id);
      return ResponseEntity.ok(deletedUser);
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

}
