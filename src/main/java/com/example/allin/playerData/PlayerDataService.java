package com.example.allin.playerData;

import java.time.LocalDate;

import com.example.allin.player.Player;
import com.example.allin.player.PlayerRepo;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.example.allin.exceptions.NotFoundException;

@Service
public class PlayerDataService {
  private final PlayerDataRepo playerDataRepo;
  private final PlayerRepo playerRepo;

  public PlayerDataService(PlayerDataRepo playerDataRepo, PlayerRepo playerRepo) {
    this.playerDataRepo = playerDataRepo;
    this.playerRepo = playerRepo;
  }

  public List<PlayerData> getAllPlayerData() {
    return playerDataRepo.findAll();
  }

  public PlayerData getPlayerDataById(final Integer playerDataId) throws NotFoundException {
    Optional<PlayerData> playerDataOptional = playerDataRepo.findById(playerDataId);
    if (playerDataOptional.isEmpty()) {
      throw new NotFoundException("Player data with id " + playerDataId + " not found.");
    }
    return playerDataOptional.get();
  }

  public PlayerData createPlayerData(final PlayerData playerData) {
    return playerDataRepo.save(playerData);
  }

  public PlayerData updatePlayerData(final Integer playerDataId, final PlayerData playerData) throws NotFoundException {
    Optional<PlayerData> playerDataOptional = playerDataRepo.findById(playerDataId);
    if (playerDataOptional.isEmpty()) {
      throw new NotFoundException("Player data with id " + playerDataId + " not found.");
    }
    PlayerData playerDataToUpdate = playerDataOptional.get();
    playerDataToUpdate.setGameDate(playerData.getGameDate());
    playerDataToUpdate.setOpposingTeam(playerData.getOpposingTeam());
    playerDataToUpdate.setPoints(playerData.getPoints());
    playerDataToUpdate.setMinutes(playerData.getMinutes());
    playerDataToUpdate.setFieldGoals(playerData.getFieldGoals());
    playerDataToUpdate.setThreePointers(playerData.getThreePointers());
    playerDataToUpdate.setFreeThrows(playerData.getFreeThrows());
    playerDataToUpdate.setRebounds(playerData.getRebounds());
    playerDataToUpdate.setAssists(playerData.getAssists());
    playerDataToUpdate.setSteals(playerData.getSteals());
    return playerDataRepo.save(playerDataToUpdate);
  }

  public PlayerData deletePlayerData(final Integer playerDataId) throws NotFoundException {
    Optional<PlayerData> playerDataOptional = playerDataRepo.findById(playerDataId);
    if (playerDataOptional.isEmpty()) {
      throw new NotFoundException("Player data with id " + playerDataId + " not found.");
    }
    playerDataRepo.deleteById(playerDataId);
    return playerDataOptional.get();
  }

  public List<PlayerData> getPlayerDataByPlayerId(final Integer playerId) throws NotFoundException {
    Optional<Player> playerOptional = playerRepo.findById(playerId);
    if (playerOptional.isEmpty()) {
      throw new NotFoundException("Player with id " + playerId + " not found.");
    }
    Player player = playerOptional.get();
    return playerDataRepo.findByPlayer(player);
  }

  public List<PlayerData> getPlayerDataByDate(final LocalDate gameDate) throws NotFoundException {
    return playerDataRepo.findByGameDate(gameDate);
  }

}