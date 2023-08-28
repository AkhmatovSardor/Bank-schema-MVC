package com.example.Bank.Schema.service.mapper;

import com.example.Bank.Schema.dto.AccountDto;
import com.example.Bank.Schema.entity.Account;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", imports = {Collectors.class})
public interface AccountMapper {
    @Mapping(target = "accountId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    Account toEntity(AccountDto dto);

    AccountDto toDto(Account account);

    @Mapping(target = "accountId",ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget Account account, AccountDto dto);
}
