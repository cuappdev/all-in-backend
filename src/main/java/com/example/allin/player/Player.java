package com.example.allin.player;

import java.util.List;
import com.example.allin.playerData.PlayerData;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;

@Entity
@Table(name = "players")
public class Player{
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
  private byte[] image;

  @Column()
  private String bio = "";

  public Player() {
  }

  public Player(String firstName, String lastName, String position, Integer number, Integer height, Integer weight, Integer year, String hometown, String highSchool, byte[] image, String bio) {
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

  public byte[] getImage() {
    return image;
  }

  public void setImage(byte[] image) {
    this.image = image;
  }

  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  @Override
  public String toString() {
    return "Player [bio=" + bio + ", firstName=" + firstName + ", height=" + height + ", highSchool=" + highSchool
        + ", hometown=" + hometown + ", id=" + id + ", image=" + image + ", lastName=" + lastName + ", number=" + number
        + ", position=" + position + ", weight=" + weight + ", year=" + year + "]";
  }
}