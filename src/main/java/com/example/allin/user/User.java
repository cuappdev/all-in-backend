package com.example.allin.user;

import com.example.allin.contract.Contract;

import java.util.List;
import java.util.LinkedList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
  private Integer id;

  @Column(name = "firstname", nullable = false)
  private String firstname;

  @Column(name = "lastname", nullable = false)
  private String lastname;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "hashedPassword", nullable = false)
  private String hashedPassword;

  @Column(name = "image", nullable = true)
  private String image;

  @Column(name = "balance", nullable = false)
  private Double balance = 1000.0;

  @Column(name = "isAdmin", nullable = false)
  private Boolean isAdmin;

  @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
  private List<Contract> contracts = new LinkedList<>();

  public User() {
  }

  public User(String firstname, String lastname, String email, String hashedPassword, String image, Double balance,
      Boolean isAdmin) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.hashedPassword = hashedPassword;
    this.image = image;
    this.balance = balance;
    this.isAdmin = isAdmin;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getHashedPassword() {
    return hashedPassword;
  }

  public void setHashedPassword(String hashedPassword) {
    this.hashedPassword = hashedPassword;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public Double getBalance() {
    return balance;
  }

  public void setBalance(Double balance) {
    this.balance = balance;
  }

  public Boolean getIsAdmin() {
    return isAdmin;
  }

  public void setIsAdmin(Boolean isAdmin) {
    this.isAdmin = isAdmin;
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

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", firstname='" + firstname + '\'' +
        ", lastname='" + lastname + '\'' +
        ", email='" + email + '\'' +
        ", hashedPassword='" + hashedPassword + '\'' +
        ", image='" + image + '\'' +
        ", balance=" + balance +
        ", isAdmin=" + isAdmin +
        '}';
  }
}
