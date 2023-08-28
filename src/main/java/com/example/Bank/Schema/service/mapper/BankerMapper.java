package com.example.Bank.Schema.service.mapper;

import com.example.Bank.Schema.dto.BankerDto;
import com.example.Bank.Schema.entity.Banker;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BankerMapper {
    @Mapping(target = "bankerId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    Banker toEntity(BankerDto dto);

    BankerDto toDto(Banker banker);

    @Mapping(target = "bankerId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget Banker banker, BankerDto dto);
}
