package com.binark.school.usermanagement.service.account;

import com.binark.school.usermanagement.entity.Account;
import com.binark.school.usermanagement.exception.AccountIdentifierUsedException;
import com.binark.school.usermanagement.exception.UserNotFoundException;

public interface AccountService {

    /**
     * Create user account
     * @param owner
     * @throws AccountIdentifierUsedException
     */
    void create(Account owner) throws AccountIdentifierUsedException;

    Account updateAccount(Account account);

    Account getAccountByResetPasswordKey(String resetPasswordKey) throws UserNotFoundException;

    Account getAccountBySlug(String accountSlug) throws UserNotFoundException;
}
