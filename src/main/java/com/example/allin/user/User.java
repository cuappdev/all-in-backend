package com.example.allin.user;

import com.example.allin.contract.Contract;
import com.example.allin.transaction.Transaction;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

  @Column(name = "username", nullable = false, unique = true)
  private String username;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "image", nullable = false)
  private String image = "src/main/resources/static/images/users/default.jpg";

  @Column(name = "balance", nullable = false)
  private Double balance = 1000.0;

  @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
  private List<Contract> contracts = new LinkedList<>();

  @OneToMany(mappedBy = "seller", fetch = FetchType.EAGER)
  private List<Transaction> sellerTransactions = new LinkedList<>();

  @OneToMany(mappedBy = "buyer", fetch = FetchType.EAGER)
  private List<Transaction> buyerTransactions = new LinkedList<>();

  public User() {
  }

  public User(String username, String email, String image, Double balance) {
    this.username = username;
    this.email = email;
    this.image = image;
    this.balance = balance;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @JsonIgnore
  public String getImage() {
    return image;
  }

  @JsonIgnore
  public void setImage(String image) {
    this.image = image;
  }

  public Double getBalance() {
    return balance;
  }

  public void setBalance(Double balance) {
    this.balance = balance;
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

  public List<Transaction> getSellerTransactions() {
    return sellerTransactions;
  }

  public void setSellerTransactions(List<Transaction> sellerTransactions) {
    this.sellerTransactions = sellerTransactions;
  }

  public void addSellerTransaction(Transaction transaction) {
    sellerTransactions.add(transaction);
  }

  public void removeSellerTransaction(Transaction transaction) {
    sellerTransactions.remove(transaction);
  }

  public List<Transaction> getBuyerTransactions() {
    return buyerTransactions;
  }

  public void setBuyerTransactions(List<Transaction> buyerTransactions) {
    this.buyerTransactions = buyerTransactions;
  }

  public void addBuyerTransaction(Transaction transaction) {
    buyerTransactions.add(transaction);
  }

  public void removeBuyerTransaction(Transaction transaction) {
    buyerTransactions.remove(transaction);
  }

  @Override
  public String toString() {
    return "User [id=" + id + ", username=" + username + ", email=" + email + ", image=" + image + ", balance="
        + balance + "]";
  }
}
