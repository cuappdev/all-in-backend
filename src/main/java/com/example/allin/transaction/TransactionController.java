package com.example.allin.transaction;

import org.springframework.web.bind.annotation.RestController;

import com.example.allin.exceptions.NotFoundException;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class TransactionController {
  public final TransactionService transactionService;

  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @GetMapping("/transactions/")
  public ResponseEntity<List<Transaction>> getAllTransactions() {
    List<Transaction> transactions = transactionService.getAllTransactions();
    return ResponseEntity.ok(transactions);
  }

  @GetMapping("/transactions/{transactionId}/")
  public ResponseEntity<Transaction> getTransactioResponseEntityById(@PathVariable final Integer transactionId) {
    try {
      Transaction transaction = transactionService.getTransactionById(transactionId);
      return ResponseEntity.ok(transaction);
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/transactions/")
  public ResponseEntity<Transaction> createTransaction(@RequestBody final Transaction transaction) {
    return ResponseEntity.status(201).body(transactionService.createTransaction(transaction));
  }

  @PatchMapping("/transactions/{transactionId}/")
  public ResponseEntity<Transaction> updateTransaction(@PathVariable final Integer transactionId,
      @RequestBody final Transaction transaction) {
    try {
      Transaction updatedTransaction = transactionService.updateTransaction(transactionId, transaction);
      return ResponseEntity.ok(updatedTransaction);
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/transactions/{transactionId}/")
  public ResponseEntity<Transaction> deleteTransaction(@PathVariable final Integer transactionId) {
    try {
      transactionService.deleteTransaction(transactionId);
      return ResponseEntity.noContent().build();
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
