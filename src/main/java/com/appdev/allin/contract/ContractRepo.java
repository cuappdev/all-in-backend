package com.appdev.allin.contract;

import com.appdev.allin.player.Player;
import com.appdev.allin.user.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface ContractRepo extends JpaRepository<Contract, Integer> {
  List<Contract> findByPlayer(Player player);

  List<Contract> findByOwner(User owner);

  List<Contract> findByForSale(Boolean forSale);

  Page<Contract> findAllByBuyPriceBetweenAndValueBetween(
      Integer minPrice, Integer maxPrice, Integer minPayout, Integer maxPayout, Pageable pageable);

  Page<Contract> findAllByBuyPriceBetweenAndValueBetweenAndRarity(
      Integer minPrice, Integer maxPrice, Integer minPayout, Integer maxPayout, Rarity rarity, Pageable pageable);
}
