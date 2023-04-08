package com.binark.school.usermanagement.service.account;

import com.binark.school.usermanagement.dto.OwnerAccountDTO;
import com.binark.school.usermanagement.entity.Account;
import com.binark.school.usermanagement.exception.EmailUsedException;

public interface CreateAccountService {

    void createOwner(OwnerAccountDTO owner) throws EmailUsedException;
}
