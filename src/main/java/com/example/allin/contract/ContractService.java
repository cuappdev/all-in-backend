package com.example.allin.contract;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.allin.exceptions.NotFoundException;

@Service
public class ContractService {

  private final ContractRepo contractRepo;

  public ContractService(ContractRepo contractRepo) {
    this.contractRepo = contractRepo;
  }

  public List<Contract> getAllContracts() {
    return contractRepo.findAll();
  }

  public Contract getContractById(final Integer contract_id) throws NotFoundException {
    Optional<Contract> contractOptional = contractRepo.findById(contract_id);
    if (contractOptional.isEmpty()) {
      throw new NotFoundException();
    }
    return contractOptional.get();
  }

  public Contract createContract(final Contract contract) {
    return contractRepo.save(contract);
  }

  public Contract updateContract(final Integer contract_id, final Contract contract) throws NotFoundException {
    Optional<Contract> contractOptional = contractRepo.findById(contract_id);
    if (contractOptional.isEmpty()) {
      throw new NotFoundException();
    }
    Contract contractToUpdate = contractOptional.get();
    contractToUpdate.setPlayerId(contract.getPlayerId());
    contractToUpdate.setOwner(contract.getOwner());
    contractToUpdate.setRarity(contract.getRarity());
    contractToUpdate.setEvent(contract.getEvent());
    contractToUpdate.setEventThreshold(contract.getEventThreshold());
    contractToUpdate.setCreationTime(contract.getCreationTime());
    contractToUpdate.setExpirationTime(contract.getExpirationTime());
    contractToUpdate.setValue(contract.getValue());
    contractToUpdate.setForSale(contract.getForSale());
    contractToUpdate.setSellPrice(contract.getSellPrice());
    return contractRepo.save(contractToUpdate);
  }

  public Contract deleteContract(final Integer contract_id) throws NotFoundException {
    Optional<Contract> contractOptional = contractRepo.findById(contract_id);
    if (contractOptional.isEmpty()) {
      throw new NotFoundException();
    }
    contractRepo.deleteById(contract_id);
    return contractOptional.get();
  }

}