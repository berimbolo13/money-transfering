package org.shulikov.transfer.validator.impl;

import org.shulikov.transfer.exception.BadRequestException;
import org.shulikov.transfer.exception.ResourceNotFoundException;
import org.shulikov.transfer.model.Account;
import org.shulikov.transfer.validator.api.AccountValidator;

public class AccountValidatorImpl implements AccountValidator {

  public void validateForWithdraw(Long id, Account account, int amount) {
    checkIfAccountExists(id, account);
    checkIfEnoughOnBalance(account.getBalance(), amount);
  }

  public void validateForDeposit(Long id, Account account) {
    checkIfAccountExists(id, account);
  }

  private void checkIfAccountExists(Long id, Account account) {
    if (account == null) {
      throw new ResourceNotFoundException("Account with id " + id + " was not found");
    }
  }

  private void checkIfEnoughOnBalance(int balance, int amount) {
    if (balance < amount) {
      throw new BadRequestException("Not enough money on the balance");
    }
  }
}
