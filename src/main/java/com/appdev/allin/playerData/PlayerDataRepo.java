package com.appdev.allin.playerData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import com.appdev.allin.player.Player;

@Repository
public interface PlayerDataRepo extends JpaRepository<PlayerData, Integer> {
    List<PlayerData> findByPlayer(Player player);
    List<PlayerData> findByGameDate(LocalDate gameDate);
    List<PlayerData> findByPlayerAndGameDate(Player player, LocalDate gameDate);
}
