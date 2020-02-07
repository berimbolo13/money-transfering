package org.shulikov.transfer.validator.api;


public interface BalanceValidator {

  void validateForTransaction(int balance, int amount);
}
