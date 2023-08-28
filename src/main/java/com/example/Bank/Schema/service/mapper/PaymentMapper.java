package com.example.Bank.Schema.service.mapper;

import com.example.Bank.Schema.dto.PaymentDto;
import com.example.Bank.Schema.entity.Payment;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public abstract class PaymentMapper {
    public abstract PaymentDto toDto(Payment payment);

    @Mapping(target = "paymentId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    public abstract Payment toEntity(PaymentDto dto);

    @Mapping(target = "paymentId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void update(@MappingTarget Payment payment, PaymentDto dto);

}
