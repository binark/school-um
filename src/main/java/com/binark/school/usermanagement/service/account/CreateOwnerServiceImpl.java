package com.binark.school.usermanagement.service.account;

import com.binark.school.usermanagement.entity.Account;
import com.binark.school.usermanagement.entity.Owner;
import com.binark.school.usermanagement.exception.EmailUsedException;
import com.binark.school.usermanagement.proxy.OwnerProxy;
import com.binark.school.usermanagement.publisher.Ipublisher;
import com.binark.school.usermanagement.repository.AccountRepository;
import com.binark.school.usermanagement.service.iam.IamManager;
import com.binark.school.usermanagement.utils.RandomPasswordGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("createOwner")
@RequiredArgsConstructor
public class CreateOwnerServiceImpl implements CreateAccountService {

    private final AccountRepository repository;

    private final OwnerProxy proxy;

//    @Autowired
//    @Qualifier("OwnerCreatePublisher")
    private final Ipublisher<Owner> publisher;

    @Autowired
    @Qualifier("iamOwner")
    private IamManager iamManager;

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

        //Create keycloak account
        String iamId = this.iamManager.createUser(owner);

        owner.setIamId(iamId);

        // save owner to database
        this.repository.save(owner);

        // Create owner to core module
<<<<<<< HEAD
//        this.proxy.createOwner(owner);
=======
        this.proxy.createOwner(owner);
>>>>>>> c231733 (create owner)

        // Send owner email
        this.publisher.publsh(owner);
    }

    @Override
    public void testCircuitBreacker() {

    }
}
