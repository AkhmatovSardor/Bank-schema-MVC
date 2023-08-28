package com.example.Bank.Schema.service;

import com.example.Bank.Schema.dto.ErrorDto;
import com.example.Bank.Schema.dto.ResponseDto;
import com.example.Bank.Schema.dto.TransactionDto;
import com.example.Bank.Schema.repository.TransactionRepository;
import com.example.Bank.Schema.service.mapper.TransactionMapper;
import com.example.Bank.Schema.service.validate.TransactionValidate;
import com.example.Bank.Schema.util.SimpleCrud;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService implements SimpleCrud<Integer, TransactionDto> {
    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;
    private final TransactionValidate transactionValidate;

    @Override
    public ResponseDto<TransactionDto> create(TransactionDto dto) {
        List<ErrorDto> errors = this.transactionValidate.validate(dto);
        if (!errors.isEmpty()) {
            return ResponseDto.<TransactionDto>builder()
                    .message("Validate error!")
                    .code(-2)
                    .errors(errors)
                    .build();
        }
        try {
            return ResponseDto.<TransactionDto>builder()
                    .data(this.transactionMapper.toDto(this.transactionRepository.save(this.transactionMapper.toEntity(dto))))
                    .success(true)
                    .message("Transaction successful created!")
                    .build();
        } catch (Exception e) {
            return ResponseDto.<TransactionDto>builder()
                    .message("While saving error " + e.getMessage())
                    .code(-3)
                    .build();
        }
    }

    @Override
    public ResponseDto<TransactionDto> get(Integer id) {
        return this.transactionRepository.findByTransactionIdAndDeletedAtIsNull(id)
                .map(transaction -> ResponseDto.<TransactionDto>builder()
                        .message("OK")
                        .success(true)
                        .data(this.transactionMapper.toDtoWithAccount(transaction))
                        .build())
                .orElse(ResponseDto.<TransactionDto>builder()
                        .message("Not found!")
                        .code(-1)
                        .build());
    }

    @Override
    public ResponseDto<TransactionDto> update(Integer id, TransactionDto dto) {
        try {
            return this.transactionRepository.findByTransactionIdAndDeletedAtIsNull(id)
                    .map(transaction -> {
                        this.transactionMapper.update(transaction, dto);
                        this.transactionRepository.save(transaction);
                        return ResponseDto.<TransactionDto>builder()
                                .message("Transaction successful updated!")
                                .success(true)
                                .data(this.transactionMapper.toDto(transaction))
                                .build();
                    })
                    .orElse(ResponseDto.<TransactionDto>builder()
                            .message("Not found!")
                            .code(-1)
                            .build());
        } catch (Exception e) {
            return ResponseDto.<TransactionDto>builder()
                    .message("While updating error!")
                    .code(-3)
                    .build();
        }
    }

    @Override
    public ResponseDto<TransactionDto> delete(Integer id) {
        try {
            return this.transactionRepository.findByTransactionIdAndDeletedAtIsNull(id)
                    .map(transaction -> {
                        transaction.setDeletedAt(LocalDateTime.now());
                        this.transactionRepository.save(transaction);
                        return ResponseDto.<TransactionDto>builder()
                                .message("Transaction successful deleted!")
                                .success(true)
                                .data(this.transactionMapper.toDto(transaction))
                                .build();
                    })
                    .orElse(ResponseDto.<TransactionDto>builder()
                            .message("Not found!")
                            .code(-1)
                            .build());
        } catch (Exception e) {
            return ResponseDto.<TransactionDto>builder()
                    .message("While deleting error " + e.getMessage())
                    .code(-3)
                    .build();
        }
    }
}
