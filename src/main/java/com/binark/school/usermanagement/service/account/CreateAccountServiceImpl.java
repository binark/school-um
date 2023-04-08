package com.binark.school.usermanagement.service.account;

import com.binark.school.usermanagement.dto.OwnerAccountDTO;
import com.binark.school.usermanagement.entity.Account;
import com.binark.school.usermanagement.exception.EmailUsedException;
import com.binark.school.usermanagement.mapper.AccountMapper;
import com.binark.school.usermanagement.proxy.OwnerProxy;
import com.binark.school.usermanagement.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class CreateAccountServiceImpl implements CreateAccountService {

    private final AccountRepository repository;

    private final AccountMapper mapper;

    private final OwnerProxy proxy;

    @Override
    public void createOwner(OwnerAccountDTO owner) throws EmailUsedException {
        String email = owner.getEmail();
        Account existing = this.repository.findOneByIdentifier(email).orElse(null);

        if (existing != null) {
            throw new EmailUsedException(email);
        }
        owner.setEnabled(false);
        owner.setPassword(randomPassword());

        Account account = mapper.toOwnerEntity(owner);

        repository.save(account);

        proxy.createOwner(owner);
    }

    private String randomPassword() {
        String password = UUID.randomUUID().toString();

        return password.length() <= 32 ? password : password.substring(0, 31);
    }
}
