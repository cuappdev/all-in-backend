package com.appdev.allin.user;

import com.appdev.allin.exceptions.ForbiddenException;
import com.appdev.allin.exceptions.NotFoundException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepo userRepo;

  public UserService(UserRepo userRepo) {
    this.userRepo = userRepo;
  }

  public Page<User> getAllUsers(Integer page, Integer size, Sort sort) {
    if (page < 0 || size <= 0) {
      throw new IllegalArgumentException("Page and size must be greater than 0");
    }
    Pageable pageable = PageRequest.of(page, size, sort);
    return userRepo.findAll(pageable);
  }

  public Optional<User> getUserByUid(final String uid) {
    return userRepo.findByUid(uid);
  }

  public User getUserByUidOrThrow(final String uid) {
    User user = userRepo.findByUid(uid)
        .orElseThrow(() -> new NotFoundException("User with id " + uid + " not found."));
    return user;
  }

  public User createUser(final User user) {
    User existingUsernameUser = userRepo.findByUsername(user.getUsername());
    if (existingUsernameUser != null) {
      throw new ForbiddenException("Username " + user.getUsername() + " already exists.");
    }

    User existingEmailUser = userRepo.findByEmail(user.getEmail());
    if (existingEmailUser != null) {
      throw new ForbiddenException("Email " + user.getEmail() + " already exists.");
    }
    return userRepo.save(user);
  }

  public User updateUserByUid(final String uid, final User updatedUser) {
    User user = getUserByUidOrThrow(uid);
    user.setUsername(updatedUser.getUsername());
    user.setImage(updatedUser.getImage());
    return userRepo.save(user);
  }

  public User addToUserBalance(final String uid, Integer amount) {
    User user = getUserByUidOrThrow(uid);
    user.setBalance(user.getBalance() + amount);
    return userRepo.save(user);
  }

  public User deleteUser(final String uid) {
    User user = getUserByUidOrThrow(uid);
    userRepo.deleteById(uid);
    return user;
  }
}
