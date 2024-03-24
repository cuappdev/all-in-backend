package com.example.allin.contract;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ContractService {

  private final ContractRepo contractRepo;

  public ContractService(ContractRepo contractRepo) {
    this.contractRepo = contractRepo;
  }

  public ResponseEntity<List<Contract>> getAllContracts() {
    return ResponseEntity.status(HttpStatus.OK).body(contractRepo.findAll());
  }

  public ResponseEntity<Contract> getContractById(final String id) {
    return ResponseEntity.status(HttpStatus.OK).body(contractRepo.findById(Integer.parseInt(id)).get());
  }

  public ResponseEntity<Contract> createContract(final Contract contract) {
    return ResponseEntity.status(HttpStatus.CREATED).body(contractRepo.save(contract));
  }

  public ResponseEntity<Contract> updateContract(final String id, final Contract contract) {
    return ResponseEntity.status(HttpStatus.OK).body(contractRepo.save(contract));
  }

  public ResponseEntity<String> deleteContract(final String id) {
    contractRepo.deleteById(Integer.parseInt(id));
    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Contract deleted");
  }

}