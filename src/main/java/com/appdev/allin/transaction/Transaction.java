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
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "transactions")
public class Transaction {
  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "seller_id")
  @OnDelete(action = OnDeleteAction.NO_ACTION)
  private User seller;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "buyer_id", nullable = false)
  @OnDelete(action = OnDeleteAction.NO_ACTION)
  private User buyer;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "contract_id", nullable = false)
  @OnDelete(action = OnDeleteAction.NO_ACTION)
  private Contract contract;

  @Column(name = "transactionDate", nullable = false)
  private LocalDate transactionDate = LocalDate.now();

  @Column(name = "price", nullable = false)
  private Double price;

  public Transaction() {}

  public Transaction(
      User seller, User buyer, Contract contract, LocalDate transactionDate, Double price) {
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

  @JsonIgnore
  public User getSeller() {
    return seller;
  }

  public Integer getSellerId() {
    if (seller == null) {
      return null;
    }
    return seller.getId();
  }

  @JsonIgnore
  public void setSeller(User seller) {
    this.seller = seller;
  }

  @JsonIgnore
  public User getBuyer() {
    return buyer;
  }

  public Integer getBuyerId() {
    if (buyer == null) {
      return null;
    }
    return buyer.getId();
  }

  @JsonIgnore
  public void setBuyer(User buyer) {
    this.buyer = buyer;
  }

  @JsonIgnore
  public Contract getContract() {
    return contract;
  }

  public Integer getContractId() {
    if (contract == null) {
      return null;
    }
    return contract.getId();
  }

  @JsonIgnore
  public void setContract(Contract contract) {
    this.contract = contract;
  }

  public LocalDate getTransactionDate() {
    return transactionDate;
  }

  public void setTransactionDate(LocalDate transactionDate) {
    this.transactionDate = transactionDate;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  @Override
  public String toString() {
    return "Transaction [id="
        + id
        + ", seller="
        + seller
        + ", buyer="
        + buyer
        + ", contract="
        + contract
        + ", transactionDate="
        + transactionDate
        + ", price="
        + price
        + "]";
  }
}
