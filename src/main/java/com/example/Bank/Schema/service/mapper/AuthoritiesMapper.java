package com.example.Bank.Schema.service.mapper;

import com.example.Bank.Schema.dto.AuthoritiesDto;
import com.example.Bank.Schema.entity.Authorities;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthoritiesMapper {
    AuthoritiesDto toDto(Authorities authorities);

    Authorities toEntity(AuthoritiesDto dto);
}
