package com.example.Bank.Schema.service;

import com.example.Bank.Schema.dto.ErrorDto;
import com.example.Bank.Schema.dto.PaymentDto;
import com.example.Bank.Schema.dto.ResponseDto;
import com.example.Bank.Schema.repository.PaymentRepository;
import com.example.Bank.Schema.service.mapper.PaymentMapper;
import com.example.Bank.Schema.service.validate.PaymentValidate;
import com.example.Bank.Schema.util.SimpleCrud;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService implements SimpleCrud<Integer, PaymentDto> {
    private final PaymentMapper paymentMapper;
    private final PaymentRepository paymentRepository;
    private final PaymentValidate paymentValidate;

    @Override
    public ResponseDto<PaymentDto> create(PaymentDto dto) {
        List<ErrorDto> errors = this.paymentValidate.validate(dto);
        if (!errors.isEmpty()) {
            return ResponseDto.<PaymentDto>builder()
                    .message("Validate error!")
                    .code(-2)
                    .errors(errors)
                    .build();
        }
        try {
            return ResponseDto.<PaymentDto>builder()
                    .data(this.paymentMapper.toDto(this.paymentRepository.save(this.paymentMapper.toEntity(dto))))
                    .message("Payment successful created!")
                    .success(true)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<PaymentDto>builder()
                    .message("While saving error " + e.getMessage())
                    .code(-3)
                    .build();
        }
    }

    @Override
    public ResponseDto<PaymentDto> get(Integer id) {
        return this.paymentRepository.findByPaymentIdAndDeletedAtIsNull(id)
                .map(payment -> ResponseDto.<PaymentDto>builder()
                        .message("OK")
                        .success(true)
                        .data(this.paymentMapper.toDto(payment))
                        .build())
                .orElse(ResponseDto.<PaymentDto>builder()
                        .message("Not found!")
                        .code(-1)
                        .build());
    }

    @Override
    public ResponseDto<PaymentDto> update(Integer id, PaymentDto dto) {
        try {
            return this.paymentRepository.findByPaymentIdAndDeletedAtIsNull(id)
                    .map(payment -> {
                        this.paymentMapper.update(payment, dto);
                        this.paymentRepository.save(payment);
                        return ResponseDto.<PaymentDto>builder()
                                .message("Payment successful updated!")
                                .success(true)
                                .data(this.paymentMapper.toDto(payment))
                                .build();
                    })
                    .orElse(ResponseDto.<PaymentDto>builder()
                            .message("Not found!")
                            .code(-1)
                            .build());
        } catch (Exception e) {
            return ResponseDto.<PaymentDto>builder()
                    .message("While deleting error " + e.getMessage())
                    .code(-3)
                    .build();
        }
    }

    @Override
    public ResponseDto<PaymentDto> delete(Integer id) {
        try {
            return this.paymentRepository.findByPaymentIdAndDeletedAtIsNull(id)
                    .map(payment -> {
                        payment.setDeletedAt(LocalDateTime.now());
                        this.paymentRepository.save(payment);
                        return ResponseDto.<PaymentDto>builder()
                                .message("Payment successful deleted!")
                                .success(true)
                                .data(this.paymentMapper.toDto(payment))
                                .build();

                    })
                    .orElse(ResponseDto.<PaymentDto>builder()
                            .message("Not found!")
                            .code(-1)
                            .build());
        } catch (Exception e) {
            return ResponseDto.<PaymentDto>builder()
                    .code(-3)
                    .message("While deleting error " + e.getMessage())
                    .build();

        }


    }
}
