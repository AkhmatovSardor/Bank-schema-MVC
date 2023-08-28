package com.example.Bank.Schema.service.validate;

import com.example.Bank.Schema.dto.AccountDto;
import com.example.Bank.Schema.dto.ErrorDto;
import com.example.Bank.Schema.repository.AccountRepository;
import com.example.Bank.Schema.service.BranchService;
import com.example.Bank.Schema.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountValidate {
    private final BranchService branchService;
    public List<ErrorDto> validate(AccountDto dto) {
        List<ErrorDto> errors=new ArrayList<>();
        if (branchService.get(dto.getBranchId()).getData()==null){
            errors.add(new ErrorDto("branch","branch not found!"));
        }
        return errors;
    }
}
