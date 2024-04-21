package com.example.allin.user;

import org.springframework.web.bind.annotation.RestController;

import com.example.allin.contract.Contract;
import com.example.allin.contract.ContractService;
import com.example.allin.transaction.Transaction;
import com.example.allin.transaction.TransactionService;
import com.example.allin.exceptions.NotFoundException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.springframework.http.HttpStatus;



@RestController
public class UserController {

  private final UserService userService;
  private final ContractService contractService;
  private final TransactionService transactionService;

  public UserController(UserService userService, ContractService ContractService,
      TransactionService transactionService) {
    this.userService = userService;
    this.contractService = ContractService;
    this.transactionService = transactionService;
  }

  // CRUD operations

  @GetMapping("/users/")
  public ResponseEntity<List<User>> getAllUsers() {
    List<User> users = userService.getAllUsers();
    return ResponseEntity.ok(users);
  }

  @GetMapping("/users/{user_id}/")
  public ResponseEntity<User> getUserById(@PathVariable final Integer user_id) {
    try {
      User user = userService.getUserById(user_id);
      return ResponseEntity.ok(user);
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/users/")
  public ResponseEntity<User> createUser(@RequestBody final User user) {
    return ResponseEntity.status(201).body(userService.createUser(user));
  }

  @PatchMapping("/users/{user_id}/")
  public ResponseEntity<User> updateUser(@PathVariable final Integer user_id, @RequestBody final User user) {
    try {
      User updatedUser = userService.updateUser(user_id, user);
      return ResponseEntity.ok(updatedUser);
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/users/{user_id}/")
  public ResponseEntity<User> deleteUser(@PathVariable final Integer user_id) {
    try {
      User deletedUser = userService.deleteUser(user_id);
      return ResponseEntity.ok(deletedUser);
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/users/{user_id}/image/")
  public ResponseEntity<byte[]> getUserImageById(@PathVariable final Integer user_id) {
    try {
      User user = userService.getUserById(user_id);
      String currentDirectory = user.getImage();
      String imageName = currentDirectory.substring(currentDirectory.lastIndexOf("/") + 1);
      currentDirectory = currentDirectory.replace(imageName, "");
      byte[] image = userService.getImageFromStorage(currentDirectory, imageName);
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.IMAGE_JPEG);
      return new ResponseEntity<>(image, headers, HttpStatus.OK);
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PatchMapping("/users/{user_id}/image/")
  public ResponseEntity<String> updateUserImageById(@PathVariable final Integer user_id,
  @RequestParam("image") MultipartFile image) {
    String uploadDirectory = "src/main/resources/static/images/users/";
    try {
      userService.updateUserImageById(user_id, image, uploadDirectory);
      return ResponseEntity.ok("Image uploaded successfully!");
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/users/{user_id}/image/")
  public ResponseEntity<String> deleteUserImageById(@PathVariable final Integer user_id) {
    String uploadDirectory = "src/main/resources/static/images/users/";
    try {
      if (userService.deleteUserImageById(user_id, uploadDirectory)) {
        return ResponseEntity.ok("Image deleted successfully");
      }
      return ResponseEntity.notFound().build();
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }
  // Add login and logout operations

  // Contract operations

  @GetMapping("/users/{user_id}/contracts/")
  public ResponseEntity<List<Contract>> getUserContracts(@PathVariable final Integer user_id) {
    try {
      List<Contract> contracts = contractService.getContractsByUserId(user_id);
      return ResponseEntity.ok(contracts);
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/users/{user_id}/players/{player_id}/contracts/")
  public ResponseEntity<Contract> createContract(@PathVariable final Integer user_id,
      @PathVariable final Integer player_id,
      @RequestBody final Contract contract) {
    try {
      Contract createdContract = contractService.createContractByUserIdAndPlayerId(user_id, player_id, contract);
      return ResponseEntity.status(201).body(createdContract);
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  // Transaction operations

  @GetMapping("/users/{user_id}/transactions/")
  public ResponseEntity<List<Transaction>> getUserTransactions(@PathVariable final Integer user_id) {
    try {
      List<Transaction> transactions = transactionService.getTransactionsByUserId(user_id);
      return ResponseEntity.ok(transactions);
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/users/{user_id}/transactions/seller/")
  public ResponseEntity<List<Transaction>> getUserSellerTransactions(@PathVariable final Integer user_id) {
    try {
      List<Transaction> transactions = transactionService.getSellerTransactionsByUserId(user_id);
      return ResponseEntity.ok(transactions);
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/users/{user_id}/transactions/buyer/")
  public ResponseEntity<List<Transaction>> getUserBuyerTransactions(@PathVariable final Integer user_id) {
    try {
      List<Transaction> transactions = transactionService.getBuyerTransactionsByUserId(user_id);
      return ResponseEntity.ok(transactions);
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

}
