package org.shulikov.transfer.model;

public class Transaction {

  private Long from;
  private Long to;
  private int amount;

  public Transaction() {
  }

  public Transaction(Long from, Long to, int amount) {
    this.from = from;
    this.to = to;
    this.amount = amount;
  }

  public Long getFrom() {
    return from;
  }

  public void setFrom(Long from) {
    this.from = from;
  }

  public Long getTo() {
    return to;
  }

  public void setTo(Long to) {
    this.to = to;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }
}
