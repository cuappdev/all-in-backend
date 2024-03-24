package com.example.allin.contract;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Contract {
  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
  private Integer id;

  private int playerId;
  // player_id integer [not null]
  private int owner;
  // owner integer [not null]

  // rarity rarity

  private String event;
  // event varchar
  private int eventThreshold;
  // event_threshold varchar
  private LocalDate creationTime;
  // creation_date datetime [not null, default: `now()`]
  private LocalDate expirationTime;
  // expiration_date datetime [not null]
  private double value;
  // value double [not null]
  private boolean forSale;
  // for_sale boolean [not null, default: False]
  private double sellPrice;
  // sell_price double

  public Contract() {
  }

  public Contract(int playerId, int owner, String event, int eventThreshold, LocalDate creationTime,
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

  public Contract(Integer id, int playerId, int owner, String event, int eventThreshold, LocalDate creationTime,
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

  public int getPlayerId() {
    return playerId;
  }

  public int getOwner() {
    return owner;
  }

  public String getEvent() {
    return event;
  }

  public int getEventThreshold() {
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