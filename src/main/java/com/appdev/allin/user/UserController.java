package com.appdev.allin.user;

import com.appdev.allin.contract.Contract;
import com.appdev.allin.contract.ContractService;
import com.appdev.allin.utils.Pagination;

import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;
  private final ContractService contractService;

  public UserController(
      UserService userService, ContractService contractService) {
    this.userService = userService;
    this.contractService = contractService;
  }

  @GetMapping("/")
  public ResponseEntity<Page<User>> getAllUsers(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "10") Integer size,
      @RequestParam(defaultValue = "balance") String sortBy,
      @RequestParam(defaultValue = "desc") String direction) {

    Pageable pageable = Pagination.generatePageable(page, size, sortBy, direction);
    Page<User> users = userService.getAllUsers(pageable);
    return ResponseEntity.ok(users);
  }

  // Creates the default account or returns the existing one, will likely be
  // changed as frontend auth scheme changes
  @GetMapping("/me/")
  public ResponseEntity<User> getMe(@AuthenticationPrincipal User user) {
    return ResponseEntity.ok(user);
  }

  @GetMapping("/me/rank")
  public ResponseEntity<Map<String, Integer>> getMyRank(@AuthenticationPrincipal User user) {
    Integer rank = userService.getUserRank(user);
    return ResponseEntity.ok(Map.of("rank", rank));
  }

  @GetMapping("/me/contracts/")
  public ResponseEntity<List<Contract>> getUserContracts(@AuthenticationPrincipal User user) {
    List<Contract> contracts = contractService.getContractsByUser(user);
    return ResponseEntity.ok(contracts);
  }

  // Functionality does not exist yet based on the designs (users can only view
  // their own profile)
  @GetMapping("/{uid}")
  public ResponseEntity<User> getUser(@PathVariable final String uid) {
    User user = userService.getUserByUid(uid);
    return ResponseEntity.ok(user);
  }

  // Functionality does not exist yet based on the designs (users cannot update
  // their profile)
  @PatchMapping("/me/")
  public ResponseEntity<User> updateUser(
      @AuthenticationPrincipal User user, @RequestBody final User updatedUser) {
    User newUser = userService.updateUser(user, updatedUser);
    return ResponseEntity.ok(newUser);
  }

  // Functionality does not exist yet based on the designs (users cannot delete
  // their profile)
  @DeleteMapping("/me/")
  public ResponseEntity<User> deleteUser(@AuthenticationPrincipal User user) {
    userService.deleteUser(user);
    return ResponseEntity.ok(user);
  }

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

  // @PostMapping("/me/contracts")
  // public ResponseEntity<Contract> createContractByRarity(
  // @AuthenticationPrincipal User user,
  // @RequestBody final Map<String, Object> body) {
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
}
