package com.appdev.allin;

import com.appdev.allin.contract.Contract;
import com.appdev.allin.contract.ContractRepo;
import com.appdev.allin.player.PlayerRepo;
import com.appdev.allin.playerData.PlayerDataRepo;
import com.appdev.allin.user.User;
import com.appdev.allin.user.UserService;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
public class Application {
    @Autowired
    PlayerRepo playerRepo;

    @Autowired
    PlayerDataRepo playerDataRepo;

    @Autowired
    ContractRepo contractRepo;

    @Autowired
    UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // Once a day at midnight
    @Scheduled(cron = "0 0 0 * * *")
    public void checkAndProcessContracts() {
        List<Contract> contracts = contractRepo.findAll();
        for (Contract contract : contracts) {
            if (!contract.getExpired() &&
                    (contract.getExpirationTime().isBefore(LocalDateTime.now()) ||
                            contract.getExpirationTime().isEqual(LocalDateTime.now()))) {
                if (isContractHit(contract)) {
                    processPayout(contract);
                }

                contract.setExpired(true);
                contractRepo.save(contract);
            }
        }
        contractRepo.flush();
    }

    // TODO: Finish
    public boolean isContractHit(Contract contract) {
        return false;
    }

    private void processPayout(Contract contract) {
        User owner = contract.getOwner();
        if (owner != null) {
            Integer payoutAmount = contract.getValue();
            userService.addToUserBalance(owner, payoutAmount);
        }
    }
}