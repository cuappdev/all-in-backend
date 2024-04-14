package com.example.allin.playerData;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

@Entity
public class PlayerData {
  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
  private Integer id;

  @Column(nullable = false)
  private Integer playerId;
  
  @Column(nullable = false)
  private LocalDate lastUpdate;

  @Column()
  private Boolean played;

  @Column()
  private Boolean started;

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
  private Integer offensiveRebounds;

  @Column()
  private Integer defensiveRebounds;

  @Column()
  private Integer totalRebounds;

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

  @Column()
  private Integer points;

  public PlayerData() {
  }

  public PlayerData(Integer playerId, LocalDate lastUpdate, Boolean played, Boolean started, Integer minutes,
      Integer fieldGoalsMade, Integer fieldGoalsAttempted, Integer threePointersMade, Integer threePointersAttempted,
      Integer freeThrowsMade, Integer freeThrowsAttempted, Integer offensiveRebounds, Integer defensiveRebounds,
      Integer totalRebounds, Integer assists, Integer steals, Integer blocks, Integer turnovers, Integer personalFouls,
      Integer points) {
    this.playerId = playerId;
    this.lastUpdate = lastUpdate;
    this.played = played;
    this.started = started;
    this.minutes = minutes;
    this.fieldGoalsMade = fieldGoalsMade;
    this.fieldGoalsAttempted = fieldGoalsAttempted;
    this.threePointersMade = threePointersMade;
    this.threePointersAttempted = threePointersAttempted;
    this.freeThrowsMade = freeThrowsMade;
    this.freeThrowsAttempted = freeThrowsAttempted;
    this.offensiveRebounds = offensiveRebounds;
    this.defensiveRebounds = defensiveRebounds;
    this.totalRebounds = totalRebounds;
    this.assists = assists;
    this.steals = steals;
    this.blocks = blocks;
    this.turnovers = turnovers;
    this.personalFouls = personalFouls;
    this.points = points;
  }

  public PlayerData(Integer id, Integer playerId, LocalDate lastUpdate, Boolean played, Boolean started, Integer minutes,
      Integer fieldGoalsMade, Integer fieldGoalsAttempted, Integer threePointersMade, Integer threePointersAttempted,
      Integer freeThrowsMade, Integer freeThrowsAttempted, Integer offensiveRebounds, Integer defensiveRebounds,
      Integer totalRebounds, Integer assists, Integer steals, Integer blocks, Integer turnovers, Integer personalFouls,
      Integer points) {
    this.id = id;
    this.playerId = playerId;
    this.lastUpdate = lastUpdate;
    this.played = played;
    this.started = started;
    this.minutes = minutes;
    this.fieldGoalsMade = fieldGoalsMade;
    this.fieldGoalsAttempted = fieldGoalsAttempted;
    this.threePointersMade = threePointersMade;
    this.threePointersAttempted = threePointersAttempted;
    this.freeThrowsMade = freeThrowsMade;
    this.freeThrowsAttempted = freeThrowsAttempted;
    this.offensiveRebounds = offensiveRebounds;
    this.defensiveRebounds = defensiveRebounds;
    this.totalRebounds = totalRebounds;
    this.assists = assists;
    this.steals = steals;
    this.blocks = blocks;
    this.turnovers = turnovers;
    this.personalFouls = personalFouls;
    this.points = points;
  }

  public Integer getId() {
    return id;
  }

  public Integer getPlayerId() {
    return playerId;
  }

  public LocalDate getLastUpdate() {
    return lastUpdate;
  }

  public Boolean getPlayed() {
    return played;
  }

  public Boolean getStarted() {
    return started;
  }

  public Integer getMinutes() {
    return minutes;
  }

  public Integer getFieldGoalsMade() {
    return fieldGoalsMade;
  }

  public Integer getFieldGoalsAttempted() {
    return fieldGoalsAttempted;
  }

  public Integer getThreePointersMade() {
    return threePointersMade;
  }

