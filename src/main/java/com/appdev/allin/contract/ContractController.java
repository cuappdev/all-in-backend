package com.appdev.allin.contract;

import com.appdev.allin.exceptions.NotForSaleException;
import com.appdev.allin.exceptions.NotFoundException;
import com.appdev.allin.exceptions.OverdrawnException;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContractController {

  private final ContractService contractService;

  public ContractController(ContractService contractService) {
    this.contractService = contractService;
  }

  // CRUD operations

  @GetMapping("/contracts/")
  public ResponseEntity<List<Contract>> getAllContracts() {
    List<Contract> contracts = contractService.getAllContracts();
    return ResponseEntity.ok(contracts);
  }

  @GetMapping("/contracts/{contract_id}/")
  public ResponseEntity<Contract> getContractById(@PathVariable final Integer contract_id) {
    try {
      Contract contract = contractService.getContractById(contract_id);
      return ResponseEntity.ok(contract);
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PatchMapping("/contracts/{contract_id}/")
  public ResponseEntity<Contract> updateContract(
      @PathVariable final Integer contract_id, @RequestBody final Contract contract) {
    try {
      Contract updatedContract = contractService.updateContract(contract_id, contract);
      return ResponseEntity.ok(updatedContract);
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/contracts/{contract_id}/")
  public ResponseEntity<Contract> deleteContract(@PathVariable final Integer contract_id) {
    try {
      Contract deletedContract = contractService.deleteContract(contract_id);
      return ResponseEntity.ok(deletedContract);
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/contracts/{contract_id}/image/")
  public ResponseEntity<byte[]> getContractImageById(@PathVariable final Integer contract_id) {
    try {
      Contract contract = contractService.getContractById(contract_id);
      String currentDirectory = contract.getOpposingTeamImage();
      String imageName = currentDirectory.substring(currentDirectory.lastIndexOf('/') + 1);
      currentDirectory = currentDirectory.replace(imageName, "");
      byte[] image = contractService.getContractImageById(currentDirectory, imageName);
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.IMAGE_JPEG);
      return new ResponseEntity<>(image, headers, HttpStatus.OK);
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  // Market operations

  @GetMapping("/market/")
  public ResponseEntity<List<Contract>> getMarketContracts() {
    List<Contract> marketContracts = contractService.getMarketContracts();
    return ResponseEntity.ok(marketContracts);
  }

  @GetMapping("/contracts/{contract_id}/recall/")
  public ResponseEntity<Contract> recallContract(@PathVariable final Integer contract_id) {
    try {
      Contract recalledContract = contractService.recallContract(contract_id);
      return ResponseEntity.ok(recalledContract);
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/contracts/{contract_id}/buy/")
  public ResponseEntity<Contract> buyContract(
      @PathVariable final Integer contract_id, @RequestBody final Map<String, Integer> body) {
    try {
      Contract boughtContract = contractService.buyContract(contract_id, body.get("buyer_id"));
      return ResponseEntity.ok(boughtContract);
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    } catch (OverdrawnException e) {
      return ResponseEntity.status(402).build();
    } catch (NotForSaleException e) {
      return ResponseEntity.status(403).build();
    }
  }

  @PostMapping("/contracts/{contract_id}/sell/")
  public ResponseEntity<Contract> sellContract(
      @PathVariable final Integer contract_id, @RequestBody final Map<String, Double> body) {
    try {
      Contract soldContract = contractService.sellContract(contract_id, body.get("sell_price"));
      return ResponseEntity.ok(soldContract);
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
