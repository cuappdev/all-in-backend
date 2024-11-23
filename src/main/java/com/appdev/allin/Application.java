package com.appdev.allin;

import com.appdev.allin.player.PlayerRepo;
import com.appdev.allin.playerData.PlayerDataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

  @Autowired PlayerRepo playerRepo;

  @Autowired PlayerDataRepo playerDataRepo;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
