package org.shulikov.transfer.validator.api;


import org.shulikov.transfer.model.Account;

public interface AccountValidator {

  void validateForWithdraw(Long id, Account account, int amount);

  void validateForDeposit(Long id, Account account);
}
