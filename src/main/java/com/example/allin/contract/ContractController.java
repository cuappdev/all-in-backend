package com.example.allin.contract;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/contracts")
public class ContractController {

  private final ContractService contractService;

  public ContractController(ContractService contractService) {
    this.contractService = contractService;
  }

  @GetMapping("/")
  public ResponseEntity<List<Contract>> getAllContracts() {
    return contractService.getAllContracts();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Contract> getContractById(@PathVariable final String id) {
    return contractService.getContractById(id);
  }

  @PostMapping("/")
  public ResponseEntity<Contract> createContract(@RequestBody final Contract contract) {
    return contractService.createContract(contract);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Contract> updateContract(@PathVariable final String id, @RequestBody final Contract contract) {
    return contractService.updateContract(id, contract);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteContract(@PathVariable final String id) {
    return contractService.deleteContract(id);
  }

}