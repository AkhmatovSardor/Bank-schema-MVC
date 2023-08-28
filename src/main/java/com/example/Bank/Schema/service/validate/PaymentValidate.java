package com.example.Bank.Schema.service.validate;

import com.example.Bank.Schema.dto.ErrorDto;
import com.example.Bank.Schema.dto.PaymentDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PaymentValidate {
    public List<ErrorDto> validate(PaymentDto dto) {
        List<ErrorDto>errors=new ArrayList<>();
        return errors;
    }
}
