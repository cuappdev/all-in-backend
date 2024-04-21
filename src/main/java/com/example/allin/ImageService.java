package com.example.allin;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;
import java.nio.file.*;

@Service
public class ImageService {
  public String saveImageToStorage(String uploadDirectory, MultipartFile image) {
    String uniqueFileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
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
      Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return uniqueFileName;
  }

  public byte[] getImageFromStorage(String uploadDirectory, String fileName) {
    Path uploadPath = Path.of(uploadDirectory);
    Path filePath = uploadPath.resolve(fileName);
    try {
      return Files.readAllBytes(filePath);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public boolean deleteImageFromStorage(String uploadDirectory, String fileName) {
    Path uploadPath = Path.of(uploadDirectory);
    Path filePath = uploadPath.resolve(fileName);
    try {
      Files.delete(filePath);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean updateImageInStorage(String uploadDirectory, MultipartFile image, String oldFileName) {
    if (deleteImageFromStorage(uploadDirectory, oldFileName)) {
      saveImageToStorage(uploadDirectory, image);
      return true;
    }
    return false;
  }
}
