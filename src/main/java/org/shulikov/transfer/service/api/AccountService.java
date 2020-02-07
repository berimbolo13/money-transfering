package org.shulikov.transfer.service.api;

import org.shulikov.transfer.model.Account;

public interface AccountService {

  Iterable<Account> findAll();

  Account getById(Long id);

  Account create(String holderName);

  Account update(Long id, String holderName);

  void delete(Long id);
}
