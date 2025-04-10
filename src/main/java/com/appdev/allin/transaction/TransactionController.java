package com.appdev.allin.transaction;

import com.appdev.allin.exceptions.NotFoundException;
import com.appdev.allin.user.User;
import com.appdev.allin.utils.Pagination;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

  public final TransactionService transactionService;

  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @GetMapping("/")
  public ResponseEntity<Page<Transaction>> getUserTransactions(
      @AuthenticationPrincipal User user,
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "10") Integer size,
      @RequestParam(defaultValue = "transactionDate") String sortBy,
      @RequestParam(defaultValue = "desc") String direction) {

    Pageable pageable = Pagination.generatePageable(page, size, sortBy, direction);
    Page<Transaction> transactions = transactionService.getTransactionsByUser(user, pageable);
    return ResponseEntity.ok(transactions);
  }

  @GetMapping("/pnl")
  public ResponseEntity<Map<LocalDate, Integer>> getPNL(
      @AuthenticationPrincipal User user,
      @RequestParam(required = false) LocalDate start,
      @RequestParam(required = false) LocalDate end) {
    LocalDateTime startDate = (start != null) ? start.atStartOfDay()
        : LocalDateTime.now().minusDays(7).toLocalDate().atStartOfDay();
    LocalDateTime endDate = (end != null) ? end.atTime(23, 59, 59) : LocalDateTime.now();
    Map<LocalDate, Integer> dailySums = transactionService.getDailySumForNullBuyerTransactions(user.getUid(),
        startDate, endDate);
    return ResponseEntity.ok(dailySums);
  }

  // TODO: Move to contracts
  // @GetMapping("/contract/{contract_id}")
  // public ResponseEntity<List<Transaction>> getTransactionsByContractId(
  // @PathVariable final Integer contract_id) {
  // try {
  // List<Transaction> transactions =
  // transactionService.getTransactionsByContractId(contract_id);
  // return ResponseEntity.ok(transactions);
  // } catch (NotFoundException e) {
  // return ResponseEntity.notFound().build();
  // }
  // }
}
