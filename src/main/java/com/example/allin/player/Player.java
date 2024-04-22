package com.example.allin.player;

import java.util.List;
import java.util.LinkedList;

import com.example.allin.playerData.PlayerData;
import com.example.allin.contract.Contract;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "players")
public class Player {
  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
  private Integer id;

  @Column(name = "firstName", nullable = false)
  private String firstName;

  @Column(name = "lastName", nullable = false)
  private String lastName;

  @Column(name = "position", nullable = false)
  private Position[] position;

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

  @Column(name = "image", nullable = false)
  private String image = "src/main/resources/static/images/players/default.jpg";

  @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
  private List<Contract> contracts = new LinkedList<>();

  @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
  private List<PlayerData> playerData = new LinkedList<>();

  public Player() {
  }

  public Player(String firstName, String lastName, Position[] position, Integer number, String height, Integer weight,
      String hometown, String highSchool) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.position = position;
    this.number = number;
    this.height = height;
    this.weight = weight;
    this.hometown = hometown;
    this.highSchool = highSchool;
  }

  public Player(String firstName, String lastName, Position[] position, Integer number, String height, Integer weight,
      String hometown, String highSchool, String image) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.position = position;
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

  public Position[] getPosition() {
    return position;
  }

  public void setPosition(Position[] position) {
    this.position = position;
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

  @JsonIgnore
  public String getImage() {
    return image;
  }

  @JsonIgnore
  public void setImage(String image) {
    this.image = image;
  }

  public List<Contract> getContracts() {
    return contracts;
  }

  public void addContract(Contract contract) {
    contracts.add(contract);
  }

  public void removeContract(Contract contract) {
    contracts.remove(contract);
  }

  public void setContracts(List<Contract> contracts) {
    this.contracts = contracts;
  }

  public List<PlayerData> getPlayerData() {
    return playerData;
  }

  public void addPlayerData(PlayerData playerData) {
    this.playerData.add(playerData);
  }

  public void removePlayerData(PlayerData playerData) {
    this.playerData.remove(playerData);
  }

  public void setPlayerData(List<PlayerData> playerData) {
    this.playerData = playerData;
  }

  @Override
  public String toString() {
    return "Player [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", position=" + position
        + ", number=" + number + ", height=" + height + ", weight=" + weight + ", hometown=" + hometown
        + ", highSchool="
        + highSchool + ", image=" + image + "]";
  }
}