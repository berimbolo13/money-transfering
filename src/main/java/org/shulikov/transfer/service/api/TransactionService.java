package org.shulikov.transfer.service.api;

import org.shulikov.transfer.model.Transaction;

public interface TransactionService {

  void perform(Transaction transaction);
}
