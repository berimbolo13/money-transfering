package org.shulikov.transfer.unit.account;

import java.util.concurrent.atomic.AtomicInteger;
import org.shulikov.transfer.model.Account;

public abstract class AccountTestBase {

  protected Account createAccount() {
    return new Account(1L, "Roland", new AtomicInteger());
  }
}
