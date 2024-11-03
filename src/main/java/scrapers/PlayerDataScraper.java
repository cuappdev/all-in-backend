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

public class PlayerDataScraper {
    private PlayerRepo playerRepo;
    private PlayerDataRepo playerDataRepo;

    // Example url for one year of games: https://cornellbigred.com/sports/mens-basketball/stats/2021-22?path=mbball#game
    // Example url for one game: https://cornellbigred.com/boxscore.aspx?id=58875&path=mbball

    int start_year = 2018;
    int end_year = 2023;

    public PopulatePlayerData(PlayerRepo playerRepo, PlayerDataRepo playerDataRepo) {
        this.playerRepo = playerRepo;
        this.playerDataRepo = playerDataRepo;
    }

    public void populate() throws IOException {
        
    }

    public void run() {
        try {
            populate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}