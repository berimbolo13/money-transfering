package org.shulikov.transfer.repository.api;

import java.util.Optional;
import org.shulikov.transfer.model.Account;

public interface AccountRepository {

  Optional<Account> findById(Long id);

  Iterable<Account> findAll();

  Account create(Account account);

  Account delete(Long id);
}
