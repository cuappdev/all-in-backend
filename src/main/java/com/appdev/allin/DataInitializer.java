package com.appdev.allin;

import com.appdev.allin.player.Player;
import com.appdev.allin.player.PlayerRepo;
import com.appdev.allin.player.Position;
import com.appdev.allin.playerData.PlayerDataRepo;
import com.appdev.allin.playerData.util.PopulatePlayerData;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    private final PlayerRepo playerRepo;
    private final PlayerDataRepo playerDataRepo;

    public DataInitializer(PlayerRepo playerRepo, PlayerDataRepo playerDataRepo) {
        this.playerRepo = playerRepo;
        this.playerDataRepo = playerDataRepo;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initializeData() {
        populatePlayers();
        populatePlayerData();
    }

    // TODO: Hard coded, implement with scraper when done
    private void populatePlayers() {
        String baseUrl = "src/main/resources/static/images/players/";

        Player chris_manon =
                new Player(
                        "Chris",
                        "Manon",
                        new Position[] {Position.GUARD},
                        30,
                        "6'5",
                        209,
                        "New Milford, N.J.",
                        "Saint Joseph Regional HS",
                        baseUrl + "chris_manon.jpeg");
        Player nazir_williams =
                new Player(
                        "Nazir",
                        "Williams",
                        new Position[] {Position.GUARD},
                        1,
                        "6'3",
                        180,
                        "Nyack, N.Y.",
                        "South Kent School",
                        baseUrl + "nazir.jpeg");
        Player isaiah_gray =
                new Player(
                        "Isaiah",
                        "Gray",
                        new Position[] {Position.GUARD},
                        13,
                        "6'3",
                        219,
                        "Brooklyn, N.Y.",
                        "Martin Luther King HS",
                        baseUrl + "isaiah.jpeg");
        Player guy_ragland =
                new Player(
                        "Guy",
                        "Ragland Jr.",
                        new Position[] {Position.FORWARD},
                        21,
                        "6'8",
                        246,
                        "West Hartford, Conn.",
                        "Northwest Catholic HS",
                        baseUrl + "guy.jpeg");
        Player sean_hansen =
                new Player(
                        "Sean",
                        "Hansen",
                        new Position[] {Position.FORWARD},
                        20,
                        "6'9",
                        246,
                        "Ramsey, N.J.",
                        "Ramsey HS",
                        baseUrl + "sean.jpeg");
        Player cooper_noard =
                new Player(
                        "Cooper",
                        "Noard",
                        new Position[] {Position.GUARD},
                        31,
                        "6'2",
                        190,
                        "Glenview, Ill.",
                        "Glenbrook South HS",
                        baseUrl + "cooper.jpeg");
        Player ak_okereke =
                new Player(
                        "AK",
                        "Okereke",
                        new Position[] {Position.FORWARD},
                        12,
                        "6'7",
                        235,
                        "Clovis, Calif.",
                        "Clovis North HS",
                        baseUrl + "ak.jpeg");
        Player keller_boothby =
                new Player(
                        "Keller",
                        "Boothby",
                        new Position[] {Position.FORWARD},
                        15,
                        "6'7",
                        223,
                        "Plano, Texas",
                        "Prestonwood Christian Academy",
                        baseUrl + "keller.jpeg");
        Player ian_imegwu =
                new Player(
                        "Ian",
                        "Imegwu",
                        new Position[] {Position.FORWARD},
                        2,
                        "6'9",
                        226,
                        "Short Hills, N.J.",
                        "Blair Academy",
                        baseUrl + "ian.jpeg");
        Player jake_fiegen =
                new Player(
                        "Jake",
                        "Fiegen",
                        new Position[] {Position.FORWARD},
                        22,
                        "6'4",
                        205,
                        "Wilmette, Ill.",
                        "New Trier HS",
                        baseUrl + "jake.jpeg");
        Player jacob_beccles =
                new Player(
                        "Jacob",
                        "Beccles",
                        new Position[] {Position.GUARD},
                        5,
                        "6'3",
                        178,
                        "Philadelphia, Pa.",
                        "Constitution HS",
                        baseUrl + "jacob.jpeg");
        Player max_watson =
                new Player(
                        "Max",
                        "Watson",
                        new Position[] {Position.GUARD},
                        25,
                        "6'4",
                        195,
                        "Brigham City, Utah",
                        "Box Elder HS",
                        baseUrl + "max.jpeg");
        Player chris_cain =
                new Player(
                        "Chris",
                        "Cain",
                        new Position[] {Position.FORWARD},
                        33,
                        "6'8",
                        223,
                        "Middletown, N.Y.",
                        "Pine Bush HS",
                        baseUrl + "chris.jpeg");
        Player evan_williams =
                new Player(
                        "Evan",
                        "Williams",
                        new Position[] {Position.FORWARD},
                        0,
                        "6'7",
                        220,
                        "Murphy, Texas",
                        "Plane East Senior HS",
                        baseUrl + "evan.jpeg");
        Player adam_hinton =
                new Player(
                        "Adam",
                        "Tsang Hinton",
                        new Position[] {Position.GUARD},
                        11,
                        "6'5",
                        213,
                        "Studio City, Calif.",
                        "Harvard-Westlake School",
                        baseUrl + "adam.jpeg");
        Player dj_nix =
                new Player(
                        "DJ",
                        "Nix",
                        new Position[] {Position.FORWARD},
                        23,
                        "6'6",
                        216,
                        "Harrisburg, N.C.",
                        "Cannon School",
                        baseUrl + "dj.jpeg");
        Player ryan_kiachian =
                new Player(
                        "Ryan",
                        "Kiachian",
                        new Position[] {Position.FORWARD, Position.CENTER},
                        3,
                        "6'10",
                        228,
                        "Los Altos Hills, Calif.",
                        "Bellarmine College Preparatory",
                        baseUrl + "ryan.jpeg");
        Player darius_ervin =
                new Player(
                        "Darius",
                        "Ervin",
                        new Position[] {Position.GUARD},
                        14,
                        "5'8",
                        170,
                        "Brooklyn, N.Y.",
                        "Northfield Mount Hermon School",
                        baseUrl + "darius.jpeg");
        Player hayden_franson =
                new Player(
                        "Hayden",
                        "Franson",
                        new Position[] {Position.FORWARD},
                        10,
                        "6'8",
                        218,
                        "American Fork, Utah",
                        "American Fork HS",
                        baseUrl + "hayden.jpeg");

        Player[] players = {
            chris_manon,
            nazir_williams,
            isaiah_gray,
            guy_ragland,
            sean_hansen,
            cooper_noard,
            ak_okereke,
            keller_boothby,
            ian_imegwu,
            jake_fiegen,
            jacob_beccles,
            max_watson,
            chris_cain,
            evan_williams,
            adam_hinton,
            dj_nix,
            ryan_kiachian,
            darius_ervin,
            hayden_franson
        };

        for (Player player : players) {
            if (playerRepo.findByNumber(player.getNumber()) == null) {
                playerRepo.save(player);
            }
        }
    }

    // TODO: Tie in with scraper data
    private void populatePlayerData() {
        PopulatePlayerData populatePlayerData = new PopulatePlayerData(playerRepo, playerDataRepo);
        populatePlayerData.run();
    }
}
