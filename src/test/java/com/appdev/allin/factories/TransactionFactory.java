package com.appdev.allin.factories;

import com.appdev.allin.contract.Contract;
import com.appdev.allin.transaction.Transaction;
import com.appdev.allin.user.User;

import com.github.javafaker.Faker;
import java.time.LocalDateTime;

// needs tests
public class TransactionFactory {
  private final Faker faker = new Faker();
  private final ContractFactory contractFactory = new ContractFactory();

  public Transaction createRandomTransaction() {
    User seller = UserFactory.createRandomUser();
    User buyer = UserFactory.createRandomUser();
    while (buyer.equals(seller)) { // Ensure buyer and seller are different
      buyer = UserFactory.createRandomUser();
    }

    Contract contract = contractFactory.createRandomContract(seller);
    LocalDateTime transactionDate = LocalDateTime.now()
        .minusDays(faker.number().numberBetween(1, 365)); // Date within the past year
    Integer price = faker.number().randomDigit();

    return new Transaction(seller, buyer, contract, transactionDate, price);
  }
}
