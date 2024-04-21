package com.example.allin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class ImageController {
  
  @Autowired
  private ImageService imageService;

  @PostMapping("/image/upload/")
  public String uploadImage(@RequestParam("image") MultipartFile[] image) {
    String uploadDirectory = "src/main/resources/images/";
    String imagesString = "";
    for (MultipartFile img : image) {
      imagesString += imageService.saveImageToStorage(uploadDirectory, img) + " ";
    }
    return imagesString;
  }
  
  @PostMapping("/image/delete/")
  public String deleteImage(@RequestParam("image") String image) {
    String uploadDirectory = "src/main/resources/images/";
    if (imageService.deleteImageFromStorage(uploadDirectory, image)) {
      return "Image deleted successfully";
    }
    return "Image not found";
  }

  @PostMapping("/image/update/")
  public String updateImage(@RequestParam("image") MultipartFile image, @RequestParam("oldImage") String oldImage) {
    String uploadDirectory = "src/main/resources/images/";
    if (imageService.updateImageInStorage(uploadDirectory, image, oldImage)) {
      return "Image updated successfully";
    }
    return "Image not found";
  }
}
