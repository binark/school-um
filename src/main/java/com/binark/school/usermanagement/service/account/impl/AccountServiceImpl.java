package com.binark.school.usermanagement.service.account.impl;

import com.binark.school.usermanagement.entity.Account;
import com.binark.school.usermanagement.exception.EmailUsedException;
import com.binark.school.usermanagement.exception.UserNotFoundException;
import com.binark.school.usermanagement.repository.AccountRepository;
import com.binark.school.usermanagement.service.account.AbstractAccountService;
import com.binark.school.usermanagement.service.account.AccountService;
import com.binark.school.usermanagement.service.iam.AccountIamManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Primary
@Slf4j
public class AccountServiceImpl extends AbstractAccountService implements AccountService {

    private final AccountIamManager accountIamManager;

    @Autowired
    public AccountServiceImpl(AccountRepository repository, AccountIamManager accountIamManager) {
        super(repository);
        this.accountIamManager = accountIamManager;
    }

    public void addSchoolToAccount(String accountSlug, String schoolSlug) throws UserNotFoundException {

        Account account = getAccountBySlug(accountSlug);

        accountIamManager.addUserSchool(account.getIamId(), schoolSlug);
    }

    @Override
    @Transactional
    public void create(Account account) throws EmailUsedException {
        String email = account.getIdentifier();
        Account existing = this.repository.findOneByIdentifier(email).orElse(null);

        if (existing != null) {
            throw new EmailUsedException(email);
        }
        account.setEnabled(false);
        account.setPassword(randomPassword());
        account.setResetPasswordKey(UUID.randomUUID().toString());
        account.setResetPasswordExpiration(LocalDateTime.now());

        //  Account account = mapper.toOwnerEntity(owner);

        //     repository.save(account);

        //   proxy.createOwner(owner);

        //   this.publisher.publsh(owner);
    }

}
