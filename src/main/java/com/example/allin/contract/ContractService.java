package com.example.allin.contract;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ContractService {

  // private final ContractRepo contractRepo;

  // public ContractService(ContractRepo contractRepo) {
  // this.contractRepo = contractRepo;
  // }

  public ResponseEntity<List<Contract>> getAllContracts() {
    // return ResponseEntity.status(HttpStatus.OK).body(contractRepo.findAll());
    List<Contract> contracts = new ArrayList<Contract>();
    contracts.add(new Contract(0, 0, "event", 10, LocalDate.now(), LocalDate.now(), 100.0, false, 5.0));
    return ResponseEntity.status(HttpStatus.OK).body(contracts);
  }

  public ResponseEntity<Contract> getContractById(final String id) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(new Contract(0, 0, "event", 10, LocalDate.now(), LocalDate.now(), 100.0, false, 5.0));
  }

  public ResponseEntity<Contract> createContract(final Contract contract) {
    System.out.println(contract);
    return ResponseEntity.status(HttpStatus.CREATED).body(contract);
  }

  public ResponseEntity<Contract> updateContract(final String id, final Contract contract) {
    System.out.println(contract);
    return ResponseEntity.status(HttpStatus.OK).body(contract);
  }

  public ResponseEntity<String> deleteContract(final String id) {
    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Contract deleted");
  }

}