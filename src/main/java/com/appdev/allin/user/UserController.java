package com.appdev.allin.user;

import com.appdev.allin.contract.Contract;
import com.appdev.allin.contract.ContractService;
import com.appdev.allin.contract.Rarity;
import com.appdev.allin.exceptions.ForbiddenException;
import com.appdev.allin.exceptions.NotFoundException;
import com.appdev.allin.exceptions.OverdrawnException;
import com.appdev.allin.player.PlayerService;
import com.appdev.allin.transaction.Transaction;
import com.appdev.allin.transaction.TransactionService;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;

@RestController
public class UserController {

  private final UserService userService;
  private final ContractService contractService;
  private final TransactionService transactionService;
  private final PlayerService playerService;

  public UserController(
      UserService userService,
      ContractService ContractService,
      TransactionService transactionService,
      PlayerService playerService) {
    this.userService = userService;
    this.contractService = ContractService;
    this.transactionService = transactionService;
    this.playerService = playerService;
  }

  // CRUD operations

  @GetMapping("/users/")
  public ResponseEntity<List<User>> getAllUsers() {
    List<User> users = userService.getAllUsers();
    return ResponseEntity.ok(users);
  }

  @GetMapping("/users/{uid}/")
  public ResponseEntity<User> getUser(@PathVariable final String uid) {
    User user = userService.getUserByUidOrThrow(uid);
    return ResponseEntity.ok(user);
  }

  // TODO: Delete
  @PostMapping("/users/")
  public ResponseEntity<User> createUser(@RequestBody final User user) {
    User newUser = userService.createUser(user);
    return ResponseEntity.status(201).body(newUser);
  }

  // Creates the default account or returns the existing one, will likely be
  // changed as frontend auth scheme changes
  @GetMapping("/users/authorize")
  public ResponseEntity<User> authorizeUser(@AuthenticationPrincipal User user) {
    return ResponseEntity.ok(user);
  }

  @PatchMapping("/users/")
  public ResponseEntity<User> updateUser(
      @AuthenticationPrincipal User user, @RequestBody final User updatedUser) {
    User newUser = userService.updateUserByUid(user.getUid(), updatedUser);
    return ResponseEntity.ok(newUser);
  }

  @DeleteMapping("/users/")
  public ResponseEntity<User> deleteUser(@AuthenticationPrincipal User user) {
    userService.deleteUser(user.getUid());
    return ResponseEntity.ok(user);
  }

  @GetMapping("/users/leaderboard")
  public ResponseEntity<Page<User>> getLeaderboard(@RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    Page<User> leaderboard = userService.getLeaderboard(page, size);
    return ResponseEntity.ok(leaderboard);
  }

  // Contract operations

  // @GetMapping("/users/{uid}/contracts/")
  // public ResponseEntity<List<Contract>> getUserContracts(@PathVariable final
  // String uid) {
  // try {
  // List<Contract> contracts = contractService.getContractsByUid(uid);
  // return ResponseEntity.ok(contracts);
  // } catch (NotFoundException e) {
  // return ResponseEntity.notFound().build();
  // }
  // }

  // TODO: Move to contract controller
  // @PostMapping("/users/{uid}/players/{player_id}/contracts/")
  // public ResponseEntity<Contract> createContractByPlayerId(
  // @PathVariable final String uid,
  // @PathVariable final Integer player_id,
  // @RequestBody final Map<String, Double> body) {
  // try {
  // Double buyPrice = body.get("buy_price");
  // Rarity rarity = Rarity.getRandomRarity();
  // Contract createdContract = contractService.createContract(uid, player_id,
  // buyPrice, rarity);
  // return ResponseEntity.status(201).body(createdContract);
  // } catch (NotFoundException e) {
  // return ResponseEntity.notFound().build();
  // } catch (OverdrawnException e) {
  // return ResponseEntity.status(403).build();
  // } catch (ClassCastException e) {
  // return ResponseEntity.badRequest().build();
  // }
  // }

  // TODO: Move to contract controller
  // @PostMapping("/users/{uid}/contracts/")
  // public ResponseEntity<Contract> createContractByRarity(
  // @PathVariable final String uid, @RequestBody final Map<String, Object> body)
  // {
  // try {
  // Double buyPrice = (Double) body.get("buy_price");
  // Rarity rarity = Rarity.valueOf((String) body.get("rarity"));
  // Integer max_player_id = playerService.getAllPlayers().size();
  // Integer player_id = (int) (ThreadLocalRandom.current().nextDouble() *
  // max_player_id) + 1;
  // Contract createdContract = contractService.createContract(uid, player_id,
  // buyPrice, rarity);
  // return ResponseEntity.status(201).body(createdContract);
  // } catch (NotFoundException e) {
  // return ResponseEntity.notFound().build();
  // } catch (OverdrawnException e) {
  // return ResponseEntity.status(403).build();
  // } catch (ClassCastException e) {
  // return ResponseEntity.badRequest().build();
  // }
  // }

  // Transaction operations

  // @GetMapping("/users/{uid}/transactions/")
  // public ResponseEntity<List<Transaction>> getUserTransactions(
  // @PathVariable final String uid) {
  // try {
  // List<Transaction> transactions =
  // transactionService.getTransactionsByUserId(uid);
  // return ResponseEntity.ok(transactions);
  // } catch (NotFoundException e) {
  // return ResponseEntity.notFound().build();
  // }
  // }

  // @GetMapping("/users/{uid}/transactions/seller/")
  // public ResponseEntity<List<Transaction>> getUserSellerTransactions(
  // @PathVariable final String uid) {
  // try {
  // List<Transaction> transactions =
  // transactionService.getSellerTransactionsByUid(uid);
  // return ResponseEntity.ok(transactions);
  // } catch (NotFoundException e) {
  // return ResponseEntity.notFound().build();
  // }
  // }

  // @GetMapping("/users/{uid}/transactions/buyer/")
  // public ResponseEntity<List<Transaction>> getUserBuyerTransactions(
  // @PathVariable final String uid) {
  // try {
  // List<Transaction> transactions =
  // transactionService.getBuyerTransactionsByUid(uid);
  // return ResponseEntity.ok(transactions);
  // } catch (NotFoundException e) {
  // return ResponseEntity.notFound().build();
  // }
  // }
}
