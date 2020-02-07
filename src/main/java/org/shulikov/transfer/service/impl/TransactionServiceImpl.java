package org.shulikov.transfer.service.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.shulikov.transfer.model.Account;
import org.shulikov.transfer.model.Transaction;
import org.shulikov.transfer.service.api.AccountService;
import org.shulikov.transfer.service.api.TransactionService;
import org.shulikov.transfer.validator.api.BalanceValidator;

@Singleton
public class TransactionServiceImpl implements TransactionService {

  private AccountService accountService;

  private BalanceValidator balanceValidator;

  @Inject
  public TransactionServiceImpl(AccountService accountService, BalanceValidator balanceValidator) {
    this.accountService = accountService;
    this.balanceValidator = balanceValidator;
  }

  @Override
  public void perform(Transaction transaction) {

    Account from = accountService.getById(transaction.getFrom());
    Account to = accountService.getById(transaction.getTo());
    int amount = transaction.getAmount();

    balanceValidator.validateForTransaction(from.getBalance(), amount);
    from.setBalance(from.getBalance() - amount);
    to.setBalance(to.getBalance() + amount);
  }
}
