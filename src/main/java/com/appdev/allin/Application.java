package com.appdev.allin;

import com.appdev.allin.contract.Contract;
import com.appdev.allin.contract.ContractRepo;
import com.appdev.allin.player.PlayerRepo;
import com.appdev.allin.playerData.PlayerDataRepo;
import com.appdev.allin.user.User;
import com.appdev.allin.user.UserService;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Scheduled;


@SpringBootApplication
public class Application {

  @Autowired PlayerRepo playerRepo;

  @Autowired PlayerDataRepo playerDataRepo;

  @Autowired
  ContractRepo contractRepo;

  @Autowired
  UserService userService;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Scheduled(cron = "0 0 0 * * *")
  public void checkAndProcessContracts() {
      List<Contract> allContracts = contractRepo.findAll();

      for (Contract contract : allContracts) {

          if (!contract.getExpired() &&
                  (contract.getExpirationTime().isBefore(LocalDate.now()) ||
                          contract.getExpirationTime().isEqual(LocalDate.now()))) {

              boolean contractHit = isContractHit(contract);

              if (contractHit) {
                  processPayout(contract);
              }

              contract.setExpired(true);

              contractRepo.save(contract);
          }
      }
  }

  // abstracted
  public boolean isContractHit(Contract contract) {
      return false;
  }

  private void processPayout(Contract contract) {
      User owner = contract.getOwner();
      if (owner != null) {
          Double payoutAmount = contract.getValue();
          userService.addToUserBalance(owner.getId(), payoutAmount);
      }
  }

}