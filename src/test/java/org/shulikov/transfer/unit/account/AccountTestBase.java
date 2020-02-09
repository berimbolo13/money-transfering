package org.shulikov.transfer.unit.account;

import org.shulikov.transfer.model.Account;

public abstract class AccountTestBase {

  protected Account createAccount() {
    return new Account(1L, "Roland", 0);
  }
}
