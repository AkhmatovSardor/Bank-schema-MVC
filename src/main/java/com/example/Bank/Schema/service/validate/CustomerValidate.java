package com.example.Bank.Schema.service.validate;

import com.example.Bank.Schema.dto.CustomerDto;
import com.example.Bank.Schema.dto.ErrorDto;
import com.example.Bank.Schema.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomerValidate {
    private final CustomerRepository customerRepository;
    public List<ErrorDto> validate(CustomerDto dto) {
        List<ErrorDto> errors = new ArrayList<>();
        if (customerRepository.existsByEmail(dto.getEmail())){
            errors.add(new ErrorDto("Email","Email already exists!"));
        }
        if(customerRepository.existsByUsername(dto.getUsername())){
            errors.add(new ErrorDto("Username","Username already exists!"));
        }
        return errors;
    }
}
