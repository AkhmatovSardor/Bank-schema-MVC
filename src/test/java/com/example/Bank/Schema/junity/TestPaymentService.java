package com.example.Bank.Schema.junity;

import com.example.Bank.Schema.dto.PaymentDto;
import com.example.Bank.Schema.dto.ResponseDto;
import com.example.Bank.Schema.entity.Payment;
import com.example.Bank.Schema.repository.PaymentRepository;
import com.example.Bank.Schema.service.PaymentService;
import com.example.Bank.Schema.service.mapper.PaymentMapper;
import com.example.Bank.Schema.service.validate.PaymentValidate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class TestPaymentService {
    private PaymentService paymentService;
    @Mock
    private PaymentMapper paymentMapper;
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private PaymentValidate paymentValidate;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void initMethod() {
        this.autoCloseable = MockitoAnnotations.openMocks(this);
        this.paymentService = new PaymentService(paymentMapper, paymentRepository, paymentValidate);
    }

    @AfterEach
    void destroyMethod() throws Exception {
        this.autoCloseable.close();
    }

    @Test
    public void testCreateMethodPositive() {
        Mockito.when(this.paymentMapper.toDto(Mockito.any()))
                .thenReturn(PaymentDto.builder()
                        .paymentId(1)
                        .loanId(1)
                        .amount(789.0)
                        .description("qwerty")
                        .status(true)
                        .build());
        ResponseDto<PaymentDto> response = this.paymentService.create(PaymentDto.builder()
                .paymentId(1)
                .loanId(1)
                .amount(789.0)
                .description("qwerty")
                .status(true)
                .build());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getData());
        Assertions.assertEquals("Payment successful created!", response.getMessage());

        Mockito.verify(this.paymentRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(this.paymentMapper, Mockito.times(1)).toEntity(Mockito.any());
        Mockito.verify(this.paymentMapper, Mockito.times(1)).toDto(Mockito.any());
    }

    @Test
    public void testCreateMethodException() {
        Mockito.when(this.paymentMapper.toDto(Mockito.any()))
                .thenThrow(RuntimeException.class);
        ResponseDto<PaymentDto> response = this.paymentService.create(PaymentDto.builder()
                .paymentId(1)
                .loanId(1)
                .amount(789.0)
                .description("qwerty")
                .status(true)
                .build());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
    }

    @Test
    public void testGetMethodPositive() {
        Integer id = 1;
        Mockito.when(this.paymentRepository.findByPaymentIdAndDeletedAtIsNull(id))
                .thenReturn(Optional.of(Payment.builder()
                        .paymentId(1)
                        .loanId(1)
                        .amount(789.0)
                        .description("qwerty")
                        .status(true)
                        .build()));
        Mockito.when(this.paymentMapper.toDto(Mockito.any()))
                .thenReturn(PaymentDto.builder()
                        .paymentId(1)
                        .loanId(1)
                        .amount(789.0)
                        .description("qwerty")
                        .status(true)
                        .build());
        ResponseDto<PaymentDto> response = this.paymentService.get(id);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("OK", response.getMessage());
        Assertions.assertNotNull(response.getData());

        Mockito.verify(this.paymentRepository, Mockito.times(1)).findByPaymentIdAndDeletedAtIsNull(id);
        Mockito.verify(this.paymentMapper, Mockito.times(1)).toDto(Mockito.any());
    }

    @Test
    public void testGetMethodNegative() {
        Integer id = 1;
        Mockito.when(this.paymentRepository.findByPaymentIdAndDeletedAtIsNull(id))
                .thenReturn(Optional.empty());
        ResponseDto<PaymentDto> response = this.paymentService.get(id);
        Assertions.assertNull(response.getData());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Not found!", response.getMessage());

        Mockito.verify(this.paymentRepository, Mockito.times(1)).findByPaymentIdAndDeletedAtIsNull(id);
    }

    @Test
    public void testUpdateMethodPositive() {
        Integer id = 1;
        Mockito.when(this.paymentRepository.findByPaymentIdAndDeletedAtIsNull(id))
                .thenReturn(Optional.of(Payment.builder()
                        .paymentId(1)
                        .loanId(1)
                        .amount(789.0)
                        .description("qwerty")
                        .status(true)
                        .build()));
        Mockito.when(this.paymentMapper.toDto(Mockito.any()))
                .thenReturn(PaymentDto.builder()
                        .paymentId(1)
                        .loanId(1)
                        .amount(789.0)
                        .description("qwerty")
                        .status(true)
                        .build());
        ResponseDto<PaymentDto> response = this.paymentService.update(id, PaymentDto.builder()
                .paymentId(1)
                .loanId(1)
                .amount(789.0)
                .description("qwerty")
                .status(true)
                .build());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getData());
        Assertions.assertEquals("Payment successful updated!", response.getMessage());

        Mockito.verify(this.paymentRepository, Mockito.times(1)).findByPaymentIdAndDeletedAtIsNull(id);
        Mockito.verify(this.paymentRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(this.paymentMapper, Mockito.times(1)).update(Mockito.any(), Mockito.any());
        Mockito.verify(this.paymentMapper, Mockito.times(1)).toDto(Mockito.any());
    }

    @Test
    public void testUpdateMethodNegative() {
        Integer id = 1;
        Mockito.when(this.paymentRepository.findByPaymentIdAndDeletedAtIsNull(id))
                .thenReturn(Optional.empty());
        ResponseDto<PaymentDto> response = this.paymentService.update(id, PaymentDto.builder()
                .paymentId(1)
                .loanId(1)
                .amount(789.0)
                .description("qwerty")
                .status(true)
                .build());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals("Not found!", response.getMessage());

        Mockito.verify(this.paymentRepository, Mockito.times(1)).findByPaymentIdAndDeletedAtIsNull(id);
    }

    @Test
    public void testUpdateMethodException() {
        Integer id = 1;
        Mockito.when(this.paymentMapper.toDto(Mockito.any()))
                .thenThrow(RuntimeException.class);
        ResponseDto<PaymentDto> response = this.paymentService.update(id, PaymentDto.builder()
                .paymentId(1)
                .loanId(1)
                .amount(789.0)
                .description("qwerty")
                .status(true)
                .build());
        Assertions.assertNull(response.getData());
        Assertions.assertFalse(response.isSuccess());

        Mockito.verify(this.paymentRepository, Mockito.times(1)).findByPaymentIdAndDeletedAtIsNull(id);
    }

    @Test
    public void testDeleteMethodPositive() {
        Integer id = 1;
        Mockito.when(this.paymentRepository.findByPaymentIdAndDeletedAtIsNull(id))
                .thenReturn(Optional.of(Payment.builder()
                        .paymentId(1)
                        .loanId(1)
                        .amount(789.0)
                        .description("qwerty")
                        .status(true)
                        .build()));
        Mockito.when(this.paymentMapper.toDto(Mockito.any()))
                .thenReturn(PaymentDto.builder()
                        .paymentId(1)
                        .loanId(1)
                        .amount(789.0)
                        .description("qwerty")
                        .status(true)
                        .build());
        ResponseDto<PaymentDto> response = this.paymentService.delete(id);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("Payment successful deleted!", response.getMessage());
        Assertions.assertNotNull(response.getData());

        Mockito.verify(this.paymentRepository, Mockito.times(1)).findByPaymentIdAndDeletedAtIsNull(id);
        Mockito.verify(this.paymentMapper, Mockito.times(1)).toDto(Mockito.any());
    }

    @Test
    public void testDeleteMethodNegative() {
        Integer id = 1;
        Mockito.when(this.paymentRepository.findByPaymentIdAndDeletedAtIsNull(id))
                .thenReturn(Optional.empty());
        ResponseDto<PaymentDto> response = this.paymentService.delete(id);
        Assertions.assertNull(response.getData());
        Assertions.assertEquals("Not found!", response.getMessage());
        Assertions.assertFalse(response.isSuccess());

        Mockito.verify(this.paymentRepository, Mockito.times(1)).findByPaymentIdAndDeletedAtIsNull(id);
    }

    @Test
    public void testDeleteMethodException() {
        Integer id = 1;
        Mockito.when(this.paymentMapper.toDto(Mockito.any()))
                .thenThrow(RuntimeException.class);
        ResponseDto<PaymentDto> response = this.paymentService.delete(id);
        Assertions.assertNull(response.getData());
        Assertions.assertNotNull(response.getMessage());
        Assertions.assertFalse(response.isSuccess());
        Mockito.verify(this.paymentRepository, Mockito.times(1)).findByPaymentIdAndDeletedAtIsNull(id);
    }
}
