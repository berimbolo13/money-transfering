package org.shulikov.transfer.model;

import java.util.concurrent.atomic.AtomicInteger;

public class Account {

  private Long id;
  private String holderName;
  private AtomicInteger balance;


  public Account() {
  }

  public Account(String holderName, AtomicInteger balance) {
    this.holderName = holderName;
    this.balance = balance;
  }

  public Account(Long id, String holderName, AtomicInteger balance) {
    this.id = id;
    this.holderName = holderName;
    this.balance = balance;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getHolderName() {
    return holderName;
  }

  public void setHolderName(String holderName) {
    this.holderName = holderName;
  }

  public AtomicInteger getBalance() {
    return balance;
  }

  public void setBalance(AtomicInteger balance) {
    this.balance = balance;
  }
}
