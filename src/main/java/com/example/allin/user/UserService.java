package com.example.allin.user;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.allin.exceptions.NotFoundException;

@Service
public class UserService {
  private final UserRepo userRepo;

  public UserService(UserRepo userRepo) {
    this.userRepo = userRepo;
  }

  public List<User> getAllUsers() {
    return userRepo.findAll();
  }

  public User getUserById(final Integer user_id) throws NotFoundException {
    Optional<User> userOptional = userRepo.findById(user_id);
    if (userOptional.isEmpty()) {
      throw new NotFoundException();
    }
    return userOptional.get();
  }

  public User createUser(final User user) {
    return userRepo.save(user);
  }

  public User updateUser(final Integer user_id, final User user) throws NotFoundException {
    Optional<User> userOptional = userRepo.findById(user_id);
    if (userOptional.isEmpty()) {
      throw new NotFoundException();
    }
    User userToUpdate = userOptional.get();
    userToUpdate.setEmail(user.getEmail());
    userToUpdate.setHashedPassword(user.getHashedPassword());
    userToUpdate.setBalance(user.getBalance());
    userToUpdate.setIsAdmin(user.getIsAdmin());
    return userRepo.save(userToUpdate);
  }

  public User deleteUser(final Integer user_id) throws NotFoundException {
    Optional<User> userOptional = userRepo.findById(user_id);
    if (userOptional.isEmpty()) {
      throw new NotFoundException();
    }
    userRepo.deleteById(user_id);
    return userOptional.get();
  }
}