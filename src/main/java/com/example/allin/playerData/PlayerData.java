package com.example.allin.playerData;

import java.time.LocalDate;

import com.example.allin.contract.OpposingTeam;
import com.example.allin.player.Player;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "player_data")
public class PlayerData {
  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "player_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Player player;

  @Column(name = "gameDate", nullable = false)
  private LocalDate gameDate;

  @Column(name = "opposingTeam", nullable = false)
  private OpposingTeam opposingTeam;

  @Column(name = "points", nullable = false)
  private Integer points;

  @Column(name = "minutes", nullable = false)
  private Integer minutes;

  @Column(name = "fieldGoals", nullable = false)
  private Integer fieldGoals;

  @Column(name = "threePointers", nullable = false)
  private Integer threePointers;

  @Column(name = "freeThrows", nullable = false)
  private Integer freeThrows;

  @Column(name = "rebounds", nullable = false)
  private Integer rebounds;

  @Column(name = "assists", nullable = false)
  private Integer assists;

  @Column(name = "steals", nullable = false)
  private Integer steals;

  @Column(name = "blocks", nullable = false)
  private Integer blocks;

  @Column(name = "turnovers", nullable = false)
  private Integer turnovers;

  @Column(name = "fouls", nullable = false)
  private Integer fouls;

  public PlayerData() {
  }

  public PlayerData(Player player, LocalDate gameDate, OpposingTeam opposingTeam, Integer points, Integer minutes,
      Integer fieldGoals, Integer threePointers, Integer freeThrows, Integer rebounds, Integer assists,
      Integer steals,
      Integer blocks, Integer turnovers, Integer fouls) {
    this.player = player;
    this.gameDate = gameDate;
    this.opposingTeam = opposingTeam;
    this.points = points;
    this.minutes = minutes;
    this.fieldGoals = fieldGoals;
    this.threePointers = threePointers;
    this.freeThrows = freeThrows;
    this.rebounds = rebounds;
    this.assists = assists;
    this.steals = steals;
    this.blocks = blocks;
    this.turnovers = turnovers;
    this.fouls = fouls;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @JsonIgnore
  public Player getPlayer() {
    return player;
  }

  public Integer getPlayerId() {
    return player.getId();
  }

  @JsonIgnore
  public void setPlayer(Player player) {
    this.player = player;
  }

  public LocalDate getGameDate() {
    return gameDate;
  }

  public void setGameDate(LocalDate gameDate) {
    this.gameDate = gameDate;
  }

  public OpposingTeam getOpposingTeam() {
    return opposingTeam;
  }

  public void setOpposingTeam(OpposingTeam opposingTeam) {
    this.opposingTeam = opposingTeam;
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

  public Integer getFieldGoals() {
    return fieldGoals;
  }

  public void setFieldGoals(Integer fieldGoals) {
    this.fieldGoals = fieldGoals;
  }

  public Integer getThreePointers() {
    return threePointers;
  }

  public void setThreePointers(Integer threePointers) {
    this.threePointers = threePointers;
  }

  public Integer getFreeThrows() {
    return freeThrows;
  }

  public void setFreeThrows(Integer freeThrows) {
    this.freeThrows = freeThrows;
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

  public Integer getFouls() {
    return fouls;
  }

  public void setFouls(Integer fouls) {
    this.fouls = fouls;
  }

  @Override
  public String toString() {
    return "PlayerData [id=" + id + ", player=" + player + ", gameDate=" + gameDate + ", opposingTeam=" + opposingTeam
        + ", points=" + points + ", minutes=" + minutes + ", fieldGoals=" + fieldGoals + ", threePointers="
        + threePointers + ", freeThrows=" + freeThrows + ", rebounds=" + rebounds + ", assists=" + assists + ", steals="
        + steals + ", blocks=" + blocks + ", turnovers=" + turnovers + ", fouls=" + fouls + "]";
  }
}