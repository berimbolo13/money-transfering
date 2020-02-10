package org.shulikov.transfer.integration.account;

import static java.lang.String.format;

import org.shulikov.transfer.integration.IntegrationTestBase;

public abstract class AccountIntegrationTestBase extends IntegrationTestBase {

  protected final String HOLDER_NAME_IS_NOT_SPECIFIED = format("\"%s must be specified\"", HOLDER_NAME_PARAM);
}
