package com.appdev.allin.gameData;
import org.springframework.stereotype.Service;

@Service
public class GameDataService {
  private final GameDataRepo gameDataRepo;

  public GameDataService(GameDataRepo gameDataRepo) {
    this.gameDataRepo = gameDataRepo;
  }

  public GameData findByOpposingTeamAndGameDateTime(String opposingTeam, String gameDateTime) {
    return gameDataRepo.findByOpposingTeamAndGameDateTime(opposingTeam, gameDateTime);
  }

  public GameData saveGameData(GameData gameData) {
    return gameDataRepo.save(gameData);
  }
}
