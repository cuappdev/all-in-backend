package com.appdev.allin.player;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.appdev.allin.exceptions.NotFoundException;

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

  public Player getPlayerByNumber(final Integer number) {
    Player player = playerRepo.findByNumber(number);
    return player;
  }

  public Player savePlayer(final Player player) {
    Player existingPlayer = playerRepo.findByFirstNameAndLastName(player.getFirstName(),
        player.getLastName());
    if (existingPlayer == null) {
      return playerRepo.save(player);
    }
    return existingPlayer;
  }

  public Player createPlayer(final Player player) {
    return playerRepo.save(player);
  }

  public Player updatePlayer(final Integer player_id, final Player player)
      throws NotFoundException {
    Optional<Player> playerOptional = playerRepo.findById(player_id);
    if (playerOptional.isEmpty()) {
      throw new NotFoundException("Player with id " + player_id + " not found.");
    }
    Player playerToUpdate = playerOptional.get();
    playerToUpdate.setFirstName(player.getFirstName());
    playerToUpdate.setLastName(player.getLastName());
    playerToUpdate.setPositions(player.getPositions());
    playerToUpdate.setNumber(player.getNumber());
    playerToUpdate.setHeight(player.getHeight());
    playerToUpdate.setWeight(player.getWeight());
    playerToUpdate.setHometown(player.getHometown());
    playerToUpdate.setHighSchool(player.getHighSchool());
    playerToUpdate.setImage(player.getImage());
    return playerRepo.save(playerToUpdate);
  }

  public Player deletePlayer(final Integer player_id) throws NotFoundException {
    Optional<Player> playerOptional = playerRepo.findById(player_id);
    if (playerOptional.isEmpty()) {
      throw new NotFoundException("Player with id " + player_id + " not found.");
    }
    playerRepo.deleteById(player_id);
    return playerOptional.get();
  }

  public byte[] getImageFromStorage(final String uploadDirectory, final String fileName) {
    Path uploadPath = Path.of(uploadDirectory);
    Path filePath = uploadPath.resolve(fileName);
    try {
      return Files.readAllBytes(filePath);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public void updatePlayerImageById(
      final Integer player_id, final MultipartFile image, final String uploadDirectory)
      throws NotFoundException {
    Optional<Player> playerOptional = playerRepo.findById(player_id);
    if (playerOptional.isEmpty()) {
      throw new NotFoundException("Player with id " + player_id + " not found.");
    }
    Player playerToUpdate = playerOptional.get();
    String uniqueFileName = player_id + "_" + image.getOriginalFilename();
    Path uploadPath = Path.of(uploadDirectory);
    Path filePath = uploadPath.resolve(uniqueFileName);
    if (!Files.exists(uploadPath)) {
      try {
        Files.createDirectories(uploadPath);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    try {
      Files.copy(
          image.getInputStream(), filePath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
    } catch (Exception e) {
      e.printStackTrace();
    }
    playerToUpdate.setImage(uploadDirectory + uniqueFileName);
    playerRepo.save(playerToUpdate);
  }

  public boolean deletePlayerImageById(final Integer player_id, final String uploadDirectory)
      throws NotFoundException {
    Optional<Player> playerOptional = playerRepo.findById(player_id);
    if (playerOptional.isEmpty()) {
      throw new NotFoundException("Player with id " + player_id + " not found.");
    }
    Player playerToUpdate = playerOptional.get();
    Path pathToFile = Path.of(playerToUpdate.getImage());
    try {
      Files.delete(pathToFile);
      playerToUpdate.setImage("src/main/resources/static/images/players/default.jpg");
      playerRepo.save(playerToUpdate);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  public Player getPlayerByFirstNameAndLastName(final String firstName,
      final String lastName) {
    Player player = playerRepo.findByFirstNameAndLastName(firstName, lastName);
    return player;
  }
}