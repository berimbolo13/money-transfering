package org.shulikov.transfer.unit.transaction;

import org.shulikov.transfer.model.Transaction;

public abstract class TransactionTestBase {

  protected Transaction createTransaction() {
    return new Transaction(1L, 2L, 5);
  }
}
