package com.example.allin.transaction;

import java.util.List;

import com.example.allin.user.User;
import com.example.allin.contract.Contract;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Integer> {

  List<Transaction> findByContract(Contract contract);

  List<Transaction> findBySeller(User seller);

  List<Transaction> findByBuyer(User buyer);

  List<Transaction> findBySellerOrBuyer(User seller, User buyer);

}
