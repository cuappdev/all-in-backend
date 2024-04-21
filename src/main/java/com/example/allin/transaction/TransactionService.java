package com.example.allin.transaction;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.example.allin.contract.Contract;
import com.example.allin.exceptions.NotFoundException;
import com.example.allin.user.UserRepo;

@Service
public class TransactionService {

  private final TransactionRepo transactionRepo;
  private final UserRepo userRepo;

  public TransactionService(TransactionRepo transactionRepo, UserRepo userRepo) {
    this.transactionRepo = transactionRepo;
    this.userRepo = userRepo;
  }

  public List<Transaction> getAllTransactions() {
    return transactionRepo.findAll();
  }

  public Transaction getTransactionById(final Integer transaction_id) throws NotFoundException {
    Optional<Transaction> transactionOptional = transactionRepo.findById(transaction_id);
    if (transactionOptional.isEmpty()) {
      throw new NotFoundException();
    }
    return transactionOptional.get();
  }

  public List<Transaction> getTransactionsByContract(final Contract contract) throws NotFoundException {
    return transactionRepo.findByContract(contract);
  }

  public Transaction createTransaction(final Transaction transaction) {
    return transactionRepo.save(transaction);
  }

  public Transaction updateTransaction(final Integer transaction_id, final Transaction transaction)
      throws NotFoundException {
    Optional<Transaction> transactionOptional = transactionRepo.findById(transaction_id);
    if (transactionOptional.isEmpty()) {
      throw new NotFoundException();
    }
    Transaction transactionToUpdate = transactionOptional.get();
    transactionToUpdate.setTransactionDate(transaction.getTransactionDate());
    transactionToUpdate.setPrice(transaction.getPrice());
    return transactionRepo.save(transactionToUpdate);
  }

  public Transaction deleteTransaction(final Integer transaction_id) throws NotFoundException {
    Optional<Transaction> transactionOptional = transactionRepo.findById(transaction_id);
    if (transactionOptional.isEmpty()) {
      throw new NotFoundException();
    }
    Transaction transactionToDelete = transactionOptional.get();
    transactionRepo.delete(transactionToDelete);
    return transactionToDelete;
  }

  // Get transactions by contract id
}
