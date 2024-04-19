package com.example.allin.contract;

import com.example.allin.user.User;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "contracts")
public class Contract {
  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
  private Integer id;

  // Change to player and add foregin key
  @Column(nullable = false)
  private Integer playerId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "owner_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private User owner;

  @Column(name = "rarity", nullable = false)
  private Rarity rarity;

  @Column(name = "opposingTeam", nullable = false)
  private String opposingTeam;

  @Column(name = "opposingTeamImage", nullable = false)
  private String opposingTeamImage;

  @Column(name = "event", nullable = false)
  private String event;

  @Column(name = "eventThreshold", nullable = false)
  private Integer eventThreshold;

  @Column(name = "creationTime", nullable = false)
  private LocalDate creationTime = LocalDate.now();

  @Column(name = "value", nullable = false)
  private Double value;

  @Column(name = "forSale", nullable = false)
  private Boolean forSale = false;

  @Column(name = "sellPrice", nullable = true)
  private Double sellPrice;

  public Contract() {
  }

  public Contract(Integer playerId, User owner, Rarity rarity, String opposingTeam, String opposingTeamImage,
      String event,
      Integer eventThreshold, Double value, Boolean forSale, Double sellPrice) {
    this.playerId = playerId;
    this.owner = owner;
    this.rarity = rarity;
    this.opposingTeam = opposingTeam;
    this.opposingTeamImage = opposingTeamImage;
    this.event = event;
    this.eventThreshold = eventThreshold;
    this.value = value;
    this.forSale = forSale;
    this.sellPrice = sellPrice;
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

  @JsonIgnore
  public User getOwner() {
    return owner;
  }

  @JsonIgnore
  public void setOwner(User owner) {
    this.owner = owner;
  }

  public Rarity getRarity() {
    return rarity;
  }

  public void setRarity(Rarity rarity) {
    this.rarity = rarity;
  }

  public String getOpposingTeam() {
    return opposingTeam;
  }

  public void setOpposingTeam(String opposingTeam) {
    this.opposingTeam = opposingTeam;
  }

  public String getOpposingTeamImage() {
    return opposingTeamImage;
  }

  public void setOpposingTeamImage(String opposingTeamImage) {
    this.opposingTeamImage = opposingTeamImage;
  }

  public String getEvent() {
    return event;
  }

  public void setEvent(String event) {
    this.event = event;
  }

  public Integer getEventThreshold() {
    return eventThreshold;
  }

  public void setEventThreshold(Integer eventThreshold) {
    this.eventThreshold = eventThreshold;
  }

  public LocalDate getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(LocalDate creationTime) {
    this.creationTime = creationTime;
  }

  public Double getValue() {
    return value;
  }

  public void setValue(Double value) {
    this.value = value;
  }

  public Boolean getForSale() {
    return forSale;
  }

  public void setForSale(Boolean forSale) {
    this.forSale = forSale;
  }

  public Double getSellPrice() {
    return sellPrice;
  }

  public void setSellPrice(Double sellPrice) {
    this.sellPrice = sellPrice;
  }

  @Override
  public String toString() {
    return "Contract{" +
        "id=" + id +
        ", playerId=" + playerId +
        // ", owner=" + owner +
        ", rarity=" + rarity +
        ", opposingTeam='" + opposingTeam + '\'' +
        ", opposingTeamImage='" + opposingTeamImage + '\'' +
        ", event='" + event + '\'' +
        ", eventThreshold=" + eventThreshold +
        ", creationTime=" + creationTime +
        ", value=" + value +
        ", forSale=" + forSale +
        ", sellPrice=" + sellPrice +
        '}';
  }

}