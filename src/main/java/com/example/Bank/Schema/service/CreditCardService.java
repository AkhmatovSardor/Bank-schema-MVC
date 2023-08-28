package com.example.Bank.Schema.service;

import com.example.Bank.Schema.dto.CreditCardDto;
import com.example.Bank.Schema.dto.ErrorDto;
import com.example.Bank.Schema.dto.ResponseDto;
import com.example.Bank.Schema.repository.CreditCardRepository;
import com.example.Bank.Schema.service.mapper.CreditCardMapper;
import com.example.Bank.Schema.service.validate.CreditCardValidate;
import com.example.Bank.Schema.util.SimpleCrud;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditCardService implements SimpleCrud<Integer, CreditCardDto> {
    private final CreditCardMapper creditCardMapper;
    private final CreditCardRepository creditCardRepository;
    private final CreditCardValidate creditCardValidate;

    @Override
    public ResponseDto<CreditCardDto> create(CreditCardDto dto) {
        List<ErrorDto> errors = this.creditCardValidate.validate(dto);
        if (!errors.isEmpty()) {
            return ResponseDto.<CreditCardDto>builder()
                    .message("Validate error!")
                    .code(-2)
                    .errors(errors)
                    .build();
        }
        try {
            return ResponseDto.<CreditCardDto>builder()
                    .message("Credit card successful created!")
                    .success(true)
                    .data(this.creditCardMapper.toDto(this.creditCardRepository.save(this.creditCardMapper.toEntity(dto))))
                    .build();
        } catch (Exception e) {
            return ResponseDto.<CreditCardDto>builder()
                    .message("While saving error!")
                    .code(-3)
                    .build();
        }
    }

    @Override
    public ResponseDto<CreditCardDto> get(Integer id) {
        return this.creditCardRepository.findByCreditCardIdAndDeletedAtIsNull(id)
                .map(card -> ResponseDto.<CreditCardDto>builder()
                        .message("OK")
                        .success(true)
                        .data(this.creditCardMapper.toDto(card))
                        .build())
                .orElse(ResponseDto.<CreditCardDto>builder()
                        .message("Not found!")
                        .code(-1)
                        .build());
    }

    @Override
    public ResponseDto<CreditCardDto> update(Integer id, CreditCardDto dto) {
        try {
            return this.creditCardRepository.findByCreditCardIdAndDeletedAtIsNull(id)
                    .map(card -> {
                        this.creditCardMapper.update(card, dto);
                        this.creditCardRepository.save(card);
                        return ResponseDto.<CreditCardDto>builder()
                                .data(this.creditCardMapper.toDto(card))
                                .message("Credit card successful updated!")
                                .success(true)
                                .build();
                    }).orElse(ResponseDto.<CreditCardDto>builder()
                            .message("Not found!")
                            .code(-1)
                            .build());
        } catch (Exception e) {
            return ResponseDto.<CreditCardDto>builder()
                    .message("While updating error!")
                    .code(-3)
                    .build();
        }
    }

    @Override
    public ResponseDto<CreditCardDto> delete(Integer id) {
        try {
            return this.creditCardRepository.findByCreditCardIdAndDeletedAtIsNull(id)
                    .map(card -> {
                        card.setDeletedAt(LocalDateTime.now());
                        this.creditCardRepository.save(card);
                        return ResponseDto.<CreditCardDto>builder()
                                .data(this.creditCardMapper.toDto(card))
                                .message("Credit card successful deleted!")
                                .success(true)
                                .build();
                    })
                    .orElse(ResponseDto.<CreditCardDto>builder()
                            .message("Not found!")
                            .code(-1)
                            .build());
        } catch (Exception e) {
            return ResponseDto.<CreditCardDto>builder()
                    .message("While deleting error " + e.getMessage())
                    .code(-3)
                    .build();
        }
    }
}
