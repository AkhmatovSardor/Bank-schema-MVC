package com.example.Bank.Schema.service;

import com.example.Bank.Schema.dto.BorrowerDto;
import com.example.Bank.Schema.dto.ErrorDto;
import com.example.Bank.Schema.dto.ResponseDto;
import com.example.Bank.Schema.repository.BorrowerRepository;
import com.example.Bank.Schema.service.mapper.BorrowerMapper;
import com.example.Bank.Schema.service.validate.BorrowerValidate;
import com.example.Bank.Schema.util.SimpleCrud;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BorrowerService implements SimpleCrud<Integer, BorrowerDto> {
    private final BorrowerRepository borrowerRepository;
    private final BorrowerMapper borrowerMapper;
    private final BorrowerValidate borrowerValidate;

    @Override
    public ResponseDto<BorrowerDto> create(BorrowerDto dto) {
        List<ErrorDto> errors = this.borrowerValidate.validate(dto);
        if (!errors.isEmpty()) {
            return ResponseDto.<BorrowerDto>builder()
                    .message("Validate error!")
                    .code(-2)
                    .errors(errors)
                    .build();
        }
        try {
            return ResponseDto.<BorrowerDto>builder()
                    .message("Borrower successful created!")
                    .success(true)
                    .data(this.borrowerMapper.toDto(this.borrowerRepository.save(this.borrowerMapper.toEntity(dto))))
                    .build();
        } catch (Exception e) {
            return ResponseDto.<BorrowerDto>builder()
                    .message("While saving error!")
                    .code(-3)
                    .build();
        }
    }

    @Override
    public ResponseDto<BorrowerDto> get(Integer id) {
        return this.borrowerRepository.findByBorrowIdAndDeletedAtIsNull(id)
                .map(borrower -> ResponseDto.<BorrowerDto>builder()
                        .message("OK")
                        .success(true)
                        .data(this.borrowerMapper.toDtoWithLoan(borrower))
                        .build())
                .orElse(ResponseDto.<BorrowerDto>builder()
                        .message("Not found!")
                        .code(-1)
                        .build());
    }

    @Override
    public ResponseDto<BorrowerDto> update(Integer id, BorrowerDto dto) {
        try {
            return this.borrowerRepository.findByBorrowIdAndDeletedAtIsNull(id)
                    .map(borrower -> {
                        this.borrowerMapper.update(borrower, dto);
                        this.borrowerRepository.save(borrower);
                        return ResponseDto.<BorrowerDto>builder()
                                .message("Borrower successful updated!")
                                .success(true)
                                .data(this.borrowerMapper.toDto(borrower))
                                .build();
                    })
                    .orElse(ResponseDto.<BorrowerDto>builder()
                            .message("Not found!")
                            .code(-1)
                            .build());
        } catch (Exception e) {
            return ResponseDto.<BorrowerDto>builder()
                    .message("While updating error")
                    .code(-3)
                    .build();
        }
    }

    @Override
    public ResponseDto<BorrowerDto> delete(Integer id) {
        try {
            return this.borrowerRepository.findByBorrowIdAndDeletedAtIsNull(id)
                    .map(borrower -> {
                        borrower.setDeletedAt(LocalDateTime.now());
                        this.borrowerRepository.save(borrower);
                        return ResponseDto.<BorrowerDto>builder()
                                .message("Borrower successful deleted!")
                                .success(true)
                                .data(this.borrowerMapper.toDto(borrower))
                                .build();
                    }).orElse(ResponseDto.<BorrowerDto>builder()
                            .message("Not found!")
                            .code(-1)
                            .build());
        } catch (Exception e) {
            return ResponseDto.<BorrowerDto>builder()
                    .message("While deleting error")
                    .code(-3)
                    .build();
        }
    }
}
