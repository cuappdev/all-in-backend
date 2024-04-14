package com.example.allin.transaction;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

@Entity
public class Transaction {
  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
  private Integer id;

  @Column(nullable = false)
  private Integer sellerId;

  @Column(nullable = false)
  private Integer buyerId;

  @Column(nullable = false)
  private Integer contractId;

  @Column(nullable = false)
  private LocalDate transactionDate;

  @Column(nullable = false)
  private Integer price;

  public Transaction() {
  }

  public Transaction(Integer sellerId, Integer buyerId, Integer contractId, LocalDate transactionDate, Integer price) {
    this.sellerId = sellerId;
    this.buyerId = buyerId;
    this.contractId = contractId;
    this.transactionDate = transactionDate;
    this.price = price;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getSellerId() {
    return sellerId;
  }

  public void setSellerId(Integer sellerId) {
    this.sellerId = sellerId;
  }

  public Integer getBuyerId() {
    return buyerId;
  }

  public void setBuyerId(Integer buyerId) {
    this.buyerId = buyerId;
  }

  public Integer getContractId() {
    return contractId;
  }

  public void setContractId(Integer contractId) {
    this.contractId = contractId;
  }

  public LocalDate getTransactionDate() {
    return transactionDate;
  }

  public void setTransactionDate(LocalDate transactionDate) {
    this.transactionDate = transactionDate;
  }

  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  @Override
  public String toString() {
    return "Transaction{" +
      "id=" + id +
      ", sellerId=" + sellerId +
      ", buyerId=" + buyerId +
      ", contractId=" + contractId +
      ", transactionDate=" + transactionDate +
      ", price=" + price +
      '}';
  }
}
