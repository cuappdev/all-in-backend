package com.example.allin.transaction;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.example.allin.exceptions.NotFoundException;

@Service
public class TransactionService {
  private final TransactionRepo transactionRepo;

  public TransactionService(TransactionRepo transactionRepo) {
    this.transactionRepo = transactionRepo;
  }

  public List<Transaction> getAllTransactions() {
    return transactionRepo.findAll();
  }

  public Transaction getTransactionById(final Integer transactionId) throws NotFoundException {
    Optional<Transaction> transactionOptional = transactionRepo.findById(transactionId);
    if (transactionOptional.isEmpty()) {
      throw new NotFoundException();
    }
    return transactionOptional.get();
  }

  public Transaction createTransaction(final Transaction transaction) {
    return transactionRepo.save(transaction);
  }

  public Transaction updateTransaction(final Integer transactionId, final Transaction transaction) throws NotFoundException {
    Optional<Transaction> transactionOptional = transactionRepo.findById(transactionId);
    if (transactionOptional.isEmpty()) {
      throw new NotFoundException();
    }
    Transaction transactionToUpdate = transactionOptional.get();
    transactionToUpdate.setSellerId(transaction.getSellerId());
    transactionToUpdate.setBuyerId(transaction.getBuyerId());
    transactionToUpdate.setContractId(transaction.getContractId());
    transactionToUpdate.setTransactionDate(transaction.getTransactionDate());
    transactionToUpdate.setPrice(transaction.getPrice());
    return transactionRepo.save(transactionToUpdate);
  }

  public Transaction deleteTransaction(final Integer transactionId) throws NotFoundException {
    Optional<Transaction> transactionOptional = transactionRepo.findById(transactionId);
    if (transactionOptional.isEmpty()) {
      throw new NotFoundException();
    }
    Transaction transactionToDelete = transactionOptional.get();
    transactionRepo.delete(transactionToDelete);
    return transactionToDelete;
  }
}