  public Integer getThreePointersAttempted() {
    return threePointersAttempted;
  }

  public Integer getFreeThrowsMade() {
    return freeThrowsMade;
  }

  public Integer getFreeThrowsAttempted() {
    return freeThrowsAttempted;
  }

  public Integer getOffensiveRebounds() {
    return offensiveRebounds;
  }

  public Integer getDefensiveRebounds() {
    return defensiveRebounds;
  }

  public Integer getTotalRebounds() {
    return totalRebounds;
  }

  public Integer getAssists() {
    return assists;
  }

  public Integer getSteals() {
    return steals;
  }

  public Integer getBlocks() {
    return blocks;
  }

  public Integer getTurnovers() {
    return turnovers;
  }

  public Integer getPersonalFouls() {
    return personalFouls;
  }

  public Integer getPoints() {
    return points;
  }

  public void lastUpdate(LocalDate lastUpdate) {
    this.lastUpdate = lastUpdate;
  }

  public void setPlayerId(Integer playerId) {
    this.playerId = playerId;
  }

  public void setPlayed(Boolean played) {
    this.played = played;
  }

  public void setStarted(Boolean started) {
    this.started = started;
  }

  public void setMinutes(Integer minutes) {
    this.minutes = minutes;
  }

  public void setFieldGoalsMade(Integer fieldGoalsMade) {
    this.fieldGoalsMade = fieldGoalsMade;
  }

  public void setFieldGoalsAttempted(Integer fieldGoalsAttempted) {
    this.fieldGoalsAttempted = fieldGoalsAttempted;
  }

  public void setThreePointersMade(Integer threePointersMade) {
    this.threePointersMade = threePointersMade;
  }

  public void setThreePointersAttempted(Integer threePointersAttempted) {
    this.threePointersAttempted = threePointersAttempted;
  }

  public void setFreeThrowsMade(Integer freeThrowsMade) {
    this.freeThrowsMade = freeThrowsMade;
  }

  public void setFreeThrowsAttempted(Integer freeThrowsAttempted) {
    this.freeThrowsAttempted = freeThrowsAttempted;
  }

  public void setOffensiveRebounds(Integer offensiveRebounds) {
    this.offensiveRebounds = offensiveRebounds;
  }

  public void setDefensiveRebounds(Integer defensiveRebounds) {
    this.defensiveRebounds = defensiveRebounds;
  }

  public void setTotalRebounds(Integer totalRebounds) {
    this.totalRebounds = totalRebounds;
  }

  public void setAssists(Integer assists) {
    this.assists = assists;
  }

  public void setSteals(Integer steals) {
    this.steals = steals;
  }

  public void setBlocks(Integer blocks) {
    this.blocks = blocks;
  }

  public void setTurnovers(Integer turnovers) {
    this.turnovers = turnovers;
  }

  public void setPersonalFouls(Integer personalFouls) {
    this.personalFouls = personalFouls;
  }

  public void setPoints(Integer points) {
    this.points = points;
  }

  @Override
  public String toString() {
    return "PlayerData [id=" + id + ", playerId=" + playerId + ", lastUpdate=" + lastUpdate + ", played=" + played
        + ", started=" + started + ", minutes=" + minutes + ", fieldGoalsMade=" + fieldGoalsMade
        + ", fieldGoalsAttempted=" + fieldGoalsAttempted + ", threePointersMade=" + threePointersMade
        + ", threePointersAttempted=" + threePointersAttempted + ", freeThrowsMade=" + freeThrowsMade
        + ", freeThrowsAttempted=" + freeThrowsAttempted + ", offensiveRebounds=" + offensiveRebounds
        + ", defensiveRebounds=" + defensiveRebounds + ", totalRebounds=" + totalRebounds + ", assists=" + assists
        + ", steals=" + steals + ", blocks=" + blocks + ", turnovers=" + turnovers + ", personalFouls=" + personalFouls
        + ", points=" + points + "]";
  }
}