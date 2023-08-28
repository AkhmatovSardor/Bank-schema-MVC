package com.example.Bank.Schema.service.mapper;

import com.example.Bank.Schema.dto.BranchDto;
import com.example.Bank.Schema.entity.Branch;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", imports = {Collectors.class})
public abstract class BranchMapper {
    @Autowired
    protected BankerMapper bankerMapper;
    @Autowired
    protected LoanMapper loanMapper;
    @Autowired
    protected AccountMapper accountMapper;

    @Mapping(target = "loan", ignore = true)
    @Mapping(target = "banker", ignore = true)
    @Mapping(target = "account", ignore = true)
    public abstract BranchDto toDto(Branch branch);

    @Mapping(target = "loan", expression = "java(branch.getLoan().stream().map(loanMapper::toDto).collect(Collectors.toSet()))")
    @Mapping(target = "banker", expression = "java(branch.getBanker().stream().map(bankerMapper::toDto).collect(Collectors.toSet()))")
    @Mapping(target = "account", expression = "java(branch.getAccount().stream().map(accountMapper::toDto).collect(Collectors.toSet()))")
    public abstract BranchDto toDtoWithAll(Branch branch);

    @Mapping(target = "branchId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    public abstract Branch toEntity(BranchDto dto);

    @Mapping(target = "branchId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void update(@MappingTarget Branch branch, BranchDto dto);
}
