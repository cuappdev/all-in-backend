package com.example.allin.contract;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepo extends JpaRepository<Contract, Integer> {

  public List<Contract> findByPlayerId(Integer playerId);

  public List<Contract> findByOwner(Integer owner);

  public List<Contract> findByForSale(Boolean forSale);

}