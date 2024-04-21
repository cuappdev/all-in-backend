package com.example.allin.user;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.allin.exceptions.NotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class UserService {
  private final UserRepo userRepo;

  public String defaultImage = "src/main/resources/static/images/users/default.jpg";

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
    userToUpdate.setUsername(user.getUsername());
    userToUpdate.setEmail(user.getEmail());
    userToUpdate.setBalance(user.getBalance());
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

  public byte[] getImageFromStorage(final String uploadDirectory, final String fileName) {
    Path uploadPath = Path.of(uploadDirectory);
    Path filePath = uploadPath.resolve(fileName);
    try {
      return Files.readAllBytes(filePath);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public void updateUserImageById(final Integer user_id, final MultipartFile image, final String uploadDirectory) throws NotFoundException {
    Optional<User> userOptional = userRepo.findById(user_id);
    if (userOptional.isEmpty()) {
      throw new NotFoundException();
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
      Files.copy(image.getInputStream(), filePath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
    } catch (Exception e) {
      e.printStackTrace();
    }
    userToUpdate.setImage(uploadDirectory + uniqueFileName);
    userRepo.save(userToUpdate);
  }

  public boolean deleteUserImageById(final Integer user_id, final String uploadDirectory) throws NotFoundException{
    Optional<User> userOptional = userRepo.findById(user_id);
    if (userOptional.isEmpty()) {
      throw new NotFoundException();
    }
    User userToUpdate = userOptional.get();
    String image = userToUpdate.getImage();
    if (image.equals(defaultImage)) {
      return false;
    }
    Path pathToFile = Path.of(userToUpdate.getImage());
    try {
      Files.delete(pathToFile);
      userToUpdate.setImage("src/main/resources/static/images/users/default.jpg");
      userRepo.save(userToUpdate);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }
}