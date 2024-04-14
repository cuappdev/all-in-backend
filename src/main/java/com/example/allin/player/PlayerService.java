package com.example.allin.player;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.allin.exceptions.NotFoundException;

@Service
public class PlayerService {

  private final PlayerRepo playerRepo;

  public PlayerService(PlayerRepo playerRepo) {
    this.playerRepo = playerRepo;
  }

  public List<Player> getAllPlayers() {
    return playerRepo.findAll();
  }

  public Player getPlayerById(final Integer player_id) throws NotFoundException {
    Optional<Player> playerOptional = playerRepo.findById(player_id);
    if (playerOptional.isEmpty()) {
      throw new NotFoundException();
    }
    return playerOptional.get();
  }

  public Player createPlayer(final Player player) {
    return playerRepo.save(player);
  }

  public Player updatePlayer(final Integer player_id, final Player player) throws NotFoundException {
    Optional<Player> playerOptional = playerRepo.findById(player_id);
    if (playerOptional.isEmpty()) {
      throw new NotFoundException();
    }
    Player playerToUpdate = playerOptional.get();
    playerToUpdate.setFirstName(player.getFirstName());
    playerToUpdate.setLastName(player.getLastName());
    playerToUpdate.setPosition(player.getPosition());
    playerToUpdate.setNumber(player.getNumber());
    playerToUpdate.setHeight(player.getHeight());
    playerToUpdate.setWeight(player.getWeight());
    playerToUpdate.setYear(player.getYear());
    playerToUpdate.setHometown(player.getHometown());
    playerToUpdate.setHighSchool(player.getHighSchool());
    playerToUpdate.setImage(player.getImage());
    playerToUpdate.setBio(player.getBio());
    return playerRepo.save(playerToUpdate);
  }

  public Player deletePlayer(final Integer player_id) throws NotFoundException {
    Optional<Player> playerOptional = playerRepo.findById(player_id);
    if (playerOptional.isEmpty()) {
      throw new NotFoundException();
    }
    playerRepo.deleteById(player_id);
    return playerOptional.get();
  }
}