package com.appdev.allin.transaction;

import com.appdev.allin.contract.Contract;
import com.appdev.allin.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class TransactionService {

  private final TransactionRepo transactionRepo;

  public TransactionService(
      TransactionRepo transactionRepo) {
    this.transactionRepo = transactionRepo;
  }

  public List<Transaction> getTransactionsByContract(final Contract contract) {
    return transactionRepo.findByContract(contract);
  }

  public Page<Transaction> getTransactionsByUser(final User user, final Pageable pageable) {
    return transactionRepo.findBySellerOrBuyer(user, user, pageable);
  }

  public Map<LocalDate, Integer> getDailySumForNullBuyerTransactions(String uid, LocalDateTime startDate,
      LocalDateTime endDate) {
    List<Transaction> transactions = transactionRepo.findNullBuyerTransactionsForUserInInterval(uid,
        startDate, endDate);

    return transactions.stream()
        .collect(Collectors.groupingBy(
            t -> t.getTransactionDate().toLocalDate(),
            Collectors.summingInt(Transaction::getPrice)));
  }

  public Transaction createTransaction(final Transaction transaction) {
    return transactionRepo.save(transaction);
  }
}
