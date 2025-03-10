package com.appdev.allin.user;

import com.appdev.allin.exceptions.ForbiddenException;
import com.appdev.allin.exceptions.NotFoundException;
import com.appdev.allin.utils.Constants;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
      throw new NotFoundException("User with id " + user_id + " not found.");
    }
    return userOptional.get();
  }

  public User createUser(final User user) throws ForbiddenException {
    User existingUser = userRepo.findByUsername(user.getUsername());
    if (existingUser != null) {
      if (existingUser.getEmail().equals(user.getEmail())) {
        return existingUser;
      } else {
        throw new ForbiddenException("Username " + user.getUsername() + " already exists.");
      }
    }
    return userRepo.save(user);
  }

    public User updateUser(final Integer user_id, final User user) throws NotFoundException {
        Optional<User> userOptional = userRepo.findById(user_id);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("User with id " + user_id + " not found.");
        }
        User userToUpdate = userOptional.get();
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setBalance(user.getBalance());
        return userRepo.save(userToUpdate);
    }

    public User addToUserBalance(Integer userId, Double amount) throws NotFoundException {
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("User with id " + userId + " not found.");
        }
        User user = userOptional.get();
        user.setBalance(user.getBalance() + amount);
        return userRepo.save(user);
    }

  public User deleteUser(final Integer user_id) throws NotFoundException {
    Optional<User> userOptional = userRepo.findById(user_id);
    if (userOptional.isEmpty()) {
      throw new NotFoundException("User with id " + user_id + " not found.");
    }
    userRepo.deleteById(user_id);
    return userOptional.get();
  }

  public byte[] getUserImageById(final String uploadDirectory, final String fileName) {
    Path uploadPath = Path.of(uploadDirectory);
    Path filePath = uploadPath.resolve(fileName);
    try {
      return Files.readAllBytes(filePath);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public byte[] updateUserImageById(
      final Integer user_id, final MultipartFile image, final String uploadDirectory)
      throws NotFoundException {
    Optional<User> userOptional = userRepo.findById(user_id);
    if (userOptional.isEmpty()) {
      throw new NotFoundException("User with id " + user_id + " not found.");
    }
    User userToUpdate = userOptional.get();
    String uniqueFileName = user_id + "_" + image.getOriginalFilename();
    Path uploadPath = Path.of(uploadDirectory);
    Path filePath = uploadPath.resolve(uniqueFileName);
    if (!Files.exists(uploadPath)) {
      try {
        Files.createDirectories(uploadPath);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    try {
      Files.copy(
          image.getInputStream(), filePath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
    } catch (Exception e) {
      e.printStackTrace();
    }
    userToUpdate.setImage(uploadDirectory + uniqueFileName);
    userRepo.save(userToUpdate);
    return getUserImageById(uploadDirectory, uniqueFileName);
  }

  public byte[] deleteUserImageById(final Integer user_id, final String uploadDirectory)
      throws NotFoundException, ForbiddenException {
    Optional<User> userOptional = userRepo.findById(user_id);
    if (userOptional.isEmpty()) {
      throw new NotFoundException("User with id " + user_id + " not found.");
    }
    User userToUpdate = userOptional.get();
    String image = userToUpdate.getImage();
    if (image.equals(Constants.DEFAULT_USER_IMAGE)) {
      throw new ForbiddenException("Cannot delete default image");
    }
    Path pathToFile = Path.of(userToUpdate.getImage());
    try {
      byte[] deletedImage = Files.readAllBytes(pathToFile);
      Files.delete(pathToFile);
      userToUpdate.setImage(Constants.DEFAULT_USER_IMAGE);
      userRepo.save(userToUpdate);
      return deletedImage;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
