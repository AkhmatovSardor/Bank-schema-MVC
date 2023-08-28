package com.example.Bank.Schema.service.validate;

import com.example.Bank.Schema.dto.BranchDto;
import com.example.Bank.Schema.dto.ErrorDto;
import com.example.Bank.Schema.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BranchValidate {
    private final BranchRepository branchRepository;
    public List<ErrorDto> valid(BranchDto dto) {
        List<ErrorDto> errors=new ArrayList<>();
        if (this.branchRepository.existsByAddress(dto.getAddress())){
            errors.add(new ErrorDto("address","address already exist!"));
        }
        return errors;
    }
}
