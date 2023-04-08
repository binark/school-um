package com.binark.school.usermanagement.repository;

import com.binark.school.usermanagement.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
