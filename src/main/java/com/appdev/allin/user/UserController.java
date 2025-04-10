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
  public ResponseEntity<User> getUserById(@PathVariable final String uid) {
    User user = userService.getUserByUid(uid);
    return ResponseEntity.ok(user);
  }

  // TODO: Delete
  @PostMapping("/users/")
  public ResponseEntity<User> createUser(@RequestBody final User user) {
    return ResponseEntity.status(201).body(userService.createUser(user));
  }

  @GetMapping("/users/authorize")
  public ResponseEntity<User> authorizeUser(@AuthenticationPrincipal User user) {
    return ResponseEntity.ok(user);
  }

  // @PatchMapping("/users/{uid}/")
  // public ResponseEntity<User> updateUser(
  // @PathVariable final String uid, @RequestBody final User user) {
  // try {
  // User updatedUser = userService.updateUserByUid(uid, user);
  // return ResponseEntity.ok(updatedUser);
  // } catch (NotFoundException e) {
  // return ResponseEntity.notFound().build();
  // }
  // }

  // @DeleteMapping("/users/{uid}/")
  // public ResponseEntity<User> deleteUser(@PathVariable final String uid) {
  // try {
  // User deletedUser = userService.deleteUserByUid(uid);
  // return ResponseEntity.ok(deletedUser);
  // } catch (NotFoundException e) {
  // return ResponseEntity.notFound().build();
  // }
  // }

  // @GetMapping("/users/{uid}/image/")
  // public ResponseEntity<byte[]> getUserImageById(@PathVariable final String
  // uid) {
  // try {
  // User user = userService.getUserByUid(uid);
  // String currentDirectory = user.getImage();
  // String imageName =
  // currentDirectory.substring(currentDirectory.lastIndexOf('/') + 1);
  // currentDirectory = currentDirectory.replace(imageName, "");
  // byte[] image = userService.getUserImageById(currentDirectory, imageName);
  // HttpHeaders headers = new HttpHeaders();
  // headers.setContentType(MediaType.IMAGE_JPEG);
  // return new ResponseEntity<>(image, headers, HttpStatus.OK);
  // } catch (NotFoundException e) {
  // return ResponseEntity.notFound().build();
  // }
  // }

  // @PatchMapping("/users/{uid}/image/")
  // public ResponseEntity<byte[]> updateUserImageById(
  // @PathVariable final String uid, @RequestBody final MultipartFile image) {
  // try {
  // byte[] uploadedImage = userService.updateUserImageById(uid, image,
  // uploadDirectory);
  // HttpHeaders headers = new HttpHeaders();
  // headers.setContentType(MediaType.IMAGE_JPEG);
  // return new ResponseEntity<>(uploadedImage, headers, HttpStatus.OK);
  // } catch (NotFoundException e) {
  // return ResponseEntity.notFound().build();
  // }
  // }

  // @DeleteMapping("/users/{uid}/image/")
  // public ResponseEntity<byte[]> deleteUserImageById(@PathVariable final String
  // uid) {
  // try {
  // byte[] deletedImage = userService.deleteUserImageById(uid, uploadDirectory);
  // HttpHeaders headers = new HttpHeaders();
  // headers.setContentType(MediaType.IMAGE_JPEG);
  // return new ResponseEntity<>(deletedImage, headers, HttpStatus.OK);
  // } catch (NotFoundException e) {
  // return ResponseEntity.notFound().build();
  // } catch (ForbiddenException e) {
  // return ResponseEntity.status(403).build();
  // }
  // }

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
