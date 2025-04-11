package com.appdev.allin.user;

import com.appdev.allin.exceptions.ForbiddenException;
import com.appdev.allin.exceptions.NotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepo userRepo;

  public UserService(UserRepo userRepo) {
    this.userRepo = userRepo;
  }

  public Page<User> getAllUsers(final Pageable pageable) {
    return userRepo.findAll(pageable);
  }

  public User getUserByUid(final String uid) {
    User user = userRepo.findById(uid)
        .orElseThrow(() -> new NotFoundException("User with id " + uid + " not found."));
    return user;
  }

  public Integer getUserRank(final User user) {
    return userRepo.countByBalanceGreaterThan(user.getBalance()) + 1;
  }

  public User createUser(final User user) {
    userRepo.findByUsername(user.getUsername())
        .orElseThrow(() -> new ForbiddenException("Username " + user.getUsername() + " already exists."));

    userRepo.findByEmail(user.getEmail())
        .orElseThrow(() -> new ForbiddenException("Email " + user.getEmail() + " already exists."));
    
    return userRepo.save(user);
  }

  public User updateUser(final User user, final User updatedUser) {
    user.setUsername(updatedUser.getUsername());
    user.setImage(updatedUser.getImage());
    return userRepo.save(user);
  }

  public User addToUserBalance(final User user, Integer amount) {
    user.setBalance(user.getBalance() + amount);
    return userRepo.save(user);
  }

  public User deleteUser(final User user) {
    userRepo.delete(user);
    return user;
  }
}
