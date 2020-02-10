package org.shulikov.transfer.service.impl;

import static java.util.Optional.ofNullable;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.shulikov.transfer.exception.ResourceNotFoundException;
import org.shulikov.transfer.model.Account;
import org.shulikov.transfer.repository.api.AccountRepository;
import org.shulikov.transfer.service.api.AccountService;

@Singleton
public class AccountServiceImpl implements AccountService {

  private AccountRepository accountRepository;

  @Inject
  public AccountServiceImpl(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public Iterable<Account> findAll() {
    return accountRepository.findAll();
  }

  @Override
  public Account getById(Long id) {
    return accountRepository.findById(id).orElseThrow(
        () -> new ResourceNotFoundException("Account with id " + id + " was not found"));
  }

  @Override
  public Account create(String holderName) {
    return accountRepository.create(new Account(holderName, 100));
  }

  @Override
  public Account update(Long id, String holderName) {
    Account account = getById(id);
    account.setHolderName(holderName);
    return account;
  }

  @Override
  public void delete(Long id) {
    ofNullable(accountRepository.delete(id)).orElseThrow(
        () -> new ResourceNotFoundException("Account with id " + id + " was not found"));
  }
}
