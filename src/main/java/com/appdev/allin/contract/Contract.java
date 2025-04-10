package com.appdev.allin.contract;

import com.appdev.allin.player.Player;
import com.appdev.allin.transaction.Transaction;
import com.appdev.allin.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
  private Integer buyPrice;

  @Column(name = "rarity", nullable = false)
  private Rarity rarity;

  @Column(name = "opposingTeam", nullable = false)
  private OpposingTeam opposingTeam;

  @Column(name = "opposingTeamImage", nullable = false)
  private String opposingTeamImage = "src/main/resources/static/images/teams/default.jpg";

  @Column(name = "event", nullable = false)
  private Event event;

  @Column(name = "eventThreshold", nullable = false)
  private Integer eventThreshold;

  @Column(name = "creationTime", nullable = false)
  private LocalDateTime creationTime = LocalDateTime.now();

  @Column(name = "value", nullable = false)
  private Integer value;

  @Column(name = "expirationTime", nullable = false)
  private LocalDateTime expirationTime;

  @Column(name = "expired")
  private Boolean expired;

  @Column(name = "forSale", nullable = false)
  private Boolean forSale = false;

  @Column(name = "sellPrice")
  private Integer sellPrice;

  @OneToMany(mappedBy = "contract", fetch = FetchType.EAGER)
  private List<Transaction> transactions = new LinkedList<>();

  public Contract() {
  }

  public Contract(
      Player player,
      User owner,
      Integer buyPrice,
      Rarity rarity,
      OpposingTeam opposingTeam,
      String opposingTeamImage,
      Event event,
      Integer eventThreshold,
      LocalDateTime creationTime,
      Integer value,
      LocalDateTime expirationTime,
      Boolean expired,
      Integer sellPrice) {
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
    this.expirationTime = expirationTime; // nullable, allows passing null
    this.expired = expired;
    this.forSale = false; // default value not passed in instantiation
    this.sellPrice = sellPrice; // nullable, allows passing null
  }

  public Contract(
      Player player,
      User owner,
      Integer buyPrice,
      Rarity rarity,
      OpposingTeam opposingTeam,
      String opposingTeamImage,
      Event event,
      Integer eventThreshold,
      LocalDateTime creationTime,
      Integer value,
      Boolean expired,
      Boolean forSale,
      Integer sellPrice) {
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

  public Contract(
      Player player,
      User owner,
      Integer value,
      LocalDateTime expirationTime,
      Boolean expired) {
    this.player = player;
    this.owner = owner;
    this.value = value;
    this.expirationTime = expirationTime;
    this.expired = expired;
    this.creationTime = LocalDateTime.now();
    this.buyPrice = 0;
    this.rarity = Rarity.COMMON;
    this.forSale = false;
    this.sellPrice = null;
    this.event = null;
    this.eventThreshold = 0;
    this.opposingTeam = null;
    this.opposingTeamImage = "default.jpg";
  }

  public Contract(
      Player player,
      User owner,
      Integer buyPrice,
      Rarity rarity,
      OpposingTeam opposingTeam,
      String opposingTeamImage,
      Event event,
      Integer eventThreshold,
      LocalDateTime creationTime,
      Integer value,
      LocalDateTime expirationTime,
      Boolean expired,
      Boolean forSale,
      Integer sellPrice) {
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
    this.expirationTime = expirationTime;
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

  public String getOwnerUid() {
    if (owner == null) {
      return null;
    }
    return owner.getUid();
  }

  @JsonIgnore
  public void setOwner(User owner) {
    this.owner = owner;
  }

  public Integer getBuyPrice() {
    return buyPrice;
  }

  public void setBuyPrice(Integer buyPrice) {
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

  public LocalDateTime getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(LocalDateTime creationTime) {
    this.creationTime = creationTime;
  }

  public Integer getValue() {
    return value;
  }

  public void setValue(Integer value) {
    this.value = value;
  }

  public LocalDateTime getExpirationTime() {
    return expirationTime;
  }

  public void setExpirationTime(LocalDateTime expirationTime) {
    this.expirationTime = expirationTime;
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

  public Integer getSellPrice() {
    return sellPrice;
  }

  public void setSellPrice(Integer sellPrice) {
    this.sellPrice = sellPrice;
  }

  @Override
  public String toString() {
    return "Contract [buyPrice="
        + buyPrice
        + ", creationTime="
        + creationTime
        + ", event="
        + event
        + ", eventThreshold="
        + eventThreshold
        + ", expirationTime="
        + expirationTime
        + ", expired="
        + expired
        + ", forSale="
        + forSale
        + ", id="
        + id
        + ", opposingTeam="
        + opposingTeam
        + ", opposingTeamImage="
        + opposingTeamImage
        + ", owner="
        + owner
        + ", player="
        + player
        + ", rarity="
        + rarity
        + ", sellPrice="
        + sellPrice
        + ", value="
        + value
        + "]";
  }
}
