package com.appdev.allin.transaction;

import com.appdev.allin.contract.Contract;
import com.appdev.allin.user.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Integer> {

  List<Transaction> findByContract(Contract contract);

  List<Transaction> findBySeller(User seller);

  List<Transaction> findByBuyer(User buyer);

  List<Transaction> findBySellerOrBuyer(User seller, User buyer);
}
