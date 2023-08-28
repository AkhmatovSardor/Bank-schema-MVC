package com.example.Bank.Schema.service.validate;

import com.example.Bank.Schema.dto.ErrorDto;
import com.example.Bank.Schema.dto.TransactionDto;
import com.example.Bank.Schema.service.AccountService;
import com.example.Bank.Schema.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TransactionValidate {
    private final AccountService accountService;
    public List<ErrorDto> validate(TransactionDto dto) {
        List<ErrorDto> errors=new ArrayList<>();
        return errors;
    }
}
