package com.appdev.allin.playerData;

import com.appdev.allin.exceptions.NotFoundException;
import java.time.LocalDate;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerDataController {
  public final PlayerDataService playerDataService;

  public PlayerDataController(PlayerDataService playerDataService) {
    this.playerDataService = playerDataService;
  }

  // CRUD operations

  @GetMapping("/playerData/")
  public ResponseEntity<List<PlayerData>> getAllPlayerData() {
    List<PlayerData> playerData = playerDataService.getAllPlayerData();
    return ResponseEntity.ok(playerData);
  }

  @GetMapping("/playerData/{playerDataId}/")
  public ResponseEntity<PlayerData> getPlayerDataById(@PathVariable final Integer playerDataId) {
    try {
      PlayerData playerData = playerDataService.getPlayerDataById(playerDataId);
      return ResponseEntity.ok(playerData);
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/playerData/")
  public ResponseEntity<PlayerData> createPlayerData(@RequestBody final PlayerData playerData) {
    return ResponseEntity.status(201).body(playerDataService.createPlayerData(playerData));
  }

  @PatchMapping("/playerData/{playerDataId}/")
  public ResponseEntity<PlayerData> updatePlayerData(
      @PathVariable final Integer playerDataId, @RequestBody final PlayerData playerData) {
    try {
      PlayerData updatedPlayerData = playerDataService.updatePlayerData(playerDataId, playerData);
      return ResponseEntity.ok(updatedPlayerData);
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/playerData/{playerDataId}/")
  public ResponseEntity<PlayerData> deletePlayerData(@PathVariable final Integer playerDataId) {
    try {
      PlayerData deletedPlayerData = playerDataService.deletePlayerData(playerDataId);
      return ResponseEntity.ok(deletedPlayerData);
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  // Player operations

  @GetMapping("/playerData/player/{playerId}/")
  public ResponseEntity<List<PlayerData>> getPlayerDataByPlayer(
      @PathVariable final Integer playerId) {
    try {
      List<PlayerData> playerData = playerDataService.getPlayerDataByPlayerId(playerId);
      return ResponseEntity.ok(playerData);
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  // Game operations

  @GetMapping("/playerData/game/{gameDate}/")
  public ResponseEntity<List<PlayerData>> getPlayerDataByGame(@PathVariable final String gameDate) {
    try {
      List<PlayerData> playerData =
          playerDataService.getPlayerDataByDate(LocalDate.parse(gameDate));
      return ResponseEntity.ok(playerData);
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
