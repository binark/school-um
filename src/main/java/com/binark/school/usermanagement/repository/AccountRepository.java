package com.binark.school.usermanagement.repository;

import com.binark.school.usermanagement.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findOneByIdentifier(String email);

    Optional<Account> findOneByResetPasswordKey(String resetPasswordKey);

    Optional<Account> findOneBySlug(String slug);

    void deleteByIdentifier(String email);
}
