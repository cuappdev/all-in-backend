package com.appdev.allin.transaction;

import com.appdev.allin.exceptions.NotFoundException;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

  public final TransactionService transactionService;

  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  // CRUD operations

  @GetMapping("/transactions/")
  public ResponseEntity<List<Transaction>> getAllTransactions() {
    List<Transaction> transactions = transactionService.getAllTransactions();
    return ResponseEntity.ok(transactions);
  }

  @GetMapping("/transactions/{transaction_id}/")
  public ResponseEntity<Transaction> getTransactionById(
      @PathVariable final Integer transaction_id) {
    try {
      Transaction transaction = transactionService.getTransactionById(transaction_id);
      return ResponseEntity.ok(transaction);
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/transactions/")
  public ResponseEntity<Transaction> createTransaction(@RequestBody final Transaction transaction) {
    return ResponseEntity.status(201).body(transactionService.createTransaction(transaction));
  }

  @PatchMapping("/transactions/{transaction_id}/")
  public ResponseEntity<Transaction> updateTransaction(
      @PathVariable final Integer transaction_id, @RequestBody final Transaction transaction) {
    try {
      Transaction updatedTransaction =
          transactionService.updateTransaction(transaction_id, transaction);
      return ResponseEntity.ok(updatedTransaction);
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/transactions/{transaction_id}/")
  public ResponseEntity<Transaction> deleteTransaction(@PathVariable final Integer transaction_id) {
    try {
      Transaction deletedTransaction = transactionService.deleteTransaction(transaction_id);
      return ResponseEntity.ok(deletedTransaction);
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  // Contract-Transaction operations

  @GetMapping("/transactions/contract/{contract_id}/")
  public ResponseEntity<List<Transaction>> getTransactionsByContractId(
      @PathVariable final Integer contract_id) {
    try {
      List<Transaction> transactions = transactionService.getTransactionsByContractId(contract_id);
      return ResponseEntity.ok(transactions);
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
