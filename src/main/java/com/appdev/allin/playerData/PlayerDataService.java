package com.appdev.allin.playerData;

import com.appdev.allin.player.Player;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PlayerDataService {
  private final PlayerDataRepo playerDataRepo;

  public PlayerDataService(PlayerDataRepo playerDataRepo) {
    this.playerDataRepo = playerDataRepo;
  }

  public List<PlayerData> getAllPlayerData() {
    return playerDataRepo.findAll();
  }

  public PlayerData createPlayerData(final PlayerData playerData) {
    return playerDataRepo.save(playerData);
  }

  public List<PlayerData> getPlayerDataByPlayer(final Player player) {
    return playerDataRepo.findByPlayer(player);
  }

  public List<PlayerData> getPlayerDataByDate(final LocalDate gameDate) {
    return playerDataRepo.findByGameDate(gameDate);
  }

  public List<PlayerData> getPlayerDataByDateAndPlayer(Player player, LocalDate gameDate) {
    return playerDataRepo.findByPlayerAndGameDate(player, gameDate);
}
}
