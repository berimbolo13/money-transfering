package org.shulikov.transfer.repository.impl;

import static java.util.Optional.ofNullable;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.shulikov.transfer.model.Account;
import org.shulikov.transfer.repository.api.AccountRepository;
import org.shulikov.transfer.validator.api.AccountValidator;

@Singleton
public class AccountRepositoryImpl implements AccountRepository {

  private Map<Long, Account> accounts = new ConcurrentHashMap<>() {{
    put(1L, new Account(1L, "First Holder", 76));
    put(2L, new Account(2L, "Second Holder", 76));
    put(3L, new Account(3L, "Third Holder", 76));
  }};

  private AtomicLong lastId = new AtomicLong(accounts.size());

  private AccountValidator accountValidator;

  @Inject
  public AccountRepositoryImpl(AccountValidator accountValidator) {
    this.accountValidator = accountValidator;
  }

  public Optional<Account> findById(Long id) {
    return ofNullable(accounts.get(id));
  }

  public Iterable<Account> findAll() {
    return accounts.values();
  }

  public Account create(Account account) {
    account.setId(lastId.incrementAndGet());
    accounts.put(account.getId(), account);
    return account;
  }

  public Account delete(Long id) {
    return accounts.remove(id);
  }

  public void transferMoney(Long fromId, Long toId, int amount) {
    accounts.compute(fromId, (key, value) -> {
      accountValidator.validateForWithdraw(key, value, amount);
      depositMoney(toId, amount);
      value.setBalance(value.getBalance() - amount);
      return value;
    });
  }

  private void depositMoney(Long toId, int amount) {
    accounts.compute(toId, (key, value) -> {
      accountValidator.validateForDeposit(key, value);
      value.setBalance(value.getBalance() + amount);
      return value;
    });
  }
}
