package org.shulikov.transfer.unit.account.validator;

import org.junit.Test;
import org.shulikov.transfer.exception.BadRequestException;
import org.shulikov.transfer.exception.ResourceNotFoundException;
import org.shulikov.transfer.model.Account;

public class AccountValidatorTest extends AccountValidatorTestBase {

  @Test
  public void validate_beforeWithdraw_accountExisted_withValidBalance() {
    Account account = new Account(1L, "holderName", 5);
    accountValidator.validateForWithdraw(account.getId(), account, 2);
  }

  @Test(expected = ResourceNotFoundException.class)
  public void validate_beforeWithdraw_accountNonExisted() {
    accountValidator.validateForWithdraw(1L, null, 2);
  }

  @Test(expected = BadRequestException.class)
  public void validate_beforeWithdraw_accountExisted_withInvalidBalance() {
    Account account = new Account(1L, "holderName", 5);
    accountValidator.validateForWithdraw(account.getId(), account, 10);
  }

  @Test
  public void validate_beforeDeposit_accountExisted() {
    Account account = createAccount();
    accountValidator.validateForDeposit(account.getId(), account);
  }

  @Test(expected = ResourceNotFoundException.class)
  public void validate_beforeDeposit_accountNonExisted() {
    accountValidator.validateForDeposit(1L, null);
  }
}
