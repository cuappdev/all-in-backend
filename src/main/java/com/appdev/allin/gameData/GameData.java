package com.appdev.allin.gameData;


import com.appdev.allin.gameData.GameData;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "game_data")
public class GameData {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
    private Integer id;

    @Column(name = "opposingTeam", nullable = false)
    private String opposingTeam;
 
    @Column(name = "gameDateTime", nullable = false)
    private String gameDateTime;

    @Column(name = "fullLocation", nullable = false)
    private String fullLocation;

    @Column(name= "logoUrl", nullable = false)
    private String logoUrl;


    public GameData() {}
  
    public GameData(
        String opposingTeam,
        String gameDateTime,
        String fullLocation,
        String logoUrl

        ) {
        this.opposingTeam = opposingTeam;
        this.gameDateTime = gameDateTime;
        this.fullLocation = fullLocation;
        this.logoUrl = logoUrl;
    }

    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpposingTeam() {
        return opposingTeam;
    }

    public void setOpposingTeam(String opposingTeam) {
        this.opposingTeam = opposingTeam;
    }

    public String getGameDateTime() {
        return gameDateTime;
    }

    public void setGameDateTime(String gameDateTime) {
        this.gameDateTime = gameDateTime;
    }

    public String getFullLocation() {
        return fullLocation;
    }

    public void setFullLocation(String fullLocation) {
        this.fullLocation = fullLocation;
    }
    
    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

}