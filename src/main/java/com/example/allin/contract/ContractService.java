package com.example.allin.contract;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    Optional<Contract> contractOptional = contractRepo.findById(Integer.parseInt(id));
    if (contractOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    return ResponseEntity.status(HttpStatus.OK).body(contractOptional.get());
  }

  public ResponseEntity<Contract> createContract(final Contract contract) {
    return ResponseEntity.status(HttpStatus.CREATED).body(contractRepo.save(contract));
  }

  public ResponseEntity<Contract> updateContract(final String id, final Contract contract) {
    Optional<Contract> contractOptional = contractRepo.findById(Integer.parseInt(id));
    if (contractOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    return ResponseEntity.status(HttpStatus.OK).body(contractRepo.save(contract));
  }

  public ResponseEntity<Contract> deleteContract(final String id) {
    Integer contract_id = Integer.parseInt(id);
    Optional<Contract> contractOptional = contractRepo.findById(contract_id);
    if (contractOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    contractRepo.deleteById(Integer.parseInt(id));
    return ResponseEntity.status(HttpStatus.OK).body(contractOptional.get());
  }

}