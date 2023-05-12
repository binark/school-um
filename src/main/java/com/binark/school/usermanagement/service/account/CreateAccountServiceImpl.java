package com.binark.school.usermanagement.service.account;

import com.binark.school.usermanagement.UserManagementApplication;
import com.binark.school.usermanagement.dto.OwnerAccountDTO;
import com.binark.school.usermanagement.entity.Account;
import com.binark.school.usermanagement.exception.EmailUsedException;
import com.binark.school.usermanagement.mapper.AccountMapper;
import com.binark.school.usermanagement.proxy.OwnerProxy;
import com.binark.school.usermanagement.publisher.Ipublisher;
import com.binark.school.usermanagement.repository.AccountRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class CreateAccountServiceImpl implements CreateAccountService {

    private final AccountRepository repository;

    private final AccountMapper mapper;

    private final OwnerProxy proxy;

    @Qualifier("OwnerCreatePublisher")
    private final Ipublisher<OwnerAccountDTO> publisher;

//    @Autowired
//    private UserManagementApplication.TestCircuitBreacker testCircuitBreacker;

   // @PostConstruct
    @CircuitBreaker(name = "test", fallbackMethod = "fallback")
    public void testCircuitBreacker() {
       // String res = this.testCircuitBreacker.principal("Armel");

        System.out.println("**************************************** res = ");

//        String test = this.proxy.createOwner();
//
//        System.out.println("************************************************ test = " + test);
    }

    void fallback(Throwable throwable) {
        System.out.println("************************************** fallback called ***************************");
    }

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

        this.publisher.publsh(owner);
    }

    private String randomPassword() {
        String password = UUID.randomUUID().toString();

        return password.length() <= 32 ? password : password.substring(0, 31);
    }
}
