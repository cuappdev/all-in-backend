package com.appdev.allin.user;

import com.appdev.allin.contract.Contract;
import com.appdev.allin.transaction.Transaction;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {
  @Id
  @Column(name = "uid", nullable = false, unique = true)
  private String uid;

  // TODO: Add unique = true when users can choose their own usernames
  @Column(name = "username", nullable = false)
  private String username;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "image", nullable = false)
  private String image;

  // Integers are used to represent money in cents, prevents floating point errors
  @Column(name = "balance", nullable = false)
  private Integer balance = 100000; // $1000.00 default

  @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
  private List<Contract> contracts = new LinkedList<>();

  @OneToMany(mappedBy = "seller", fetch = FetchType.EAGER)
  private List<Transaction> sellerTransactions = new LinkedList<>();

  @OneToMany(mappedBy = "buyer", fetch = FetchType.EAGER)
  private List<Transaction> buyerTransactions = new LinkedList<>();

  public User() {
  }

  // Use this constructor when creating a new user
  public User(String uid, String username, String email, String image) {
    this.uid = uid;
    this.username = username;
    this.email = email;
    this.image = image;
  }

  public User(String uid, String username, String email, String image, Integer balance,
      List<Contract> contracts, List<Transaction> sellerTransactions, List<Transaction> buyerTransactions) {
    this.uid = uid;
    this.username = username;
    this.email = email;
    this.image = image;
    this.balance = balance;
    this.contracts = contracts;
    this.sellerTransactions = sellerTransactions;
    this.buyerTransactions = buyerTransactions;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
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

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public Integer getBalance() {
    return balance;
  }

  public void setBalance(Integer balance) {
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
    return "User{" +
        "uid='" + uid + '\'' +
        ", username='" + username + '\'' +
        ", email='" + email + '\'' +
        ", image='" + image + '\'' +
        ", balance=" + balance +
        ", contracts=" + contracts +
        ", sellerTransactions=" + sellerTransactions +
        ", buyerTransactions=" + buyerTransactions +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof User user))
      return false;

    if (!getUid().equals(user.getUid()))
      return false;
    if (!getUsername().equals(user.getUsername()))
      return false;
    if (!getEmail().equals(user.getEmail()))
      return false;
    if (!getImage().equals(user.getImage()))
      return false;
    if (!getBalance().equals(user.getBalance()))
      return false;
    if (!getContracts().equals(user.getContracts()))
      return false;
    if (!getSellerTransactions().equals(user.getSellerTransactions()))
      return false;
    return getBuyerTransactions().equals(user.getBuyerTransactions());
  }
}
