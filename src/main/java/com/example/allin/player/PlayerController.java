package com.example.allin.player;

import org.springframework.web.bind.annotation.RestController;

import com.example.allin.contract.Contract;
import com.example.allin.contract.ContractService;

import com.example.allin.exceptions.NotFoundException;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class PlayerController {

  public final PlayerService playerService;
  public final ContractService contractService;

  public PlayerController(PlayerService playerService, ContractService contractService) {
    this.playerService = playerService;
    this.contractService = contractService;
  }

  // CRUD operations

  @GetMapping("/players/")
  public ResponseEntity<List<Player>> getAllPlayers() {
    List<Player> players = playerService.getAllPlayers();
    return ResponseEntity.ok(players);
  }

  @GetMapping("/players/{playerId}/")
  public ResponseEntity<Player> getPlayerById(@PathVariable final Integer playerId) {
    try {
      Player player = playerService.getPlayerById(playerId);
      return ResponseEntity.ok(player);
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/players/")
  public ResponseEntity<Player> createPlayer(@RequestBody final Player player) {
    return ResponseEntity.status(201).body(playerService.createPlayer(player));
  }

  @PatchMapping("/players/{playerId}/")
  public ResponseEntity<Player> updatePlayer(@PathVariable final Integer playerId,
      @RequestBody final Player player) {
    try {
      Player updatedPlayer = playerService.updatePlayer(playerId, player);
      return ResponseEntity.ok(updatedPlayer);
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/players/{playerId}/")
  public ResponseEntity<Player> deletePlayer(@PathVariable final Integer playerId) {
    try {
      return ResponseEntity.ok(playerService.deletePlayer(playerId));
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  // Contracts Operations

  @GetMapping("/players/{playerId}/contracts/")
  public ResponseEntity<List<Contract>> getContractsByPlayerId(@PathVariable final Integer playerId) {
    try {
      List<Contract> contracts = contractService.getContractsByPlayerId(playerId);
      return ResponseEntity.ok(contracts);
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

}