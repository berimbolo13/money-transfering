package org.shulikov.transfer.service.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.shulikov.transfer.model.Transaction;
import org.shulikov.transfer.repository.api.AccountRepository;
import org.shulikov.transfer.service.api.TransactionService;

@Singleton
public class TransactionServiceImpl implements TransactionService {

  private AccountRepository accountRepository;

  @Inject
  public TransactionServiceImpl(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public void perform(Transaction transaction) {
    accountRepository
        .transferMoney(transaction.getFrom(), transaction.getTo(), transaction.getAmount());
  }
}
