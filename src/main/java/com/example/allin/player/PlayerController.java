package com.example.allin.player;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;

@RestController
public class PlayerController {
  public final PlayerService playerService;

  public PlayerController(PlayerService playerService) {
    this.playerService = playerService;
  }

  @GetMapping("/players/")
  public ResponseEntity<List<Player>> getAllPlayers() {
    List<Player> players = playerService.getAllPlayers();
    return ResponseEntity.ok(players);
  }

  @GetMapping("/players/{playerId}/")
  public ResponseEntity<Player> getPlayerById(@PathVariable final Integer playerId) {
    try {
      Player player = playerService.getPlayerById(playerId);
      return ResponseEntity.ok(player);
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/players/")
  public ResponseEntity<Player> createPlayer(@RequestBody final Player player) {
    return ResponseEntity.status(201).body(playerService.createPlayer(player));
  }

  @PatchMapping("/players/{playerId}/")
  public ResponseEntity<Player> updatePlayer(@PathVariable final Integer playerId,
      @RequestBody final Player player) {
    try {
      Player updatedPlayer = playerService.updatePlayer(playerId, player);
      return ResponseEntity.ok(updatedPlayer);
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/players/{playerId}/")
  public ResponseEntity<Player> deletePlayer(@PathVariable final Integer playerId) {
    try {
      return ResponseEntity.ok(playerService.deletePlayer(playerId));
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/players/{playerId}/image/")
  public ResponseEntity<byte[]> getImageFromStorage(@PathVariable final Integer playerId) throws NotFoundException{
    try {
      Player player = playerService.getPlayerById(playerId);
      String currentDirectory = player.getImage();
      String imageName = currentDirectory.substring(currentDirectory.lastIndexOf("/") + 1);
      currentDirectory = currentDirectory.replace(imageName, "");
      byte[] image = playerService.getImageFromStorage(currentDirectory, imageName);
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.IMAGE_JPEG);
      return new ResponseEntity<>(image, headers, HttpStatus.OK);
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PatchMapping("/players/{playerId}/image/")
  public ResponseEntity<String> updatePlayerImageById(@PathVariable final Integer playerId,
      @RequestBody final MultipartFile image){
    String uploadDirectory = "src/main/resources/static/images/players/";
    try {
      playerService.updatePlayerImageById(playerId, image, uploadDirectory);
      return ResponseEntity.ok("Image uploaded successfully!");
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/players/{playerId}/image/")
  public ResponseEntity<String> deletePlayerImageById(@PathVariable final Integer playerId){
    String uploadDirectory = "src/main/resources/static/images/players/";
    try {
      if (playerService.deletePlayerImageById(playerId, uploadDirectory)) {
        return ResponseEntity.ok("Image deleted successfully");
      }
      return ResponseEntity.notFound().build();
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

}