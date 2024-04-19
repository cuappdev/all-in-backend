package com.example.allin.contract;

import com.example.allin.user.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepo extends JpaRepository<Contract, Integer> {

  public List<Contract> findByPlayerId(Integer playerId);

  public List<Contract> findByOwner(User owner);

  public List<Contract> findByForSale(Boolean forSale);

}