package com.example.allin.contract;

import com.example.allin.user.User;
import com.example.allin.transaction.Transaction;
import com.example.allin.player.Player;

import java.time.LocalDate;
import java.util.List;
import java.util.LinkedList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "contracts")
public class Contract {
  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "player_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Player player;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "owner_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private User owner;

  @Column(name = "buyPrice", nullable = false)
  private Double buyPrice;

  @Column(name = "rarity", nullable = false)
  private Rarity rarity;

  @Column(name = "opposingTeam", nullable = false)
  private OpposingTeam opposingTeam;

  @Column(name = "opposingTeamImage", nullable = false)
  private String opposingTeamImage = "/src/main/resources/static/images/teams/default.jpg";

  @Column(name = "event", nullable = false)
  private Event event;

  @Column(name = "eventThreshold", nullable = false)
  private Integer eventThreshold;

  @Column(name = "creationTime", nullable = false)
  private LocalDate creationTime = LocalDate.now();

  @Column(name = "value", nullable = false)
  private Double value;

  @Column(name = "expired")
  private Boolean expired;

  @Column(name = "forSale", nullable = false)
  private Boolean forSale = false;

  @Column(name = "sellPrice")
  private Double sellPrice;

  @OneToMany(mappedBy = "contract", fetch = FetchType.EAGER)
  private List<Transaction> transactions = new LinkedList<>();

  public Contract() {
  }

  public Contract(Player player, User owner, Double buyPrice, Rarity rarity, OpposingTeam opposingTeam,
      String opposingTeamImage,
      Event event,
      Integer eventThreshold, LocalDate creationTime, Double value, Boolean expired, Boolean forSale,
      Double sellPrice) {
    this.player = player;
    this.owner = owner;
    this.buyPrice = buyPrice;
    this.rarity = rarity;
    this.opposingTeam = opposingTeam;
    this.opposingTeamImage = opposingTeamImage;
    this.event = event;
    this.eventThreshold = eventThreshold;
    this.creationTime = creationTime;
    this.value = value;
    this.expired = expired;
    this.forSale = forSale;
    this.sellPrice = sellPrice;
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
    if (player == null) {
      return null;
    }
    return player.getId();
  }

  @JsonIgnore
  public void setPlayer(Player player) {
    this.player = player;
  }

  @JsonIgnore
  public User getOwner() {
    return owner;
  }

  public Integer getOwnerId() {
    if (owner == null) {
      return null;
    }
    return owner.getId();
  }

  @JsonIgnore
  public void setOwner(User owner) {
    this.owner = owner;
  }

  public Double getBuyPrice() {
    return buyPrice;
  }

  public void setBuyPrice(Double buyPrice) {
    this.buyPrice = buyPrice;
  }

  public Rarity getRarity() {
    return rarity;
  }

  public void setRarity(Rarity rarity) {
    this.rarity = rarity;
  }

  public OpposingTeam getOpposingTeam() {
    return opposingTeam;
  }

  public void setOpposingTeam(OpposingTeam opposingTeam) {
    this.opposingTeam = opposingTeam;
  }

  @JsonIgnore
  public String getOpposingTeamImage() {
    return opposingTeamImage;
  }

  @JsonIgnore
  public void setOpposingTeamImage(String opposingTeamImage) {
    this.opposingTeamImage = opposingTeamImage;
  }

  public Event getEvent() {
    return event;
  }

  public void setEvent(Event event) {
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

  public Boolean getExpired() {
    return expired;
  }

  public void setExpired(Boolean expired) {
    this.expired = expired;
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
    return "Contract [buyPrice=" + buyPrice + ", creationTime=" + creationTime + ", event=" + event
        + ", eventThreshold=" + eventThreshold + ", expired=" + expired + ", forSale=" + forSale + ", id=" + id
        + ", opposingTeam=" + opposingTeam + ", opposingTeamImage=" + opposingTeamImage + ", owner=" + owner
        + ", player=" + player + ", rarity=" + rarity + ", sellPrice=" + sellPrice + ", value=" + value + "]";
  }

}