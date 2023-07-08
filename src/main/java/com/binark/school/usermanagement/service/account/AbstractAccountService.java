package com.binark.school.usermanagement.service.account;

import com.binark.school.usermanagement.entity.Account;
import com.binark.school.usermanagement.exception.UserNotFoundException;
import com.binark.school.usermanagement.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractAccountService implements AccountService{

    protected final AccountRepository repository;

    @Override
    @Transactional
    public Account updateAccount(Account account) {
        return repository.save(account);
    }

    @Override
    public Account getAccountByResetPasswordKey(String resetPasswordKey) throws UserNotFoundException {
        return repository.findOneByResetPasswordKey(resetPasswordKey)
                .orElseThrow(() -> {
                    log.error("Unable to find user with reset password key:  ", resetPasswordKey);
                    return new UserNotFoundException();
                });
    }

    @Override
    public Account getAccountBySlug(String accountSlug) throws UserNotFoundException {
        return repository.findOneBySlug(accountSlug)
                .orElseThrow(() -> {
                    log.error("Unable to find user with slug:  ", accountSlug);
                    return new UserNotFoundException();
                });
    }

    protected String randomPassword() {
        String password = UUID.randomUUID().toString();

        return password.length() <= 32 ? password : password.substring(0, 31);
    }
}
