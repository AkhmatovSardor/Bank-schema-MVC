package com.example.Bank.Schema.service.mapper;

import com.example.Bank.Schema.dto.BorrowerDto;
import com.example.Bank.Schema.entity.Borrower;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class BorrowerMapper {
    @Autowired
    protected LoanMapper loanMapper;

    @Mapping(target = "loan", ignore = true)
    public abstract BorrowerDto toDto(Borrower borrower);

    @Mapping(target = "loan", expression = "java(this.loanMapper.toDto(borrower.getLoan()))")
    public abstract BorrowerDto toDtoWithLoan(Borrower borrower);

    @Mapping(target = "borrowId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    public abstract Borrower toEntity(BorrowerDto dto);

    @Mapping(target = "borrowId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void update(@MappingTarget Borrower borrower, BorrowerDto dto);
}
