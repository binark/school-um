package com.binark.school.usermanagement.service.account;

import com.binark.school.usermanagement.entity.Account;
import com.binark.school.usermanagement.entity.Owner;
import com.binark.school.usermanagement.exception.EmailUsedException;
import com.binark.school.usermanagement.proxy.OwnerProxy;
import com.binark.school.usermanagement.publisher.Ipublisher;
import com.binark.school.usermanagement.repository.AccountRepository;
import com.binark.school.usermanagement.service.iam.IamManager;
import com.binark.school.usermanagement.utils.RandomPasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service("createOwner")
public class OwnerAccountServiceImpl extends AbstractAccountService implements AccountService {

    @Value("${school.pwd-token-expiration}")
    private int tokenExpirationTime;

    private final OwnerProxy proxy;

    private final Ipublisher<Owner> publisher;

    private final IamManager iamManager;

    @Autowired
    public OwnerAccountServiceImpl(
            AccountRepository repository,
            OwnerProxy proxy,
            Ipublisher<Owner> publisher,
            @Qualifier("iamOwner") IamManager iamManager) {

        super(repository);
        this.proxy = proxy;
        this.publisher = publisher;
        this.iamManager = iamManager;
    }

    /**
     * Create owner account
     * @param account should be instance of {@link Owner}
     * @throws EmailUsedException
     */
    @Transactional
    @Override
    public void create(Account account) throws EmailUsedException {

        // Check account is instance of Owner. If not, throw exception
        // TODO create custom exception for mismatch type
        if (!(account instanceof Owner)) {
            throw new IllegalArgumentException("The object is not an instance of owner");
        }

        // Cast owner data
        Owner owner = (Owner) account;

        String email = owner.getIdentifier();

        // Find existing owner by email
        Account existing = this.repository.findOneByIdentifier(email).orElse(null);

        // If owner exists, throw exception
        if (existing != null) {
            throw new EmailUsedException(email);
        }

        // Enable and set default random password for owner
        owner.setEnabled(false);
        owner.setPassword(RandomPasswordGenerator.getRandomPassword());
        owner.setResetPasswordKey(UUID.randomUUID().toString());
        owner.setResetPasswordExpiration(LocalDateTime.now().plusHours(tokenExpirationTime));

        //Create keycloak account
        String iamId = this.iamManager.createUser(owner);

        owner.setIamId(iamId);

        // save owner to database
        this.repository.save(owner);

        // Create owner to core module
     //   this.proxy.createOwner(owner);

        // Send owner email
        this.publisher.publish(owner);
    }
}
