package com.appdev.allin.data;
import com.appdev.allin.transaction.Transaction;
import com.appdev.allin.user.User;
import com.appdev.allin.contract.Contract;
import com.appdev.allin.data.ContractFactory;
import com.github.javafaker.Faker;

import java.time.LocalDate;

//needs tests
public class TransactionFactory {
    private final Faker faker = new Faker();
    private final UserFactory userFactory = new UserFactory();
    private final ContractFactory contractFactory = new ContractFactory();

    public Transaction createRandomTransaction() {
        User seller = userFactory.createRandomUser();
        User buyer = userFactory.createRandomUser();
        while (buyer.equals(seller)) {  // Ensure buyer and seller are different
            buyer = userFactory.createRandomUser();
        }

        Contract contract = contractFactory.createRandomContract();
        LocalDate transactionDate = LocalDate.now().minusDays(faker.number().numberBetween(1, 365));  // Date within the past year
        Double price = faker.number().randomDouble(2, 100, 10000);  // Random price between 100 and 10000

        return new Transaction(seller, buyer, contract, transactionDate, price);
    }
}
