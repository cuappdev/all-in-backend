package com.appdev.allin.player;

import com.appdev.allin.contract.Contract;
import com.appdev.allin.contract.ContractService;
import com.appdev.allin.exceptions.NotFoundException;
import com.appdev.allin.playerData.PlayerData;
import com.appdev.allin.playerData.PlayerDataService;

import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/players")
public class PlayerController {

  private final PlayerService playerService;
  private final ContractService contractService;
  private final PlayerDataService playerDataService;

  public PlayerController(PlayerService playerService, ContractService contractService,
      PlayerDataService playerDataService) {
    this.playerService = playerService;
    this.contractService = contractService;
    this.playerDataService = playerDataService;
  }

  @GetMapping("/")
  public ResponseEntity<List<Player>> getAllPlayers() {
    List<Player> players = playerService.getAllPlayers();
    return ResponseEntity.ok(players);
  }

  @GetMapping("/{pid}/")
  public ResponseEntity<Player> getPlayerById(@PathVariable final Integer pid) {
    Player player = playerService.getPlayerById(pid);
    return ResponseEntity.ok(player);
  }

  // @GetMapping("/{pid}/contracts")
  // public ResponseEntity<List<Contract>> getContractsByPlayerId(
  // @PathVariable final Integer pid) {
  // Player player = playerService.getPlayerById(pid);
  // List<Contract> contracts = contractService.getContractsByPlayer(player);
  // return ResponseEntity.ok(contracts);
  // }

  // @GetMapping("/{pid}/data")
  // public ResponseEntity<List<PlayerData>> getDataByPlayerId(@PathVariable final
  // Integer pid) {
  // Player player = playerService.getPlayerById(pid);
  // List<PlayerData> playerData =
  // playerDataService.getPlayerDataByPlayer(player);
  // return ResponseEntity.ok(playerData);
  // }

  // TODO: Remove this method after storing images in digital ocean and saving url
  // in player model
  @GetMapping("/players/{pid}/image")
  public ResponseEntity<byte[]> getPlayerImageById(@PathVariable final Integer pid)
      throws NotFoundException {
    try {
      Player player = playerService.getPlayerById(pid);
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
}
