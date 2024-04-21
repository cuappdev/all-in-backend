package com.example.allin.contract;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.allin.user.User;
import com.example.allin.user.UserRepo;

import com.example.allin.transaction.Transaction;
import com.example.allin.transaction.TransactionRepo;

import com.example.allin.player.Player;
import com.example.allin.player.PlayerRepo;

import com.example.allin.exceptions.NotFoundException;
import com.example.allin.exceptions.OverdrawnException;
import com.example.allin.exceptions.NotForSaleException;

@Service
public class ContractService {

  private final ContractRepo contractRepo;
  private final UserRepo userRepo;
  private final TransactionRepo transactionRepo;
  private final PlayerRepo playerRepo;

  public ContractService(ContractRepo contractRepo, UserRepo userRepo, TransactionRepo transactionRepo,
      PlayerRepo playerRepo) {
    this.contractRepo = contractRepo;
    this.userRepo = userRepo;
    this.transactionRepo = transactionRepo;
    this.playerRepo = playerRepo;
  }

  public List<Contract> getAllContracts() {
    return contractRepo.findAll();
  }

  public Contract getContractById(final Integer contract_id) throws NotFoundException {
    Optional<Contract> contractOptional = contractRepo.findById(contract_id);
    if (contractOptional.isEmpty()) {
      throw new NotFoundException();
    }
    return contractOptional.get();
  }

  public List<Contract> getContractsByPlayerId(final Integer player_id) throws NotFoundException {
    return null;
  }

  public List<Contract> getContractsByUserId(final Integer user_id) throws NotFoundException {
    Optional<User> userOptional = userRepo.findById(user_id);
    if (userOptional.isEmpty()) {
      throw new NotFoundException();
    }
    User user = userOptional.get();
    return contractRepo.findByOwner(user);
  }

  public Contract createContractByUserIdAndPlayerId(final Integer user_id, final Integer player_id,
      final Contract contract) throws NotFoundException {
    Optional<User> userOptional = userRepo.findById(user_id);
    Optional<Player> playerOptional = playerRepo.findById(player_id);
    if (userOptional.isEmpty() || playerOptional.isEmpty()) {
      throw new NotFoundException();
    }
    User user = userOptional.get();
    Player player = playerOptional.get();
    contract.setOwner(user);
    contract.setPlayer(player);
    contractRepo.save(contract);
    Transaction transaction = new Transaction(null, user, contract,
        contract.getCreationTime(), contract.getBuyPrice());
    transactionRepo.save(transaction);
    return contract;
  }

  public Contract updateContract(final Integer contract_id, final Contract contract) throws NotFoundException {
    Optional<Contract> contractOptional = contractRepo.findById(contract_id);
    if (contractOptional.isEmpty()) {
      throw new NotFoundException();
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
      throw new NotFoundException();
    }
    contractRepo.deleteById(contract_id);
    return contractOptional.get();
  }

  public List<Contract> getMarketContracts() {
    return contractRepo.findByForSale(true);
  }

  public Contract buyContract(final Integer contract_id, final Integer buyer_id)
      throws NotFoundException, OverdrawnException, NotForSaleException {
    Optional<Contract> contractOptional = contractRepo.findById(contract_id);
    if (contractOptional.isEmpty()) {
      throw new NotFoundException();
    }

    Contract contractToBuy = contractOptional.get();

    if (!contractToBuy.getForSale()) {
      throw new NotForSaleException();
    }

    Optional<User> buyerOptional = userRepo.findById(buyer_id);
    if (buyerOptional.isEmpty()) {
      throw new NotFoundException();
    }

    User seller = contractToBuy.getOwner();
    User buyer = buyerOptional.get();
    Double sellPrice = contractToBuy.getSellPrice();

    if (sellPrice > buyer.getBalance()) {
      throw new OverdrawnException();
    }

    seller.setBalance(seller.getBalance() + sellPrice);
    buyer.setBalance(buyer.getBalance() - sellPrice);

    contractToBuy.setOwner(buyer);
    contractToBuy.setForSale(false);

    return contractRepo.save(contractToBuy);
  }

  public Contract sellContract(final Integer contract_id, final Double sellPrice) throws NotFoundException {
    Optional<Contract> contractOptional = contractRepo.findById(contract_id);
    if (contractOptional.isEmpty()) {
      throw new NotFoundException();
    }

    Contract contractToSell = contractOptional.get();
    contractToSell.setForSale(true);
    contractToSell.setSellPrice(sellPrice);

    return contractRepo.save(contractToSell);
  }

}