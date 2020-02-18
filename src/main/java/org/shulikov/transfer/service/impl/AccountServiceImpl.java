package org.shulikov.transfer.service.impl;

import static java.util.Optional.ofNullable;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.shulikov.transfer.exception.ResourceNotFoundException;
import org.shulikov.transfer.model.Account;
import org.shulikov.transfer.repository.api.AccountRepository;
import org.shulikov.transfer.service.api.AccountService;

@Singleton
@Slf4j
public class AccountServiceImpl implements AccountService {

  private AccountRepository accountRepository;

  @Inject
  public AccountServiceImpl(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public Iterable<Account> findAll() {
    log.info("All accounts fetching...");
    return accountRepository.findAll();
  }

  @Override
  public Account getById(Long id) {
    log.info("Trying to fetch account with id {}", id);
    return accountRepository.findById(id).orElseThrow(
        () -> new ResourceNotFoundException("Account with id " + id + " was not found"));
  }

  @Override
  public Account create(String holderName) {
    Account account = accountRepository.create(new Account(holderName, new AtomicInteger(100)));
    log.info("Account with id {} was created", account.getId());
    return account;
  }

  @Override
  public Account update(Long id, String holderName) {
    Account account = getById(id);
    account.setHolderName(holderName);
    log.info("Account with id {} was updated", account.getId());
    return account;
  }

  @Override
  public void delete(Long id) {
    ofNullable(accountRepository.delete(id)).orElseThrow(
        () -> new ResourceNotFoundException("Account with id " + id + " was not found"));
    log.info("Account with id {} was deleted", id);
  }
}
