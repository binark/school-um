package com.binark.school.usermanagement.service.auth;

import com.binark.school.usermanagement.entity.Account;
import com.binark.school.usermanagement.exception.ResetPasswordExpirationException;
import com.binark.school.usermanagement.exception.UserNotFoundException;
import com.binark.school.usermanagement.publisher.Ipublisher;
import com.binark.school.usermanagement.service.account.AccountService;
import com.binark.school.usermanagement.service.iam.AccountIamManager;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PasswordManagementService {

    private final AccountService accountService;

    private final PasswordEncoder passwordEncoder;

    private final AccountIamManager iamManager;

    private final Ipublisher<Account> publisher;

    public String checkResetPasswordPossibility(String resetPasswordKey) throws UserNotFoundException, ResetPasswordExpirationException {
        Account account = accountService.getAccountByResetPasswordKey(resetPasswordKey);

        if (hasResetPasswordKeyExpired(account.getResetPasswordExpiration())) {
            throw new ResetPasswordExpirationException();
        }

        return account.getSlug();
    }

    public void setUserPassword(String slug, String password) throws UserNotFoundException {

        Account account = accountService.getAccountBySlug(slug);
        this.populateUserAccount(account, password);

        iamManager.activateAccount(account.getIamId(), password);

        accountService.updateAccount(account);

        publisher.publish(account);
    }

    private boolean hasResetPasswordKeyExpired(@NotNull LocalDateTime resetPasswordKeyExpirationTime) {

        Optional.ofNullable(resetPasswordKeyExpirationTime).orElseThrow(()-> new IllegalArgumentException());

        return resetPasswordKeyExpirationTime.isBefore(LocalDateTime.now());
    }

    private void populateUserAccount(Account account, String password) {
        account.setPassword(passwordEncoder.encode(password));
        account.setResetPasswordKey(null);
        account.setResetPasswordExpiration(null);
        account.setEnabled(true);
    }
}
