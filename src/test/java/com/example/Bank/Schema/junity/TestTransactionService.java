package com.example.Bank.Schema.junity;

import com.example.Bank.Schema.dto.ResponseDto;
import com.example.Bank.Schema.dto.TransactionDto;
import com.example.Bank.Schema.entity.Transaction;
import com.example.Bank.Schema.repository.TransactionRepository;
import com.example.Bank.Schema.service.TransactionService;
import com.example.Bank.Schema.service.mapper.TransactionMapper;
import com.example.Bank.Schema.service.validate.TransactionValidate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class TestTransactionService {
    private TransactionService transactionService;
    @Mock
    private TransactionMapper transactionMapper;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private TransactionValidate transactionValidate;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void initMethod() {
        this.autoCloseable = MockitoAnnotations.openMocks(this);
        this.transactionService = new TransactionService(transactionMapper, transactionRepository, transactionValidate);
    }

    @AfterEach
    void destroyMethod() throws Exception {
        this.autoCloseable.close();
    }

    @Test
    public void testCreateMethodPositive() {
        Mockito.when(this.transactionMapper.toDto(Mockito.any()))
                .thenReturn(TransactionDto.builder()
                        .transactionId(1)
                        .customerId(1)
                        .amount(789.0)
                        .status(true)
                        .build());
        ResponseDto<TransactionDto> response = this.transactionService.create(TransactionDto.builder()
                .transactionId(1)
                .customerId(1)
                .amount(789.0)
                .status(true)
                .build());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getData());
        Assertions.assertEquals("Transaction successful created!", response.getMessage());

        Mockito.verify(this.transactionRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(this.transactionMapper, Mockito.times(1)).toEntity(Mockito.any());
        Mockito.verify(this.transactionMapper, Mockito.times(1)).toDto(Mockito.any());
    }

    @Test
    public void testCreateMethodException() {
        Mockito.when(this.transactionMapper.toDto(Mockito.any()))
                .thenThrow(RuntimeException.class);
        ResponseDto<TransactionDto> response = this.transactionService.create(TransactionDto.builder()
                .transactionId(1)
                .customerId(1)
                .amount(789.0)
                .status(true)
                .build());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
    }

    @Test
    public void testGetMethodPositive() {
        Integer id = 1;
        Mockito.when(this.transactionRepository.findByTransactionIdAndDeletedAtIsNull(id))
                .thenReturn(Optional.of(Transaction.builder()
                        .transactionId(1)
                        .customerId(1)
                        .amount(789.0)
                        .status(true)
                        .build()));
        Mockito.when(this.transactionMapper.toDtoWithAccount(Mockito.any()))
                .thenReturn(TransactionDto.builder()
                        .transactionId(1)
                        .customerId(1)
                        .amount(789.0)
                        .status(true)
                        .build());
        ResponseDto<TransactionDto> response = this.transactionService.get(id);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("OK", response.getMessage());
        Assertions.assertNotNull(response.getData());

        Mockito.verify(this.transactionRepository, Mockito.times(1)).findByTransactionIdAndDeletedAtIsNull(id);
        Mockito.verify(this.transactionMapper, Mockito.times(1)).toDtoWithAccount(Mockito.any());
    }

    @Test
    public void testGetMethodNegative() {
        Integer id = 1;
        Mockito.when(this.transactionRepository.findByTransactionIdAndDeletedAtIsNull(id))
                .thenReturn(Optional.empty());
        ResponseDto<TransactionDto> response = this.transactionService.get(id);
        Assertions.assertNull(response.getData());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Not found!", response.getMessage());

        Mockito.verify(this.transactionRepository, Mockito.times(1)).findByTransactionIdAndDeletedAtIsNull(id);
    }

    @Test
    public void testUpdateMethodPositive() {
        Integer id = 1;
        Mockito.when(this.transactionRepository.findByTransactionIdAndDeletedAtIsNull(id))
                .thenReturn(Optional.of(Transaction.builder()
                        .transactionId(1).customerId(1)
                        .amount(789.0)
                        .status(true)
                        .build()));
        Mockito.when(this.transactionMapper.toDto(Mockito.any()))
                .thenReturn(TransactionDto.builder()
                        .transactionId(1)
                        .customerId(1)
                        .amount(789.0)
                        .status(true)
                        .build());
        ResponseDto<TransactionDto> response = this.transactionService.update(id, TransactionDto.builder()
                .transactionId(1)
                .customerId(1)
                .amount(789.0)
                .status(true)
                .build());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getData());
        Assertions.assertEquals("Transaction successful updated!", response.getMessage());

        Mockito.verify(this.transactionRepository, Mockito.times(1)).findByTransactionIdAndDeletedAtIsNull(id);
        Mockito.verify(this.transactionRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(this.transactionMapper, Mockito.times(1)).update(Mockito.any(), Mockito.any());
        Mockito.verify(this.transactionMapper, Mockito.times(1)).toDto(Mockito.any());

    }

    @Test
    public void testUpdateMethodNegative() {
        Integer id = 1;
        Mockito.when(this.transactionRepository.findByTransactionIdAndDeletedAtIsNull(id))
                .thenReturn(Optional.empty());
        ResponseDto<TransactionDto> response = this.transactionService.update(id, TransactionDto.builder()
                .transactionId(1)
                .customerId(1)
                .amount(789.0)
                .status(true)
                .build());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals("Not found!", response.getMessage());

        Mockito.verify(this.transactionRepository, Mockito.times(1)).findByTransactionIdAndDeletedAtIsNull(id);
    }

    @Test
    public void testUpdateMethodException() {
        Integer id = 1;
        Mockito.when(this.transactionMapper.toDto(Mockito.any()))
                .thenThrow(RuntimeException.class);
        ResponseDto<TransactionDto> response = this.transactionService.delete(id);
        Assertions.assertNull(response.getData());
        Assertions.assertFalse(response.isSuccess());

        Mockito.verify(this.transactionRepository, Mockito.times(1)).findByTransactionIdAndDeletedAtIsNull(id);
    }

    @Test
    public void testDeleteMethodPositive() {
        Integer id = 1;
        Mockito.when(this.transactionRepository.findByTransactionIdAndDeletedAtIsNull(id))
                .thenReturn(Optional.of(Transaction.builder()
                        .transactionId(1)
                        .customerId(1)
                        .amount(789.0)
                        .status(true)
                        .build()));
        Mockito.when(this.transactionMapper.toDtoWithAccount(Mockito.any()))
                .thenReturn(TransactionDto.builder()
                        .transactionId(1)
                        .customerId(1)
                        .amount(789.0)
                        .status(true)
                        .build());
        ResponseDto<TransactionDto> response = this.transactionService.delete(id);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("Transaction successful deleted!", response.getMessage());

        Mockito.verify(this.transactionRepository, Mockito.times(1)).findByTransactionIdAndDeletedAtIsNull(id);
        Mockito.verify(this.transactionMapper, Mockito.times(1)).toDto(Mockito.any());
    }

    @Test
    public void testDeleteMethodNegative() {
        Integer id = 1;
        Mockito.when(this.transactionRepository.findByTransactionIdAndDeletedAtIsNull(id))
                .thenReturn(Optional.empty());
        ResponseDto<TransactionDto> response = this.transactionService.delete(id);
        Assertions.assertNull(response.getData());
        Assertions.assertEquals("Not found!", response.getMessage());
        Assertions.assertFalse(response.isSuccess());

        Mockito.verify(this.transactionRepository, Mockito.times(1)).findByTransactionIdAndDeletedAtIsNull(id);
    }

    @Test
    public void testDeleteMethodException() {
        Integer id = 1;
        Mockito.when(this.transactionMapper.toDto(Mockito.any()))
                .thenThrow(RuntimeException.class);
        ResponseDto<TransactionDto> response = this.transactionService.delete(id);
        Assertions.assertNull(response.getData());
        Assertions.assertNotNull(response.getMessage());
        Assertions.assertFalse(response.isSuccess());
        Mockito.verify(this.transactionRepository, Mockito.times(1)).findByTransactionIdAndDeletedAtIsNull(id);
    }
}
