package com.binark.school.usermanagement.service;

import com.binark.school.usermanagement.dto.OwnerAccountDTO;
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

    private final AccountMapper mapper;

    @Override
    public List<OwnerAccountDTO> getAll() {
        List<OwnerAccountDTO> accounts = repository.findAll()
                                                    .stream()
                                                    .map(mapper::toOwnerDto)
                                                    .collect(Collectors.toList());
        System.out.println("accounts = " + accounts);
        return accounts;
    }
}
