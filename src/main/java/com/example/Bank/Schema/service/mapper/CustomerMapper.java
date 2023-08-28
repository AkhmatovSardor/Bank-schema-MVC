package com.example.Bank.Schema.service.mapper;

import com.example.Bank.Schema.dto.CustomerDto;
import com.example.Bank.Schema.entity.Customer;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", imports = {Collectors.class})
public abstract class CustomerMapper {
    @Autowired
    protected BorrowerMapper borrowerMapper;
    @Autowired
    protected TransactionMapper transactionMapper;
    @Autowired
    protected CreditCardMapper creditCardMapper;
    @Autowired
    protected AuthoritiesMapper authoritiesMapper;
    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Mapping(target = "customerId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "authority", ignore = true)
    @Mapping(target = "password", expression = "java(this.passwordEncoder.encode(dto.getPassword()))")
    public abstract Customer toEntity(CustomerDto dto);

    @Mapping(target = "creditCard", ignore = true)
    @Mapping(target = "borrower", ignore = true)
    @Mapping(target = "transaction", ignore = true)
    @Mapping(target = "authority", ignore = true)
    public abstract CustomerDto toDto(Customer customer);

    @Mapping(target = "creditCard", expression = "java(customer.getCreditCard().stream().map(this.creditCardMapper::toDto).collect(Collectors.toSet()))")
    @Mapping(target = "borrower", expression = "java(this.borrowerMapper.toDto(customer.getBorrower()))")
    @Mapping(target = "transaction", expression = "java(this.transactionMapper.toDto(customer.getTransaction()))")
    @Mapping(target = "authority", expression = "java(customer.getAuthority().stream().map(this.authoritiesMapper::toDto).collect(Collectors.toSet()))")
    public abstract CustomerDto toDtoWithAll(Customer customer);

    @Mapping(target = "customerId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "authority", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void update(@MappingTarget Customer customer, CustomerDto dto);
}
