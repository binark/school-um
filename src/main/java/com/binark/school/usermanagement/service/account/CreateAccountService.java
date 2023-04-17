package com.binark.school.usermanagement.service.account;

import com.binark.school.usermanagement.entity.Account;
import com.binark.school.usermanagement.exception.AccountIdentifierUsedException;

public interface CreateAccountService {

    /**
     * Create user account
     * @param owner
     * @throws AccountIdentifierUsedException
     */
    void create(Account owner) throws AccountIdentifierUsedException;

    void testCircuitBreacker();
}
