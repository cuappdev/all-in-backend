package com.appdev.allin.transaction;

import com.appdev.allin.contract.Contract;
import com.appdev.allin.contract.ContractRepo;
import com.appdev.allin.exceptions.NotFoundException;
import com.appdev.allin.user.User;
import com.appdev.allin.user.UserRepo;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

  private final TransactionRepo transactionRepo;
  private final UserRepo userRepo;
  private final ContractRepo contractRepo;

  public TransactionService(
      TransactionRepo transactionRepo, UserRepo userRepo, ContractRepo contractRepo) {
    this.transactionRepo = transactionRepo;
    this.userRepo = userRepo;
    this.contractRepo = contractRepo;
  }

  public List<Transaction> getAllTransactions() {
    return transactionRepo.findAll();
  }

  public Transaction getTransactionById(final Integer transaction_id) throws NotFoundException {
    Optional<Transaction> transactionOptional = transactionRepo.findById(transaction_id);
    if (transactionOptional.isEmpty()) {
      throw new NotFoundException("Transaction with id " + transaction_id + " not found.");
    }
    return transactionOptional.get();
  }

  public List<Transaction> getTransactionsByContract(final Contract contract)
      throws NotFoundException {
    return transactionRepo.findByContract(contract);
  }

  public List<Transaction> getTransactionsByUserId(final String uid) throws NotFoundException {
    Optional<User> userOptional = userRepo.findById(uid);
    if (userOptional.isEmpty()) {
      throw new NotFoundException("User with id " + uid + " not found.");
    }
    User user = userOptional.get();
    return transactionRepo.findBySellerOrBuyer(user, user);
  }

  public List<Transaction> getSellerTransactionsByUserId(final String uid)
      throws NotFoundException {
    Optional<User> userOptional = userRepo.findById(uid);
    if (userOptional.isEmpty()) {
      throw new NotFoundException("User with id " + uid + " not found.");
    }
    User user = userOptional.get();
    return transactionRepo.findBySeller(user);
  }

  public List<Transaction> getBuyerTransactionsByUserId(final String uid)
      throws NotFoundException {
    Optional<User> userOptional = userRepo.findById(uid);
    if (userOptional.isEmpty()) {
      throw new NotFoundException("User with id " + uid + " not found.");
    }
    User user = userOptional.get();
    return transactionRepo.findByBuyer(user);
  }

  public Transaction createTransaction(final Transaction transaction) {
    return transactionRepo.save(transaction);
  }

  public Transaction updateTransaction(final Integer transaction_id, final Transaction transaction)
      throws NotFoundException {
    Optional<Transaction> transactionOptional = transactionRepo.findById(transaction_id);
    if (transactionOptional.isEmpty()) {
      throw new NotFoundException("Transaction with id " + transaction_id + " not found.");
    }
    Transaction transactionToUpdate = transactionOptional.get();
    transactionToUpdate.setTransactionDate(transaction.getTransactionDate());
    transactionToUpdate.setPrice(transaction.getPrice());
    return transactionRepo.save(transactionToUpdate);
  }

  public Transaction deleteTransaction(final Integer transaction_id) throws NotFoundException {
    Optional<Transaction> transactionOptional = transactionRepo.findById(transaction_id);
    if (transactionOptional.isEmpty()) {
      throw new NotFoundException("Transaction with id " + transaction_id + " not found.");
    }
    Transaction transactionToDelete = transactionOptional.get();
    transactionRepo.delete(transactionToDelete);
    return transactionToDelete;
  }

  public List<Transaction> getTransactionsByContractId(final Integer contract_id)
      throws NotFoundException {
    Optional<Contract> contractOptional = contractRepo.findById(contract_id);
    if (contractOptional.isEmpty()) {
      throw new NotFoundException("Contract with id " + contract_id + " not found.");
    }
    Contract contract = contractOptional.get();
    return transactionRepo.findByContract(contract);
  }
}
