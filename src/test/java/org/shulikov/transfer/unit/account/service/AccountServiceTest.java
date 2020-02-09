package org.shulikov.transfer.unit.account.service;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.Test;
import org.shulikov.transfer.exception.ResourceNotFoundException;
import org.shulikov.transfer.model.Account;

public class AccountServiceTest extends AccountServiceTestBase {

  @Test
  public void findAll_accounts_returnsSingleAccount() {
    Account account = createAccount();
    when(accountRepository.findAll()).thenReturn(singletonList(account));

    Iterable<Account> accounts = accountService.findAll();
    assertThat(accounts).isEqualTo(singletonList(account));
  }

  @Test
  public void getOne_account_whichIsExisted_returnsAccount() {
    Account account = createAccount();
    when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

    Account foundedAccount = accountService.getById(1L);
    assertThat(foundedAccount).isEqualTo(account);
  }

  @Test(expected = ResourceNotFoundException.class)
  public void getOne_account_whichIsNotExisted_throwsNotFound() {
    when(accountRepository.findById(1L)).thenReturn(Optional.empty());
    accountService.getById(1L);
  }

  @Test
  public void update_account_whichIsExisted_returnsAccount() {
    Account account = createAccount();
    when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

    String updatedName = "updatedName";
    Account updatedAccount = accountService.update(1L, updatedName);
    assertThat(updatedAccount.getHolderName()).isEqualTo(updatedName);
  }

  @Test(expected = ResourceNotFoundException.class)
  public void update_account_whichIsNotExisted_throwsNotFound() {
    when(accountRepository.findById(1L)).thenReturn(Optional.empty());
    accountService.update(1L, "updatedName");
  }

  @Test
  public void delete_account_whichIsExisted_doesNotThrowException() {
    Account account = createAccount();
    when(accountRepository.delete(1L)).thenReturn(account);

    accountService.delete(1L);
  }

  @Test(expected = ResourceNotFoundException.class)
  public void delete_account_whichIsExisted_throwsException() {
    when(accountRepository.findById(1L)).thenReturn(Optional.empty());
    accountService.delete(1L);
  }
}
