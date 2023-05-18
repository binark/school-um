package com.binark.school.usermanagement.service.iam;

import com.binark.school.usermanagement.entity.Account;

public interface IamManager {

    String createUser(Account account) throws IllegalArgumentException;
}
