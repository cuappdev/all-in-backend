package com.example.allin.contract;

import org.springframework.web.bind.annotation.RestController;

import com.example.allin.exceptions.NotFoundException;
import com.example.allin.exceptions.OverdrawnException;
import com.example.allin.exceptions.NotForSaleException;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class ContractController {

  private final ContractService contractService;

  public ContractController(ContractService contractService) {
    this.contractService = contractService;
  }

  @GetMapping("/contracts/")
  public ResponseEntity<List<Contract>> getAllContracts() {
    List<Contract> contracts = contractService.getAllContracts();
    return ResponseEntity.ok(contracts);
  }

  @GetMapping("/contract/{contract_id}/")
  public ResponseEntity<Contract> getContractById(@PathVariable final Integer contract_id) {
    try {
      Contract contract = contractService.getContractById(contract_id);
      return ResponseEntity.ok(contract);
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  // Change to create the contract on the backend
  @PostMapping("/contracts/")
  public ResponseEntity<Contract> createContract(@RequestBody final Contract contract) {
    return ResponseEntity.status(201).body(contractService.createContract(contract));
  }

  @PatchMapping("/contract/{contract_id}/")
  public ResponseEntity<Contract> updateContract(@PathVariable final Integer contract_id,
      @RequestBody final Contract contract) {
    try {
      Contract updatedContract = contractService.updateContract(contract_id, contract);
      return ResponseEntity.ok(updatedContract);
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/contract/{contract_id}/")
  public ResponseEntity<Contract> deleteContract(@PathVariable final Integer contract_id) {
    try {
      Contract deletedContract = contractService.deleteContract(contract_id);
      return ResponseEntity.ok(deletedContract);
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/market/")
  public ResponseEntity<List<Contract>> getMarketContracts() {
    List<Contract> marketContracts = contractService.getMarketContracts();
    return ResponseEntity.ok(marketContracts);
  }

  @PostMapping("/contract/{contract_id}/buy/")
  public ResponseEntity<Contract> buyContract(@PathVariable final Integer contract_id,
      @RequestBody final Integer buyer_id) {
    try {
      Contract boughtContract = contractService.buyContract(contract_id, buyer_id);
      return ResponseEntity.ok(boughtContract);
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    } catch (OverdrawnException e) {
      return ResponseEntity.status(402).build();
    } catch (NotForSaleException e) {
      return ResponseEntity.status(403).build();
    }
  }

  @PostMapping("/contract/{contract_id}/sell/")
  public ResponseEntity<Contract> sellContract(@PathVariable final Integer contract_id,
      @RequestBody final Double sellPrice) {
    try {
      Contract soldContract = contractService.sellContract(contract_id, sellPrice);
      return ResponseEntity.ok(soldContract);
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

}