package com.appdev.allin.contract;

import com.appdev.allin.player.Player;
import com.appdev.allin.user.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepo extends JpaRepository<Contract, Integer> {

  public List<Contract> findByPlayer(Player player);

  public List<Contract> findByOwner(User owner);

  public List<Contract> findByForSale(Boolean forSale);
}
