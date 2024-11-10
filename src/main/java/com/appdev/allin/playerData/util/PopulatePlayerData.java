package com.appdev.allin.playerData.util;

import com.appdev.allin.contract.OpposingTeam;
import com.appdev.allin.player.Player;
import com.appdev.allin.player.PlayerRepo;
import com.appdev.allin.playerData.PlayerData;
import com.appdev.allin.playerData.PlayerDataRepo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PopulatePlayerData {

    private PlayerRepo playerRepo;
    private PlayerDataRepo playerDataRepo;
    String baseUrl = "https://cornellbigred.com/boxscore.aspx?id=";

    int[] ids = {
        58875,
        // 59093, 59018, 58876, 59200, 59057, 58877, 58878, 58874, 58879, 59092, 58881,
        // 58882,
        // 57826, 57827, 57828, 59255, 57829, 57830, 57831, 57832, 57833, 57834, 57835,
        // 57836, 57837,
        // 57838, 57839, 59254
    };

    public PopulatePlayerData(PlayerRepo playerRepo, PlayerDataRepo playerDataRepo) {
        this.playerRepo = playerRepo;
        this.playerDataRepo = playerDataRepo;
    }

    public void populate() throws IOException {
        for (int id : ids) {
            String url = baseUrl + id;
            Document doc = Jsoup.connect(url).get();
            String dateText = doc.getElementsByTag("dd").get(0).text();
            LocalDate gameDate = LocalDate.parse(dateText, DateTimeFormatter.ofPattern("MM/dd/yy"));
            Elements opposingTeamElement = doc.getElementsByClass("game-details-history-link");
            int index = opposingTeamElement.get(0).text().indexOf("vs ");
            String opposingTeam = opposingTeamElement.get(0).text().substring(index + 3);
            if (opposingTeam.equals("SUNY Morrisville")) {
                opposingTeam = "Morrisville";
            } else if (opposingTeam.equals("George Mason")) {
                opposingTeam = "George";
            } else if (opposingTeam.equals("Cal St. Fullerton")) {
                opposingTeam = "Fullerton";
            } else if (opposingTeam.equals("Utah Valley")) {
                opposingTeam = "Utah";
            } else if (opposingTeam.equals("Robert Morris")) {
                opposingTeam = "Robert";
            } else if (opposingTeam.equals("Ohio St.")) {
                opposingTeam = "Ohio";
            }
            Elements sections = doc.getElementsByTag("section");
            // check which section whose aria label contains Cornell

            Elements stats = doc.getElementsByTag("tr");
            // get the 2 and 3 index, and get the first h3 inside of that
            String team1 = sections.get(2).getElementsByTag("h3").get(0).text();
            if (team1.contains("Cornell")) {
                stats = sections.get(2).getElementsByTag("tr");
            } else {
                stats = sections.get(3).getElementsByTag("tr");
            }

            int i = 1;
            while (stats.get(i).getElementsByTag("td").get(0).text().equals("TM") == false) {
                Elements playerStats = stats.get(i).getElementsByTag("td");
                int playerNumber = Integer.parseInt(playerStats.get(0).text());
                String minString = playerStats.get(3).text();
                int minutes = 0;
                if (!minString.contains("+")) {
                    minutes = Integer.parseInt(minString);
                }
                int fieldGoalsMade = Integer.parseInt(playerStats.get(4).text().split("-")[0]);
                int threePointersMade = Integer.parseInt(playerStats.get(5).text().split("-")[0]);
                int freeThrowsMade = Integer.parseInt(playerStats.get(6).text().split("-")[0]);
                int rebounds = Integer.parseInt(playerStats.get(8).text());
                int personalFouls = Integer.parseInt(playerStats.get(9).text());
                int assists = Integer.parseInt(playerStats.get(10).text());
                int turnovers = Integer.parseInt(playerStats.get(11).text());
                int blocks = Integer.parseInt(playerStats.get(12).text());
                int steals = Integer.parseInt(playerStats.get(13).text());
                int points = Integer.parseInt(playerStats.get(14).text());
                Player player = playerRepo.findByNumber(playerNumber);

                PlayerData playerData =
                        new PlayerData(
                                player,
                                gameDate,
                                OpposingTeam.valueOf(opposingTeam),
                                points,
                                minutes,
                                fieldGoalsMade,
                                threePointersMade,
                                freeThrowsMade,
                                rebounds,
                                assists,
                                steals,
                                blocks,
                                turnovers,
                                personalFouls);

                if (playerDataRepo.findByPlayerAndGameDate(player, gameDate) == null) {
                    playerDataRepo.save(playerData);
                }
                ++i;
            }
        }
    }

    public void run() {
        try {
            populate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
