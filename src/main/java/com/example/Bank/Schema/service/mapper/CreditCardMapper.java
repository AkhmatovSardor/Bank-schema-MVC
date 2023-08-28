package com.example.Bank.Schema.service.mapper;

import com.example.Bank.Schema.dto.CreditCardDto;
import com.example.Bank.Schema.entity.CreditCard;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class CreditCardMapper {
    public abstract CreditCardDto toDto(CreditCard creditCard);

    @Mapping(target = "creditCardId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    public abstract CreditCard toEntity(CreditCardDto dto);

    @Mapping(target = "creditCardId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void update(@MappingTarget CreditCard card, CreditCardDto dto);
}
