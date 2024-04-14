package com.example.allin.user;

import com.example.allin.contract.Contract;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
  private Integer id;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String hashedPassword;

  // private String image;

  @Column(nullable = false)
  private Double balance;

  @Column
  private String sessionToken;

  @Column
  private String sessionExpiration;

  // @Column
  // private String refreshToken;

  @Column(nullable = false)
  private Boolean isAdmin;

  @OneToMany(mappedBy = "owner", cascade = jakarta.persistence.CascadeType.ALL)
  private List<Contract> contracts;

  public User() {
  }

  public User(String email, String hashedPassword, Double balance, String sessionToken, String sessionExpiration,
      Boolean isAdmin) {
    this.email = email;
    this.hashedPassword = hashedPassword;
    this.balance = balance;
    this.sessionToken = sessionToken;
    this.sessionExpiration = sessionExpiration;
    this.isAdmin = isAdmin;
    this.contracts = new java.util.LinkedList<>();
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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

  public Double getBalance() {
    return balance;
  }

  public void setBalance(Double balance) {
    this.balance = balance;
  }

  public String getSessionToken() {
    return sessionToken;
  }

  public void setSessionToken(String sessionToken) {
    this.sessionToken = sessionToken;
  }

  public String getSessionExpiration() {
    return sessionExpiration;
  }

  public void setSessionExpiration(String sessionExpiration) {
    this.sessionExpiration = sessionExpiration;
  }

  public Boolean getIsAdmin() {
    return isAdmin;
  }

  public void setIsAdmin(Boolean isAdmin) {
    this.isAdmin = isAdmin;
  }

  @Override
  public String toString() {
    return "User [balance=" + balance + ", email=" + email + ", hashedPassword=" + hashedPassword + ", id=" + id
        + ", isAdmin=" + isAdmin + ", sessionExpiration=" + sessionExpiration + ", sessionToken=" + sessionToken + "]";
  }
}
