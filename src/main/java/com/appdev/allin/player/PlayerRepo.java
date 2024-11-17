package com.appdev.allin.player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepo extends JpaRepository<Player, Integer> {
    Player findByFirstNameAndLastName(String firstName, String lastName);
    Player findByNumber(Integer number);
}