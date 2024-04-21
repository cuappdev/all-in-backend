package com.example.allin.playerData;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "player_data")
public class PlayerData {
  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
  private Integer id;

  @Column(nullable = false)
  private Integer playerId;
  
  @Column(nullable = false)
  private LocalDate gameDate;

  @Column(nullable = false)
  private String opponent;

  @Column()
  private Boolean played;

  @Column()
  private Integer points;

  @Column()
  private Integer minutes;

  @Column()
  private Integer fieldGoalsMade;

  @Column()
  private Integer fieldGoalsAttempted;

  @Column()
  private Integer threePointersMade;

  @Column()
  private Integer threePointersAttempted;

  @Column()
  private Integer freeThrowsMade;

  @Column()
  private Integer freeThrowsAttempted;

  @Column()
  private Integer rebounds;

  @Column()
  private Integer assists;

  @Column()
  private Integer steals;

  @Column()
  private Integer blocks;

  @Column()
  private Integer turnovers;

  @Column()
  private Integer personalFouls;

  public PlayerData() {
  }

  public PlayerData(Integer playerId, LocalDate gameDate, String opponent, Boolean played, Integer points, Integer minutes,
      Integer fieldGoalsMade, Integer fieldGoalsAttempted, Integer threePointersMade, Integer threePointersAttempted,
      Integer freeThrowsMade, Integer freeThrowsAttempted, Integer rebounds, Integer assists, Integer steals,
      Integer blocks, Integer turnovers, Integer personalFouls) {
    this.playerId = playerId;
    this.gameDate = gameDate;
    this.opponent = opponent;
    this.played = played;
    this.points = points;
    this.minutes = minutes;
    this.fieldGoalsMade = fieldGoalsMade;
    this.fieldGoalsAttempted = fieldGoalsAttempted;
    this.threePointersMade = threePointersMade;
    this.threePointersAttempted = threePointersAttempted;
    this.freeThrowsMade = freeThrowsMade;
    this.freeThrowsAttempted = freeThrowsAttempted;
    this.rebounds = rebounds;
    this.assists = assists;
    this.steals = steals;
    this.blocks = blocks;
    this.turnovers = turnovers;
    this.personalFouls = personalFouls;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getPlayerId() {
    return playerId;
  }

  public void setPlayerId(Integer playerId) {
    this.playerId = playerId;
  }

  public LocalDate getGameDate() {
    return gameDate;
  }

  public void setGameDate(LocalDate gameDate) {
    this.gameDate = gameDate;
  }

  public String getOpponent() {
    return opponent;
  }

  public void setOpponent(String opponent) {
    this.opponent = opponent;
  }

  public Boolean getPlayed() {
    return played;
  }

  public void setPlayed(Boolean played) {
    this.played = played;
  }

  public Integer getPoints() {
    return points;
  }

  public void setPoints(Integer points) {
    this.points = points;
  }

  public Integer getMinutes() {
    return minutes;
  }

  public void setMinutes(Integer minutes) {
    this.minutes = minutes;
  }

  public Integer getFieldGoalsMade() {
    return fieldGoalsMade;
  }

  public void setFieldGoalsMade(Integer fieldGoalsMade) {
    this.fieldGoalsMade = fieldGoalsMade;
  }

  public Integer getFieldGoalsAttempted() {
    return fieldGoalsAttempted;
  }

  public void setFieldGoalsAttempted(Integer fieldGoalsAttempted) {
    this.fieldGoalsAttempted = fieldGoalsAttempted;
  }

  public Integer getThreePointersMade() {
    return threePointersMade;
  }

  public void setThreePointersMade(Integer threePointersMade) {
    this.threePointersMade = threePointersMade;
  }

  public Integer getThreePointersAttempted() {
    return threePointersAttempted;
  }

  public void setThreePointersAttempted(Integer threePointersAttempted) {
    this.threePointersAttempted = threePointersAttempted;
  }

  public Integer getFreeThrowsMade() {
    return freeThrowsMade;
  }

  public void setFreeThrowsMade(Integer freeThrowsMade) {
    this.freeThrowsMade = freeThrowsMade;
  }

  public Integer getFreeThrowsAttempted() {
    return freeThrowsAttempted;
  }

  public void setFreeThrowsAttempted(Integer freeThrowsAttempted) {
    this.freeThrowsAttempted = freeThrowsAttempted;
  }

  public Integer getRebounds() {
    return rebounds;
  }

  public void setRebounds(Integer rebounds) {
    this.rebounds = rebounds;
  }

  public Integer getAssists() {
    return assists;
  }

  public void setAssists(Integer assists) {
    this.assists = assists;
  }

  public Integer getSteals() {
    return steals;
  }

  public void setSteals(Integer steals) {
    this.steals = steals;
  }

  public Integer getBlocks() {
    return blocks;
  }

  public void setBlocks(Integer blocks) {
    this.blocks = blocks;
  }

  public Integer getTurnovers() {
    return turnovers;
  }

  public void setTurnovers(Integer turnovers) {
    this.turnovers = turnovers;
  }

  public Integer getPersonalFouls() {
    return personalFouls;
  }

  public void setPersonalFouls(Integer personalFouls) {
    this.personalFouls = personalFouls;
  }

  @Override
  public String toString() {
    return "PlayerData [id=" + id + ", playerId=" + playerId + ", gameDate=" + gameDate + ", opponent=" + opponent
        + ", played=" + played + ", points=" + points + ", minutes=" + minutes + ", fieldGoalsMade=" + fieldGoalsMade
        + ", fieldGoalsAttempted=" + fieldGoalsAttempted + ", threePointersMade=" + threePointersMade
        + ", threePointersAttempted=" + threePointersAttempted + ", freeThrowsMade=" + freeThrowsMade
        + ", freeThrowsAttempted=" + freeThrowsAttempted + ", rebounds=" + rebounds + ", assists=" + assists
        + ", steals=" + steals + ", blocks=" + blocks + ", turnovers=" + turnovers + ", personalFouls=" + personalFouls
        + "]";
  }
}