package com.appdev.allin;

import com.appdev.allin.player.Player;
import com.appdev.allin.player.PlayerRepo;
import com.appdev.allin.player.Position;
import com.appdev.allin.playerData.PlayerDataRepo;
import com.appdev.allin.playerData.util.PopulatePlayerData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    @Autowired PlayerRepo playerRepo;

    @Autowired PlayerDataRepo playerDataRepo;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner populatePlayers(PlayerRepo playerRepo, PlayerDataRepo playerDataRepo) {
        return (args) -> {
            String baseUrl = "src/main/resources/static/images/players/";

            Player chris_manon =
                    new Player(
                            "Chris",
                            "Manon",
                            new Position[] {Position.Guard},
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
                            new Position[] {Position.Guard},
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
                            new Position[] {Position.Guard},
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
                            new Position[] {Position.Forward},
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
                            new Position[] {Position.Forward},
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
                            new Position[] {Position.Guard},
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
                            new Position[] {Position.Forward},
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
                            new Position[] {Position.Forward},
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
                            new Position[] {Position.Forward},
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
                            new Position[] {Position.Guard},
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
                            new Position[] {Position.Guard},
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
                            new Position[] {Position.Guard},
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
                            new Position[] {Position.Forward},
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
                            new Position[] {Position.Forward},
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
                            new Position[] {Position.Guard},
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
                            new Position[] {Position.Forward},
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
                            new Position[] {Position.Forward, Position.Center},
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
                            new Position[] {Position.Guard},
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
                            new Position[] {Position.Forward},
                            10,
                            "6'8",
                            218,
                            "American Fork, Utah",
                            "American Fork HS",
                            baseUrl + "hayden.jpeg");

            playerRepo.save(chris_manon);
            playerRepo.save(nazir_williams);
            playerRepo.save(isaiah_gray);
            playerRepo.save(guy_ragland);
            playerRepo.save(sean_hansen);
            playerRepo.save(cooper_noard);
            playerRepo.save(ak_okereke);
            playerRepo.save(keller_boothby);
            playerRepo.save(ian_imegwu);
            playerRepo.save(jake_fiegen);
            playerRepo.save(jacob_beccles);
            playerRepo.save(max_watson);
            playerRepo.save(chris_cain);
            playerRepo.save(evan_williams);
            playerRepo.save(adam_hinton);
            playerRepo.save(dj_nix);
            playerRepo.save(ryan_kiachian);
            playerRepo.save(darius_ervin);
            playerRepo.save(hayden_franson);

            PopulatePlayerData populatePlayerData =
                    new PopulatePlayerData(playerRepo, playerDataRepo);
            populatePlayerData.run();
        };
    }
}
