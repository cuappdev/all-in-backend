package com.appdev.allin.player;

import com.appdev.allin.contract.Contract;
import com.appdev.allin.contract.ContractService;
import com.appdev.allin.exceptions.NotFoundException;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class PlayerController {

  public final PlayerService playerService;
  public final ContractService contractService;

  public PlayerController(PlayerService playerService, ContractService contractService) {
    this.playerService = playerService;
    this.contractService = contractService;
  }

  // CRUD operations

  @GetMapping("/players/")
  public ResponseEntity<List<Player>> getAllPlayers() {
    List<Player> players = playerService.getAllPlayers();
    return ResponseEntity.ok(players);
  }

  @GetMapping("/players/{player_id}/")
  public ResponseEntity<Player> getPlayerById(@PathVariable final Integer player_id) {
    try {
      Player player = playerService.getPlayerById(player_id);
      return ResponseEntity.ok(player);
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/players/")
  public ResponseEntity<Player> createPlayer(@RequestBody final Player player) {
    return ResponseEntity.status(201).body(playerService.createPlayer(player));
  }

  @PatchMapping("/players/{player_id}/")
  public ResponseEntity<Player> updatePlayer(
      @PathVariable final Integer player_id, @RequestBody final Player player) {
    try {
      Player updatedPlayer = playerService.updatePlayer(player_id, player);
      return ResponseEntity.ok(updatedPlayer);
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/players/{player_id}/")
  public ResponseEntity<Player> deletePlayer(@PathVariable final Integer player_id) {
    try {
      return ResponseEntity.ok(playerService.deletePlayer(player_id));
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  // Contracts Operations

  @GetMapping("/players/{player_id}/contracts/")
  public ResponseEntity<List<Contract>> getContractsByPlayerId(
      @PathVariable final Integer player_id) {
    try {
      List<Contract> contracts = contractService.getContractsByPlayerId(player_id);
      return ResponseEntity.ok(contracts);
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  // Images Operations

  @GetMapping("/players/{player_id}/image/")
  public ResponseEntity<byte[]> getPlayerImageById(@PathVariable final Integer player_id)
      throws NotFoundException {
    try {
      Player player = playerService.getPlayerById(player_id);
      String currentDirectory = player.getImage();
      String imageName = currentDirectory.substring(currentDirectory.lastIndexOf('/') + 1);
      currentDirectory = currentDirectory.replace(imageName, "");
      byte[] image = playerService.getImageFromStorage(currentDirectory, imageName);
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.IMAGE_JPEG);
      return new ResponseEntity<>(image, headers, HttpStatus.OK);
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PatchMapping("/players/{player_id}/image/")
  public ResponseEntity<String> updatePlayerImageById(
      @PathVariable final Integer player_id, @RequestBody final MultipartFile image) {
    String uploadDirectory = "src/main/resources/static/images/players/";
    try {
      playerService.updatePlayerImageById(player_id, image, uploadDirectory);
      return ResponseEntity.ok("Image uploaded successfully!");
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/players/{player_id}/image/")
  public ResponseEntity<String> deletePlayerImageById(@PathVariable final Integer player_id) {
    String uploadDirectory = "src/main/resources/static/images/players/";
    try {
      if (playerService.deletePlayerImageById(player_id, uploadDirectory)) {
        return ResponseEntity.ok("Image deleted successfully");
      }
      return ResponseEntity.notFound().build();
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
