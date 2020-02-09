package org.shulikov.transfer.service.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.shulikov.transfer.model.Transaction;
import org.shulikov.transfer.repository.api.AccountRepository;
import org.shulikov.transfer.service.api.TransactionService;
import org.shulikov.transfer.validator.api.TransactionValidator;

@Singleton
public class TransactionServiceImpl implements TransactionService {

  private AccountRepository accountRepository;

  private TransactionValidator transactionValidator;

  @Inject
  public TransactionServiceImpl(
      AccountRepository accountRepository,
      TransactionValidator transactionValidator) {

    this.accountRepository = accountRepository;
    this.transactionValidator = transactionValidator;
  }

  @Override
  public void perform(Transaction transaction) {
    transactionValidator.validateTransaction(transaction);
    accountRepository
        .transferMoney(transaction.getFrom(), transaction.getTo(), transaction.getAmount());
  }
}
