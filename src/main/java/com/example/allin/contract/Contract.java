package com.example.allin.contract;

import com.example.allin.user.User;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "contracts")
public class Contract {
  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
  private Integer id;

  // Change to player and add foregin key
  @Column(nullable = false)
  private Integer playerId;

  // Add foreign key
  @Column(nullable = false)
  private Integer owner;

  @Column(nullable = false)
  private Rarity rarity;

  @Column(nullable = false)
  private String event;

  @Column(nullable = false)
  private Integer eventThreshold;

  @Column(nullable = false)
  private LocalDate creationTime = LocalDate.now();

  @Column(nullable = false)
  private LocalDate expirationTime;

  @Column(nullable = false)
  private Double value;

  @Column(nullable = false)
  private Boolean forSale = false;

  private Double sellPrice;

  @ManyToOne
  private User user;

  public Contract() {
  }

  public Contract(Integer playerId, Integer owner, String event, Integer eventThreshold, LocalDate creationTime,
      LocalDate expirationTime,
      Double value, Boolean forSale, Double sellPrice) {
    this.playerId = playerId;
    this.owner = owner;
    this.event = event;
    this.eventThreshold = eventThreshold;
    this.creationTime = creationTime;
    this.expirationTime = expirationTime;
    this.value = value;
    this.forSale = forSale;
    this.sellPrice = sellPrice;
  }

  public Contract(Integer id, Integer playerId, Integer owner, String event, Integer eventThreshold,
      LocalDate creationTime,
      LocalDate expirationTime, Double value, Boolean forSale, Double sellPrice) {
    this.id = id;
    this.playerId = playerId;
    this.owner = owner;
    this.event = event;
    this.eventThreshold = eventThreshold;
    this.creationTime = creationTime;
    this.expirationTime = expirationTime;
    this.value = value;
    this.forSale = forSale;
    this.sellPrice = sellPrice;
  }

  public Integer getId() {
    return id;
  }

  public Integer getPlayerId() {
    return playerId;
  }

  public Integer getOwner() {
    return owner;
  }

  public Rarity getRarity() {
    return rarity;
  }

  public String getEvent() {
    return event;
  }

  public Integer getEventThreshold() {
    return eventThreshold;
  }

  public LocalDate getCreationTime() {
    return creationTime;
  }

  public LocalDate getExpirationTime() {
    return expirationTime;
  }

  public Double getValue() {
    return value;
  }

  public Boolean getForSale() {
    return forSale;
  }

  public Double getSellPrice() {
    return sellPrice;
  }

  public void setPlayerId(int playerId) {
    this.playerId = playerId;
  }

  public void setOwner(int owner) {
    this.owner = owner;
  }

  public void setRarity(Rarity rarity) {
    this.rarity = rarity;
  }

  public void setEvent(String event) {
    this.event = event;
  }

  public void setEventThreshold(int eventThreshold) {
    this.eventThreshold = eventThreshold;
  }

  public void setCreationTime(LocalDate creationTime) {
    this.creationTime = creationTime;
  }

  public void setExpirationTime(LocalDate expirationTime) {
    this.expirationTime = expirationTime;
  }

  public void setValue(double value) {
    this.value = value;
  }

  public void setForSale(boolean forSale) {
    this.forSale = forSale;
  }

  public void setSellPrice(double sellPrice) {
    this.sellPrice = sellPrice;
  }

  @Override
  public String toString() {
    return "Contract(id=" + this.id + ", playerId=" + this.playerId + ", owner=" + this.owner + ", rarity="
        + this.rarity + ", event=" + this.event + ", eventThreshold=" + this.eventThreshold + ", creationTime="
        + this.creationTime + ", expirationTime=" + this.expirationTime + ", value=" + this.value + ", forSale="
        + this.forSale + ", sellPrice=" + this.sellPrice + ")";
  }

}