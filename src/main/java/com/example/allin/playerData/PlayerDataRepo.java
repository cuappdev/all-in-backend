package com.example.allin.playerData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerDataRepo extends JpaRepository<PlayerData, Integer> {}