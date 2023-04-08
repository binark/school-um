package com.binark.school.usermanagement.mapper;

import com.binark.school.usermanagement.dto.OwnerAccountDTO;
import com.binark.school.usermanagement.entity.Account;
import com.binark.school.usermanagement.entity.Owner;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(source = "identifier", target = "email")
    OwnerAccountDTO toOwnerDto(Account school);

    @Mapping(target = "identifier", source = "email")
    Owner toOwnerEntity(OwnerAccountDTO schoolDto);
}