package com.appdev.allin.gameData;

import com.appdev.allin.exceptions.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GameDataService {
    private final GameDataRepo gameDataRepo;

  public GameDataService(GameDataRepo gameDataRepo) {
    this.gameDataRepo = gameDataRepo;
  }

  public Page<GameData> getAllGameData(final Pageable pageable) {
    return gameDataRepo.findAll(pageable);
  }

  public GameData getGameDataById(final Integer id) {
    return gameDataRepo.findById(id)
        .orElseThrow(() -> new NotFoundException("GameData with id " + id + " not found."));
  }

  public GameData createGameData(final GameData data) {
    return gameDataRepo.save(data);
  }

  public GameData updateGameData(final Integer id, final GameData newData) {
    GameData existing = gameDataRepo.findById(id)
        .orElseThrow(() -> new NotFoundException("GameData with id " + id + " not found."));
    existing.setOpposingTeam(newData.getOpposingTeam());
    existing.setGameDateTime(newData.getGameDateTime());
    existing.setFullLocation(newData.getFullLocation());
    existing.setLogoUrl(newData.getLogoUrl());
    return gameDataRepo.save(existing);
  }

  public void deleteGameData(final Integer id) {
    GameData existing = gameDataRepo.findById(id)
        .orElseThrow(() -> new NotFoundException("GameData with id " + id + " not found."));
    gameDataRepo.delete(existing);
  }

  public GameData findByOpposingTeamAndGameDateTime(String opposingTeam, String gameDateTime) {
    return gameDataRepo.findByOpposingTeamAndGameDateTime(opposingTeam, gameDateTime);
  }

  public GameData saveGameData(GameData gameData) {
    return gameDataRepo.save(gameData);
  }
}