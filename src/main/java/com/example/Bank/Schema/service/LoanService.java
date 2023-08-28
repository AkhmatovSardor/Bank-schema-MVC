package com.example.Bank.Schema.service;

import com.example.Bank.Schema.dto.ErrorDto;
import com.example.Bank.Schema.dto.LoanDto;
import com.example.Bank.Schema.dto.ResponseDto;
import com.example.Bank.Schema.repository.LoanRepository;
import com.example.Bank.Schema.service.mapper.LoanMapper;
import com.example.Bank.Schema.service.validate.LoanValidate;
import com.example.Bank.Schema.util.SimpleCrud;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService implements SimpleCrud<Integer, LoanDto> {
    private final LoanMapper loanMapper;
    private final LoanRepository loanRepository;
    private final LoanValidate loanValidate;

    @Override
    public ResponseDto<LoanDto> create(LoanDto dto) {
        List<ErrorDto> errors = this.loanValidate.validate(dto);
        if (!errors.isEmpty()) {
            return ResponseDto.<LoanDto>builder()
                    .message("Validate error!")
                    .code(-2)
                    .errors(errors)
                    .build();
        }
        try {
            return ResponseDto.<LoanDto>builder()
                    .data(this.loanMapper.toDto(this.loanRepository.save(this.loanMapper.toEntity(dto))))
                    .message("Loan successful created!")
                    .success(true)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<LoanDto>builder()
                    .message("While saving error " + e.getMessage())
                    .code(-3)
                    .build();
        }
    }

    @Override
    public ResponseDto<LoanDto> get(Integer id) {
        return this.loanRepository.findByLoanIdAndDeletedAtIsNull(id)
                .map(loan -> ResponseDto.<LoanDto>builder()
                        .message("OK")
                        .success(true)
                        .data(this.loanMapper.toDtoWithAll(loan))
                        .build())
                .orElse(ResponseDto.<LoanDto>builder()
                        .message("Not found!")
                        .code(-1)
                        .build());
    }

    @Override
    public ResponseDto<LoanDto> update(Integer id, LoanDto dto) {
        try {
            return this.loanRepository.findByLoanIdAndDeletedAtIsNull(id)
                    .map(loan -> {
                        this.loanMapper.update(loan, dto);
                        this.loanRepository.save(loan);
                        return ResponseDto.<LoanDto>builder()
                                .success(true)
                                .message("Loan successful updated!")
                                .data(this.loanMapper.toDto(loan))
                                .build();
                    })
                    .orElse(ResponseDto.<LoanDto>builder()
                            .message("Not found!")
                            .code(-1)
                            .build());
        } catch (Exception e) {
            return ResponseDto.<LoanDto>builder()
                    .message("While updating error " + e.getMessage())
                    .code(-3)
                    .build();
        }
    }

    @Override
    public ResponseDto<LoanDto> delete(Integer id) {
        try {
            return this.loanRepository.findByLoanIdAndDeletedAtIsNull(id)
                    .map(loan -> {
                        loan.setDeletedAt(LocalDateTime.now());
                        this.loanRepository.save(loan);
                        return ResponseDto.<LoanDto>builder()
                                .message("Loan successful deleted!")
                                .success(true)
                                .data(this.loanMapper.toDto(loan))
                                .build();
                    })
                    .orElse(ResponseDto.<LoanDto>builder()
                            .message("Not found!")
                            .code(-1)
                            .build());
        } catch (Exception e) {
            return ResponseDto.<LoanDto>builder()
                    .message("While deleting error!")
                    .code(-3)
                    .build();
        }
    }
}
