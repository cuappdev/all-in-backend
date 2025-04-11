package com.appdev.allin.gameData;

import com.appdev.allin.utils.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gamedata")
public class GameDataController {

  private final GameDataService gameDataService;

  public GameDataController(GameDataService gameDataService) {
    this.gameDataService = gameDataService;
  }

  @GetMapping("/")
    public ResponseEntity<Page<GameData>> getAllGameData(
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "10") Integer size,
        @RequestParam(defaultValue = "gameDateTime") String sortBy,
        @RequestParam(defaultValue = "desc") String direction) {

        Pageable pageable = Pagination.generatePageable(page, size, sortBy, direction);
        Page<GameData> paginated = gameDataService.getAllGameData(pageable);
        return ResponseEntity.ok(paginated);
    }

  @GetMapping("/{gameData_id}/")
    public ResponseEntity<GameData> getGameDataById(@PathVariable final Integer id) {
        GameData gameData = gameDataService.getGameDataById(id);
        return ResponseEntity.ok(gameData);
    }

  @PostMapping("/")
    public ResponseEntity<GameData> createGameData(@RequestBody final GameData gameData) {
        GameData created = gameDataService.createGameData(gameData);
        return ResponseEntity.status(201).body(created);
    }

  @PatchMapping("/{gameData_id}/")
    public ResponseEntity<GameData> updateGameData(
        @PathVariable final Integer id, @RequestBody final GameData updatedData) {
        GameData updated = gameDataService.updateGameData(id, updatedData);
        return ResponseEntity.ok(updated);
    }

  @DeleteMapping("/{gameData_id}/")
    public ResponseEntity<Void> deleteGameData(@PathVariable final Integer id) {
        gameDataService.deleteGameData(id);
        return ResponseEntity.noContent().build();
    }

  @GetMapping("/gameData/find/")
    public ResponseEntity<GameData> findByOpposingTeamAndGameDateTime(
        @RequestParam String opposingTeam, @RequestParam String gameDateTime) {
        GameData gameData = gameDataService.findByOpposingTeamAndGameDateTime(opposingTeam, gameDateTime);
        return ResponseEntity.ok(gameData);
    }

  @PostMapping("/gameData/save/")
    public ResponseEntity<GameData> saveGameData(@RequestBody GameData gameData) {
        GameData savedGameData = gameDataService.saveGameData(gameData);
        return ResponseEntity.status(201).body(savedGameData);
    }
}