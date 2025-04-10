package com.appdev.allin.transaction;

import com.appdev.allin.contract.Contract;
import com.appdev.allin.user.User;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Integer> {

  List<Transaction> findByContract(Contract contract);

  Page<Transaction> findBySellerOrBuyer(User seller, User buyer, Pageable pageable);

  @Query("SELECT t FROM Transaction t WHERE t.buyer IS NULL AND t.seller.id = :uid AND t.transactionDate >= :startDate AND t.transactionDate <= :endDate")
  List<Transaction> findNullBuyerTransactionsForUserInInterval(
      @Param("uid") String uid,
      @Param("startDate") LocalDateTime startDate,
      @Param("endDate") LocalDateTime endDate);
}
