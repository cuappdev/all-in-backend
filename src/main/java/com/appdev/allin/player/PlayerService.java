package com.appdev.allin.player;

import com.appdev.allin.exceptions.NotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

  private final PlayerRepo playerRepo;

  public PlayerService(PlayerRepo playerRepo) {
    this.playerRepo = playerRepo;
  }

  public List<Player> getAllPlayers() {
    return playerRepo.findAll();
  }

  public Player getPlayerById(final Integer pid) {
    Player player = playerRepo.findById(pid)
        .orElseThrow(() -> new NotFoundException("Player with id " + pid + " not found."));
    return player;
  }

  public Player createPlayer(final Player player) {
    return playerRepo.save(player);
  }
}
