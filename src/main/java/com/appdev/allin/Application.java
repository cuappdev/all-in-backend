package com.appdev.allin;

import com.appdev.allin.contract.Contract;
import com.appdev.allin.contract.ContractRepo;
import com.appdev.allin.player.PlayerRepo;
import com.appdev.allin.playerData.PlayerDataRepo;
import com.appdev.allin.user.User;
import com.appdev.allin.user.UserService;
import com.appdev.allin.transaction.Transaction;
import com.appdev.allin.transaction.TransactionService;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    @Autowired
    PlayerRepo playerRepo;

    @Autowired
    PlayerDataRepo playerDataRepo;

    @Autowired
    ContractRepo contractRepo;

    @Autowired
    UserService userService;

    @Autowired
    TransactionService transactionService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // Once a day at midnight
    @Scheduled(cron = "0 0 0 * * *")
    public void checkAndProcessContracts() {
        logger.info("Running scheduled contract check at {}", LocalDateTime.now());

        List<Contract> contracts = contractRepo.findAll();
        for (Contract contract : contracts) {
            if (!contract.getExpired() &&
                    (contract.getExpirationTime().isBefore(LocalDateTime.now()) ||
                            contract.getExpirationTime().isEqual(LocalDateTime.now()))) {
                if (isContractHit(contract)) {
                    processPayout(contract);
                }
                // TODO: Add logic to handle contract hit vs miss for the transaction
                createFinalTransaction(contract);
                contract.setExpired(true);
                contractRepo.save(contract);
            }
        }
    }

    // TODO: Finish
    public boolean isContractHit(Contract contract) {
        return true;
    }

    private void processPayout(Contract contract) {
        User owner = contract.getOwner();
        if (owner != null) {
            Integer payoutAmount = contract.getValue();
            userService.addToUserBalance(owner, payoutAmount);
        }
    }

    private void createFinalTransaction(Contract contract) {
        User seller = contract.getOwner();
        Transaction finalTransaction = new Transaction(
                seller,
                null,
                contract,
                LocalDateTime.now(),
                contract.getValue());
        transactionService.createTransaction(finalTransaction);
    }
}