package com.example.allin.gameData;


import com.example.allin.contract.Event;
import com.example.allin.contract.OpposingTeam;
import com.example.allin.player.Player;
import com.example.allin.gameData.GameData;
import com.fasterxml.jackson.annotation.JsonIgnore;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


import java.time.LocalDate;


@Entity
@Table(name = "game_data")
// game id, gameDate, game time, opposing team, location (which school), location (actual building)
public class GameData {
   @Id
   @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
   private Integer id;


   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "game_id")
   @OnDelete(action = OnDeleteAction.CASCADE)
   private Game game;


   @Column(name = "opposingTeam", nullable = false)
   private OpposingTeam opposingTeam;


   @Column(name = "opposingTeamLogo", nullable = false)
   private OpposingTeam opposingTeamLogo;


   @Column(name = "gameDate", nullable = false)
   private LocalDate gameDate;


   @Column(name = "gameTime", nullable = false)
   private DateTime gameTime;


   @Column(name = "collegeCampus", nullable = false)
   private Location collegeCampus;


   @Column(name = "actualLocation", nullable = false)
   private Location actualLocation;


   public GameData() {}


   public GameData(
           Game game,
           OpposingTeam opposingTeam,
           OpposingTeam opposingTeamLogo,
           LocalDate gameDate,
           DateTime gameTime,
           Location collegeCampus,
           Location actualLocation
           ) {
       this.game = game;
       this.opposingTeam = opposingTeam;
       this.opposingTeamLogo = opposingTeamLogo;
       this.gameDate = gameDate;
       this.gameTime = gameTime;
       this.collegeCampus = collegeCampus;
       this.actualLocation = actualLocation;
   }


   // game id
   public Integer getId() {
       return id;
   }


   public void setId(Integer id) {
       this.id = id;
   }


   // opposing team
   public OpposingTeam getOpposingTeam() {
       return opposingTeam;
   }


   public void setOpposingTeam(OpposingTeam opposingTeam) {
       this.opposingTeam = opposingTeam;
   }

    // opposing team logo
   public OpposingTeam getOpposingTeamLogo() {
       return opposingTeamLogo;
   }


   public void setOpposingTeamLogo(OpposingTeam opposingTeamLogo) {
       this.opposingTeamLogo = opposingTeamLogo;
   }
  
   // game date
   public LocalDate getGameDate() {
       return gameDate;
   }


   public void setGameDate(LocalDate gameDate) {
       this.gameDate = gameDate;
   }

    // game time
   public DateTime getGameTime() {
       return gameTime;
   }


   public void setGameTime(DateTime gameTime) {
       this.gameTime = gameTime;
   }
  
    // collegeCampus
    public Location getCollegeCampus() {
       return collegeCampus;
   }


   public void setCollegeCampus(Location collegeCampus) {
       this.collegeCampus = collegeCampus;
   }

    // actualLocation
    public Location getActualLocation() {
       return actualLocation;
   }

   public void setActualLocation(Location actualLocation) {
       this.actualLocation = actualLocation;
   }


   @Override
   public String toString() {
       return "GameData [id="
               + id
               + ", opposingTeam="
               + opposingTeam
               + ", opposingTeamLogo="
               + opposingTeamLogo
               + ", gameDate="
               + gameDate
               + ", gameTime="
               + gameTime
               + ", collegeCampus="
               + collegeCampus
               + ", actualLocation="
               + actualLocation
               + "]";
   }


   public Integer getEvent(Event e) {
       switch (e) {
           case Game:
               return getId();
           case OpposingTeam:
               return getOpposingTeam();
           case OpposingTeamLogo:
               return getOpposingTeamLogo();
           case GameDate:
               return getGameDate();
           case GameTime:
               return getGameTime();
           case CollegeCampus:
               return getCollegeCampus();
           case ActualLocation:
               return getActualLocation();
           default:
               return 0;
       }
   }
}


