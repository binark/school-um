package com.binark.school.usermanagement.service;

import com.binark.school.usermanagement.dto.OwnerAccountDTO;
import com.binark.school.usermanagement.entity.Owner;
import com.binark.school.usermanagement.mapper.AccountMapper;
import com.binark.school.usermanagement.repository.OwnerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository repository;

    @Override
    public List<Owner> getAll() {
        List<Owner> accounts = repository.findAll()
                                                    .stream()
                                                    .collect(Collectors.toList());
        return accounts;
    }
}
