package com.binark.school.usermanagement.service.account;

import com.binark.school.usermanagement.entity.Account;
import com.binark.school.usermanagement.exception.AccountIdentifierUsedException;
import com.binark.school.usermanagement.exception.UserNotFoundException;

public interface AccountService {

    /**
     * Create user account
     * @param account The account to create
     * @throws AccountIdentifierUsedException If the identifier is already used
     */
    void create(Account account) throws AccountIdentifierUsedException;

    /**
     * Update user account
     * @param account The account to update
     * @return The updated account
     */
    void updateAccount(Account account);

    /**
     * Find account by passing a reset password token
     * @param resetPasswordKey The reset password token
     * @return The retrieved account
     * @throws UserNotFoundException If account not found
     */
    Account getAccountByResetPasswordKey(String resetPasswordKey) throws UserNotFoundException;

    /**
     * Find user by slug
     * @param accountSlug The given slug
     * @return The retrieved user
     * @throws UserNotFoundException If account not found
     */
    Account getAccountBySlug(String accountSlug) throws UserNotFoundException;

    /**
     * Find user by its identifier
     * @param identifier The given identifier
     * @return The retrieved user
     * @throws UserNotFoundException If account not found
     */
    Account findByIdentifier(String identifier) throws UserNotFoundException;
}
