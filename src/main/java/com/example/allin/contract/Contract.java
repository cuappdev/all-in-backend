package com.example.allin.contract;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Contract {
  enum Rarity {
    Common,
    Rare,
    Epic,
    Legendary
  }

  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
  private Integer id;

  @Column(nullable = false)
  private Integer playerId;

  @Column(nullable = false)
  private Integer owner;

  @Column(nullable = false)
  private Rarity rarity = Rarity.Common;

  @Column(nullable = false)
  private String event;

  @Column(nullable = false)
  private Integer eventThreshold;

  @Column(nullable = false)
  private LocalDate creationTime = LocalDate.now();

  @Column(nullable = false)
  private LocalDate expirationTime;

  @Column(nullable = false)
  private double value;

  @Column(nullable = false)
  private boolean forSale = false;

  private double sellPrice;

  public Contract() {
  }

  public Contract(Integer playerId, Integer owner, String event, Integer eventThreshold, LocalDate creationTime,
      LocalDate expirationTime,
      double value, boolean forSale, double sellPrice) {
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
      LocalDate expirationTime, double value, boolean forSale, double sellPrice) {
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

  public double getValue() {
    return value;
  }

  public boolean isForSale() {
    return forSale;
  }

  public double getSellPrice() {
    return sellPrice;
  }

  public void setOwner(int owner) {
    this.owner = owner;
  }

  public void setExpirationTime(LocalDate expirationTime) {
    this.expirationTime = expirationTime;
  }

  public void setForSale(boolean forSale) {
    this.forSale = forSale;
  }

  public void setSellPrice(double sellPrice) {
    this.sellPrice = sellPrice;
  }

  @Override
  public String toString() {
    return "Contract(id=" + this.id + ", playerId=" + this.playerId + ", owner=" + this.owner + ", event=" + this.event
        + ", eventThreshold=" + this.eventThreshold + ", creationTime=" + this.creationTime + ", expirationTime="
        + this.expirationTime + ", value=" + this.value + ", forSale=" + this.forSale + ", sellPrice=" + this.sellPrice
        + ")";
  }

}