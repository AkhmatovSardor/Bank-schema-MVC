package com.example.Bank.Schema.service.validate;

import com.example.Bank.Schema.dto.BorrowerDto;
import com.example.Bank.Schema.dto.ErrorDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BorrowerValidate {
    public List<ErrorDto> validate(BorrowerDto dto) {
        List<ErrorDto> errors=new ArrayList<>();
        return errors;
    }
}
