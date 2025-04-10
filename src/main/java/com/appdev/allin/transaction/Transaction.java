package com.appdev.allin.transaction;

import com.appdev.allin.contract.Contract;
import com.appdev.allin.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "transaction")
public class Transaction {
  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "seller_id") // If seller is null, then this is the first transaction with the contract (user
                                  // bought the contract from us)
  @OnDelete(action = OnDeleteAction.NO_ACTION)
  private User seller;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "buyer_id") // If the buyer is null, then this is the last transaction with the contract
                                 // (contract has matured and has been paid out if hit)
  @OnDelete(action = OnDeleteAction.NO_ACTION)
  private User buyer;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "contract_id", nullable = false)
  @OnDelete(action = OnDeleteAction.NO_ACTION)
  private Contract contract;

  @Column(name = "transactionDate", nullable = false)
  private LocalDateTime transactionDate = LocalDateTime.now();

  @Column(name = "price", nullable = false)
  private Integer price;

  public Transaction() {
  }

  public Transaction(
      User seller, User buyer, Contract contract, LocalDateTime transactionDate, Integer price) {
    this.seller = seller;
    this.buyer = buyer;
    this.contract = contract;
    this.transactionDate = transactionDate;
    this.price = price;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public User getSeller() {
    return seller;
  }

  public String getSellerUid() {
    if (seller == null) {
      return null;
    }
    return seller.getUid();
  }

  public void setSeller(User seller) {
    this.seller = seller;
  }

  public User getBuyer() {
    return buyer;
  }

  public String getBuyerUid() {
    if (buyer == null) {
      return null;
    }
    return buyer.getUid();
  }

  public void setBuyer(User buyer) {
    this.buyer = buyer;
  }

  public Contract getContract() {
    return contract;
  }

  public Integer getContractId() {
    if (contract == null) {
      return null;
    }
    return contract.getId();
  }

  public void setContract(Contract contract) {
    this.contract = contract;
  }

  public LocalDateTime getTransactionDate() {
    return transactionDate;
  }

  public void setTransactionDate(LocalDateTime transactionDate) {
    this.transactionDate = transactionDate;
  }

  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  // @Override
  // public String toString() {
  // return "User{" +
  // "uid='" + uid + '\'' +
  // ", username='" + username + '\'' +
  // ", email='" + email + '\'' +
  // ", image='" + image + '\'' +
  // ", balance=" + balance +
  // ", createdAt=" + createdAt +
  // ", contracts=" + contracts +
  // ", sellerTransactions=" + sellerTransactions +
  // ", buyerTransactions=" + buyerTransactions +
  // '}';
  // }

  // @Override
  // public String toString() {
  // return "Transaction{" +
  // "id='" + id + '\'' +
  // ", seller='" + seller + '\'' +

  // }
}
