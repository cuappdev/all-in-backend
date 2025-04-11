package com.appdev.allin.player;

import com.appdev.allin.contract.Contract;
import com.appdev.allin.playerData.PlayerData;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "player")
public class Player {
  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
  private Integer id;

  @Column(name = "firstName", nullable = false)
  private String firstName;

  @Column(name = "lastName", nullable = false)
  private String lastName;

  @Column(name = "positions", nullable = false)
  private Position[] positions;

  @Column(name = "number", nullable = false)
  private Integer number;

  @Column(name = "height", nullable = false)
  private String height;

  @Column(name = "weight", nullable = false)
  private Integer weight;

  @Column(name = "hometown", nullable = false)
  private String hometown;

  @Column(name = "highSchool", nullable = false)
  private String highSchool;

  // TODO: Remove default value, should be populated when scraping
  @Column(name = "image", nullable = false)
  private String image = "src/main/resources/static/images/players/default.jpg";

  @OneToMany(mappedBy = "player", fetch = FetchType.LAZY)
  private List<Contract> contracts = new LinkedList<>();

  @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
  private List<PlayerData> playerData = new LinkedList<>();

  public Player() {
  }

  public Player(
      String firstName,
      String lastName,
      Position[] positions,
      Integer number,
      String height,
      Integer weight,
      String hometown,
      String highSchool,
      String image) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.positions = positions;
    this.number = number;
    this.height = height;
    this.weight = weight;
    this.hometown = hometown;
    this.highSchool = highSchool;
    this.image = image;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Position[] getPositions() {
    return positions;
  }

  public void setPositions(Position[] positions) {
    this.positions = positions;
  }

  public Integer getNumber() {
    return number;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }

  public String getHeight() {
    return height;
  }

  public void setHeight(String height) {
    this.height = height;
  }

  public Integer getWeight() {
    return weight;
  }

  public void setWeight(Integer weight) {
    this.weight = weight;
  }

  public String getHometown() {
    return hometown;
  }

  public void setHometown(String hometown) {
    this.hometown = hometown;
  }

  public String getHighSchool() {
    return highSchool;
  }

  public void setHighSchool(String highSchool) {
    this.highSchool = highSchool;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public List<Contract> getContracts() {
    return contracts;
  }

  public void setContracts(List<Contract> contracts) {
    this.contracts = contracts;
  }

  public void addContract(Contract contract) {
    contracts.add(contract);
  }

  public void removeContract(Contract contract) {
    contracts.remove(contract);
  }

  public List<PlayerData> getPlayerData() {
    return playerData;
  }

  public void setPlayerData(List<PlayerData> playerData) {
    this.playerData = playerData;
  }

  public void addPlayerData(PlayerData playerData) {
    this.playerData.add(playerData);
  }

  public void removePlayerData(PlayerData playerData) {
    this.playerData.remove(playerData);
  }

  @Override
  public String toString() {
    return "Player{" +
        "id='" + id + '\'' +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", positions='" + positions + '\'' +
        ", number=" + number +
        ", height='" + height + '\'' +
        ", weight=" + weight +
        ", hometown='" + hometown + '\'' +
        ", highSchool='" + highSchool + '\'' +
        ", image='" + image + '\'' +
        ", contracts=" + contracts +
        ", playerData=" + playerData +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof Player player))
      return false;

    if (!getId().equals(player.getId()))
      return false;
    if (!getFirstName().equals(player.getFirstName()))
      return false;
    if (!getLastName().equals(player.getLastName()))
      return false;
    if (!getPositions().equals(player.getPositions()))
      return false;
    if (!getNumber().equals(player.getNumber()))
      return false;
    if (!getHeight().equals(player.getHeight()))
      return false;
    if (!getWeight().equals(player.getWeight()))
      return false;
    if (!getHometown().equals(player.getHometown()))
      return false;
    if (!getHighSchool().equals(player.getHighSchool()))
      return false;
    if (!getImage().equals(player.getImage()))
      return false;
    if (!getContracts().equals(player.getContracts()))
      return false;
    return !getPlayerData().equals(player.getPlayerData());
  }
}
