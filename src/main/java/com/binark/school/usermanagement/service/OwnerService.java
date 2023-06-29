package com.binark.school.usermanagement.service;

import com.binark.school.usermanagement.dto.OwnerAccountDTO;
import com.binark.school.usermanagement.entity.Owner;

import java.util.List;

public interface OwnerService {

    /**
     * Get all existing owners
     * @return {@link List} Collection of owners
     */
    List<Owner> getAll();
}
