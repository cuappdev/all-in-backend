package com.example.allin.player;

import java.util.List;

import com.example.allin.playerData.PlayerData;
import com.example.allin.contract.Contract;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;

@Entity
@Table(name = "players")
public class Player {
  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
  private Integer id;

  @Column()
  private String firstName;

  @Column()
  private String lastName;

  @Column()
  private String position;

  @Column()
  private Integer number;

  @Column()
  private Integer height;

  @Column()
  private Integer weight;

  @Column()
  private Integer year;

  @Column()
  private String hometown;

  @Column()
  private String highSchool;

  @Column()
  private String image = "src/main/resources/static/images/players/default.jpg";

  @Column()
  private String bio = "";

  @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
  private List<Contract> contracts;

  @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
  private List<PlayerData> playerData;

  public Player() {
  }

  public Player(String firstName, String lastName, String position, Integer number, Integer height, Integer weight,
      Integer year, String hometown, String highSchool, String image, String bio) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.position = position;
    this.number = number;
    this.height = height;
    this.weight = weight;
    this.year = year;
    this.hometown = hometown;
    this.highSchool = highSchool;
    this.image = image;
    this.bio = bio;
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

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public Integer getNumber() {
    return number;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }

  public Integer getHeight() {
    return height;
  }

  public void setHeight(Integer height) {
    this.height = height;
  }

  public Integer getWeight() {
    return weight;
  }

  public void setWeight(Integer weight) {
    this.weight = weight;
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
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

  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  public List<Contract> getContracts() {
    return contracts;
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

  public void addContract(Contract contract) {
    contracts.add(contract);
  }

  public void removeContract(Contract contract) {
    contracts.remove(contract);
  }

  @Override
  public String toString() {
    return "Player [bio=" + bio + ", firstName=" + firstName + ", height=" + height + ", highSchool=" + highSchool
        + ", hometown=" + hometown + ", id=" + id + ", image=" + image + ", lastName=" + lastName + ", number=" + number
        + ", position=" + position + ", weight=" + weight + ", year=" + year + "]";
  }
}