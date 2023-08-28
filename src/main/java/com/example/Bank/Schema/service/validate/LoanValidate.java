package com.example.Bank.Schema.service.validate;

import com.example.Bank.Schema.dto.ErrorDto;
import com.example.Bank.Schema.dto.LoanDto;
import com.example.Bank.Schema.service.AccountService;
import com.example.Bank.Schema.service.BorrowerService;
import com.example.Bank.Schema.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LoanValidate {
    private final AccountService accountService;
    public List<ErrorDto> validate(LoanDto dto) {
        List<ErrorDto>errors=new ArrayList<>();
        if (accountService.get(dto.getAccountId()).getData()==null){
            errors.add(new ErrorDto("account",String.format("This %d id account not found!",dto.getAccountId())));
        }
        return errors;
    }
}
