package com.appdev.allin.gameData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameDataRepo extends JpaRepository<GameData, Integer> {
  GameData findByOpposingTeamAndGameDateTime(String opposingTeam, String gameDateTime);
}