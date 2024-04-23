package com.example.allin.playerData.util;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.example.allin.player.Player;
import com.example.allin.player.PlayerRepo;

import com.example.allin.playerData.PlayerData;
import com.example.allin.playerData.PlayerDataRepo;

public class PopulatePlayerData {

  private PlayerRepo playerRepo;
  private PlayerDataRepo playerDataRepo;
  String baseUrl = "https://cornellbigred.com/boxscore.aspx?id=";

  int[] ids = {
      58875,
      59093,
      59018,
      58876,
      59200,
      59057,
      58877,
      58878,
      58874,
      58879,
      59092,
      58881,
      58882,
      57826,
      57827,
      57828,
      59255,
      57829,
      57830,
      57831,
      57832,
      57833,
      57834,
      57835,
      57836,
      57837,
      57838,
      57839,
      59254
  };

  public PopulatePlayerData(PlayerRepo playerRepo, PlayerDataRepo playerDataRepo) {
    this.playerRepo = playerRepo;
    this.playerDataRepo = playerDataRepo;
  }

  public void populate() throws IOException {
    for (int id : ids) {
      String url = baseUrl + id;
      Document doc = Jsoup.connect(url).get();
      System.out.println(doc);
      break;
    }

    // PlayerData playerData = new PlayerData();
    // playerDataRepo.save(playerData);
  }

  public void run() {
    try {
      populate();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
