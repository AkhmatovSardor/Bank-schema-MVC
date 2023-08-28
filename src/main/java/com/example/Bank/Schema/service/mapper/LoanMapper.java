package com.example.Bank.Schema.service.mapper;

import com.example.Bank.Schema.dto.LoanDto;
import com.example.Bank.Schema.entity.Loan;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring",imports = {Collectors.class})
public abstract class LoanMapper {
    @Autowired
    protected PaymentMapper paymentMapper;
    @Autowired
    protected AccountMapper accountMapper;
    @Mapping(target = "payment",ignore = true)
    @Mapping(target = "account",ignore = true)
    public abstract LoanDto toDto(Loan loan);
    @Mapping(target = "payment",expression = "java(this.paymentMapper.toDto(loan.getPayment()))")
    @Mapping(target = "account",expression = "java(loan.getAccount().stream().map(this.accountMapper::toDto).collect(Collectors.toSet()))")
    public abstract LoanDto toDtoWithAll(Loan loan);
    @Mapping(target = "loanId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    public abstract Loan toEntity(LoanDto dto);
    @Mapping(target = "loanId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void update(@MappingTarget Loan loan, LoanDto dto);
}
