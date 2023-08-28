package com.example.Bank.Schema.service.validate;

import com.example.Bank.Schema.dto.CreditCardDto;
import com.example.Bank.Schema.dto.ErrorDto;
import com.example.Bank.Schema.repository.CreditCardRepository;
import com.example.Bank.Schema.service.AccountService;
import com.example.Bank.Schema.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CreditCardValidate {
    private final CreditCardRepository creditCardRepository;
    private final CustomerService customerService;
    public List<ErrorDto> validate(CreditCardDto dto) {
        List<ErrorDto> errors = new ArrayList<>();
        if (creditCardRepository.existsByNumber(dto.getNumber())){
            errors.add(new ErrorDto("Number","Number already exist"));
        }
        if (customerService.get(dto.getCustomerId()).getData()==null){
            errors.add(new ErrorDto("customer","customer not found!"));
        }
        return errors;
    }
}
