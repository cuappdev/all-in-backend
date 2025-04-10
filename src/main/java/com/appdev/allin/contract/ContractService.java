package com.appdev.allin.contract;

import com.appdev.allin.exceptions.NotForSaleException;
import com.appdev.allin.exceptions.NotFoundException;
import com.appdev.allin.exceptions.OverdrawnException;
import com.appdev.allin.player.Player;
import com.appdev.allin.player.PlayerRepo;
import com.appdev.allin.playerData.PlayerDataRepo;
import com.appdev.allin.transaction.Transaction;
import com.appdev.allin.transaction.TransactionRepo;
import com.appdev.allin.user.User;
import com.appdev.allin.user.UserRepo;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ContractService {

  private final ContractRepo contractRepo;
  private final UserRepo userRepo;
  private final TransactionRepo transactionRepo;
  private final PlayerRepo playerRepo;
  private final PlayerDataRepo playerDataRepo;

  public final String imagePath = "src/main/resources/static/images/teams/";

  // public final String imagePath =
  // "root/all-in-backend/src/main/resources/static/images/teams/";

  public ContractService(
      ContractRepo contractRepo,
      UserRepo userRepo,
      TransactionRepo transactionRepo,
      PlayerRepo playerRepo,
      PlayerDataRepo playerDataRepo) {
    this.contractRepo = contractRepo;
    this.userRepo = userRepo;
    this.transactionRepo = transactionRepo;
    this.playerRepo = playerRepo;
    this.playerDataRepo = playerDataRepo;
  }

  public List<Contract> getAllContracts() {
    return contractRepo.findAll();
  }

  public Contract getContractById(final Integer contract_id) throws NotFoundException {
    Optional<Contract> contractOptional = contractRepo.findById(contract_id);
    if (contractOptional.isEmpty()) {
      throw new NotFoundException("Contract with id " + contract_id + " not found.");
    }
    return contractOptional.get();
  }

  public List<Contract> getContractsByPlayerId(final Integer player_id) throws NotFoundException {
    Optional<Player> playerOptional = playerRepo.findById(player_id);
    if (playerOptional.isEmpty()) {
      throw new NotFoundException("Player with id " + player_id + " not found.");
    }
    Player player = playerOptional.get();
    return contractRepo.findByPlayer(player);
  }

  public List<Contract> getContractsByUid(final String uid) throws NotFoundException {
    Optional<User> userOptional = userRepo.findById(uid);
    if (userOptional.isEmpty()) {
      throw new NotFoundException("User with id " + uid + " not found.");
    }
    User user = userOptional.get();
    return contractRepo.findByOwner(user);
  }

  public Contract createContract(
      final String uid, final Integer player_id, final Integer buyPrice, final Rarity rarity)
      throws NotFoundException, OverdrawnException {
    Optional<User> userOptional = userRepo.findById(uid);
    Optional<Player> playerOptional = playerRepo.findById(player_id);
    if (userOptional.isEmpty() || playerOptional.isEmpty()) {
      throw new NotFoundException("User or player not found.");
    }
    User user = userOptional.get();
    Player player = playerOptional.get();

    if (buyPrice > user.getBalance()) {
      throw new OverdrawnException("User balance is too low.");
    }

    ContractGenerator contractGenerator = new BasketballContractGenerator(playerDataRepo);
    Contract contract = contractGenerator.generateContract(user, player, buyPrice, rarity);
    contractRepo.save(contract);

    user.setBalance(user.getBalance() - contract.getBuyPrice());
    userRepo.save(user);

    Transaction transaction = new Transaction(null, user, contract, contract.getCreationTime(), contract.getBuyPrice());
    transactionRepo.save(transaction);

    return contract;
  }

  public Contract updateContract(final Integer contract_id, final Contract contract)
      throws NotFoundException {
    Optional<Contract> contractOptional = contractRepo.findById(contract_id);
    if (contractOptional.isEmpty()) {
      throw new NotFoundException("Contract with id " + contract_id + " not found.");
    }
    Contract contractToUpdate = contractOptional.get();
    contractToUpdate.setRarity(contract.getRarity());
    contractToUpdate.setEvent(contract.getEvent());
    contractToUpdate.setEventThreshold(contract.getEventThreshold());
    contractToUpdate.setCreationTime(contract.getCreationTime());
    contractToUpdate.setValue(contract.getValue());
    contractToUpdate.setForSale(contract.getForSale());
    contractToUpdate.setSellPrice(contract.getSellPrice());
    return contractRepo.save(contractToUpdate);
  }

  public Contract deleteContract(final Integer contract_id) throws NotFoundException {
    Optional<Contract> contractOptional = contractRepo.findById(contract_id);
    if (contractOptional.isEmpty()) {
      throw new NotFoundException("Contract with id " + contract_id + " not found.");
    }
    contractRepo.deleteById(contract_id);
    return contractOptional.get();
  }

  public List<Contract> getMarketContracts() {
    return contractRepo.findByForSale(true);
  }

  public Contract buyContract(final Integer contract_id, final String buyer_id)
      throws NotFoundException, OverdrawnException, NotForSaleException {
    Optional<Contract> contractOptional = contractRepo.findById(contract_id);
    if (contractOptional.isEmpty()) {
      throw new NotFoundException("Contract with id " + contract_id + " not found.");
    }

    Contract contractToBuy = contractOptional.get();

    if (!contractToBuy.getForSale()) {
      throw new NotForSaleException("Contract is not for sale.");
    }

    Optional<User> sellerOptional = userRepo.findById(contractToBuy.getOwner().getUid());
    Optional<User> buyerOptional = userRepo.findById(buyer_id);
    if (sellerOptional.isEmpty() || buyerOptional.isEmpty()) {
      throw new NotFoundException("Seller or buyer not found.");
    }

    User seller = contractToBuy.getOwner();
    User buyer = buyerOptional.get();

    Integer sellPrice = contractToBuy.getSellPrice();

    if (sellPrice > buyer.getBalance()) {
      throw new OverdrawnException("Buyer balance is too low.");
    }

    seller.setBalance(seller.getBalance() + sellPrice);
    buyer.setBalance(buyer.getBalance() - sellPrice);

    contractToBuy.setOwner(buyer);
    contractToBuy.setForSale(false);
    contractRepo.save(contractToBuy);

    Transaction transaction = new Transaction(seller, buyer, contractToBuy, LocalDateTime.now(), sellPrice);
    transactionRepo.save(transaction);

    return contractToBuy;
  }

  public Contract sellContract(final Integer contract_id, final Integer sellPrice)
      throws NotFoundException {
    Optional<Contract> contractOptional = contractRepo.findById(contract_id);
    if (contractOptional.isEmpty()) {
      throw new NotFoundException("Contract with id " + contract_id + " not found.");
    }

    Contract contractToSell = contractOptional.get();
    contractToSell.setForSale(true);
    contractToSell.setSellPrice(sellPrice);

    return contractRepo.save(contractToSell);
  }

  public byte[] getContractImageById(final String uploadDirectory, final String fileName) {
    Path uploadPath = Path.of(uploadDirectory);
    Path filePath = uploadPath.resolve(fileName);
    try {
      return Files.readAllBytes(filePath);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public Contract recallContract(final Integer contract_id) throws NotFoundException {
    Optional<Contract> contractOptional = contractRepo.findById(contract_id);
    if (contractOptional.isEmpty()) {
      throw new NotFoundException("Contract with id " + contract_id + " not found.");
    }

    Contract contractToRecall = contractOptional.get();
    contractToRecall.setForSale(false);
    contractToRecall.setSellPrice(0);

    return contractRepo.save(contractToRecall);
  }
}
