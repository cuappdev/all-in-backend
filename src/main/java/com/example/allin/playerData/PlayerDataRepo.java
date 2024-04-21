package com.example.allin.playerData;

import java.util.List;
import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.allin.player.Player;

@Repository
public interface PlayerDataRepo extends JpaRepository<PlayerData, Integer> {

  List<PlayerData> findByPlayer(Player player);

  List<PlayerData> findByGameDate(LocalDate gameDate);

}