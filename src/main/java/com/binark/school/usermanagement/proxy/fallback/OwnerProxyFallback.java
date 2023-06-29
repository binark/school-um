package com.binark.school.usermanagement.proxy.fallback;

import com.binark.school.usermanagement.dto.OwnerAccountDTO;
import com.binark.school.usermanagement.proxy.OwnerProxy;
import com.binark.school.usermanagement.repository.AccountRepository;
import org.springframework.stereotype.Component;

@Component
public class OwnerProxyFallback {

    private final AccountRepository accountRepository;

    public OwnerProxyFallback(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

   // @Override
    public String createOwner() {

        System.out.println("************************* resilience fallback ********************************");

    //    this.accountRepository.deleteByIdentifier(accountDTO.getEmail());

        return "Fallback called";
    }
}
