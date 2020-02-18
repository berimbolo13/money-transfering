package org.shulikov.transfer.unit.account.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Test;
import org.shulikov.transfer.model.Account;

public class AccountRepositoryTest extends AccountRepositoryTestBase {

  @Test
  public void createAccount_check_idIncrementation() {
    Account first = accountRepository.create(new Account("First", new AtomicInteger()));
    Account second = accountRepository.create(new Account("Second", new AtomicInteger()));
    Account third = accountRepository.create(new Account("Third", new AtomicInteger()));

    assertThat(second.getId()).isEqualTo(first.getId() + 1);
    assertThat(third.getId()).isEqualTo(second.getId() + 1);
  }

  @Test
  public void getAccount_byId_whichIsExisted_returnsOptionalAccount() {
    Account account = accountRepository.create(new Account("holderName", new AtomicInteger()));
    Optional<Account> byId = accountRepository.findById(account.getId());

    assertThat(byId).isPresent();
    assertThat(byId.get().getId()).isEqualTo(account.getId());
  }

  @Test
  public void getAccount_byId_whichIsNotExisted_returnsEmptyOptional() {
    Optional<Account> byId = accountRepository.findById(5L);
    assertThat(byId).isEmpty();
  }

  @Test
  public void getAllAccounts_returnsAccountList() {
    accountRepository.create(new Account("holderName", new AtomicInteger()));
    Iterable<Account> all = accountRepository.findAll();

    assertThat(all).size().isGreaterThan(0);
  }

  @Test
  public void deleteAccount_whichIsExisted() {
    Account account = accountRepository.create(new Account("holderName", new AtomicInteger()));
    accountRepository.delete(account.getId());

    Optional<Account> byId = accountRepository.findById(account.getId());
    assertThat(byId).isEmpty();
  }

  @Test
  public void transferMoney_fromTo_balancesAreChanged() {
    Account from = accountRepository.create(new Account("from", new AtomicInteger(10)));
    Account to = accountRepository.create(new Account("to", new AtomicInteger()));

    accountRepository.transferMoney(from.getId(), to.getId(), 10);

    assertThat(from.getBalance().get()).isEqualTo(0);
    assertThat(to.getBalance().get()).isEqualTo(10);
  }
}
