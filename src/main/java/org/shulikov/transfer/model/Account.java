package org.shulikov.transfer.model;

public class Account {

  private Long id;
  private String holderName;
  private int balance;


  public Account() {
  }

  public Account(String holderName, int balance) {
    this.holderName = holderName;
    this.balance = balance;
  }

  public Account(Long id, String holderName, int balance) {
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

  public int getBalance() {
    return balance;
  }

  public void setBalance(int balance) {
    this.balance = balance;
  }
}
