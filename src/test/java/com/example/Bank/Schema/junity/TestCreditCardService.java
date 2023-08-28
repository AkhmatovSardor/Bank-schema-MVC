package com.example.Bank.Schema.junity;

import com.example.Bank.Schema.dto.CreditCardDto;
import com.example.Bank.Schema.dto.ResponseDto;
import com.example.Bank.Schema.entity.CreditCard;
import com.example.Bank.Schema.repository.CreditCardRepository;
import com.example.Bank.Schema.service.CreditCardService;
import com.example.Bank.Schema.service.mapper.CreditCardMapper;
import com.example.Bank.Schema.service.validate.CreditCardValidate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class TestCreditCardService {
    private CreditCardService creditCardService;
    @Mock
    private CreditCardMapper creditCardMapper;
    @Mock
    private CreditCardRepository creditCardRepository;
    @Mock
    private CreditCardValidate creditCardValidate;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void initMethod() {
        this.autoCloseable = MockitoAnnotations.openMocks(this);
        this.creditCardService = new CreditCardService(creditCardMapper, creditCardRepository, creditCardValidate);
    }

    @AfterEach
    void destroyMethod() throws Exception {
        this.autoCloseable.close();
    }

    @Test
    public void testCreateMethodPositive() {
        Mockito.when(this.creditCardMapper.toDto(Mockito.any()))
                .thenReturn(CreditCardDto.builder()
                        .creditCardId(1)
                        .name("qwerty")
                        .number(124566464546L)
                        .amount(123.0)
                        .customerId(1)
                        .build());
        ResponseDto<CreditCardDto> response = this.creditCardService.create(CreditCardDto.builder()
                .creditCardId(1)
                .name("qwerty")
                .number(124566464546L)
                .amount(123.0)
                .customerId(1)
                .build());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getData());
        Assertions.assertEquals("Credit card successful created!", response.getMessage());

        Mockito.verify(this.creditCardRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(this.creditCardMapper, Mockito.times(1)).toEntity(Mockito.any());
        Mockito.verify(this.creditCardMapper, Mockito.times(1)).toDto(Mockito.any());
    }

    @Test
    public void testCreateMethodException() {
        Mockito.when(this.creditCardMapper.toDto(Mockito.any()))
                .thenThrow(RuntimeException.class);
        ResponseDto<CreditCardDto> response = this.creditCardService.create(CreditCardDto.builder()
                .creditCardId(1)
                .name("qwerty")
                .number(124566464546L)
                .amount(123.0)
                .customerId(1)
                .build());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
    }

    @Test
    public void testGetMethodPositive() {
        Integer id = 1;
        Mockito.when(this.creditCardRepository.findByCreditCardIdAndDeletedAtIsNull(id))
                .thenReturn(Optional.of(CreditCard.builder()
                        .creditCardId(1)
                        .name("qwerty")
                        .number(124566464546L)
                        .amount(123.0)
                        .customerId(1)
                        .build()));
        Mockito.when(this.creditCardMapper.toDto(Mockito.any()))
                .thenReturn(CreditCardDto.builder()
                        .creditCardId(1)
                        .name("qwerty")
                        .number(124566464546L)
                        .amount(123.0)
                        .customerId(1)
                        .build());
        ResponseDto<CreditCardDto> response = this.creditCardService.get(id);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("OK", response.getMessage());
        Assertions.assertNotNull(response.getData());

        Mockito.verify(this.creditCardRepository, Mockito.times(1)).findByCreditCardIdAndDeletedAtIsNull(id);
        Mockito.verify(this.creditCardMapper, Mockito.times(1)).toDto(Mockito.any());
    }

    @Test
    public void testGetMethodNegative() {
        Integer id = 1;
        Mockito.when(this.creditCardRepository.findByCreditCardIdAndDeletedAtIsNull(id))
                .thenReturn(Optional.empty());
        ResponseDto<CreditCardDto> response = this.creditCardService.get(id);
        Assertions.assertNull(response.getData());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Not found!", response.getMessage());

        Mockito.verify(this.creditCardRepository, Mockito.times(1)).findByCreditCardIdAndDeletedAtIsNull(id);
    }

    @Test
    public void testUpdateMethodPositive() {
        Integer id = 1;
        Mockito.when(this.creditCardRepository.findByCreditCardIdAndDeletedAtIsNull(id))
                .thenReturn(Optional.of(CreditCard.builder()
                        .creditCardId(1)
                        .name("qwerty")
                        .number(124566464546L)
                        .amount(123.0)
                        .customerId(1)
                        .build()));
        Mockito.when(this.creditCardMapper.toDto(Mockito.any()))
                .thenReturn(CreditCardDto.builder()
                        .creditCardId(1)
                        .name("qwerty")
                        .number(124566464546L)
                        .amount(123.0)
                        .customerId(1)
                        .build());
        ResponseDto<CreditCardDto> response = this.creditCardService.update(id, CreditCardDto.builder()
                .creditCardId(1)
                .name("qwerty")
                .number(124566464546L)
                .amount(123.0)
                .customerId(1)
                .build());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getData());
        Assertions.assertEquals("Credit card successful updated!", response.getMessage());

        Mockito.verify(this.creditCardRepository, Mockito.times(1)).findByCreditCardIdAndDeletedAtIsNull(id);
        Mockito.verify(this.creditCardRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(this.creditCardMapper, Mockito.times(1)).update(Mockito.any(), Mockito.any());
        Mockito.verify(this.creditCardMapper, Mockito.times(1)).toDto(Mockito.any());
    }

    @Test
    public void testUpdateMethodNegative() {
        Integer id = 1;
        Mockito.when(this.creditCardRepository.findByCreditCardIdAndDeletedAtIsNull(id))
                .thenReturn(Optional.empty());
        ResponseDto<CreditCardDto> response = this.creditCardService.update(id, CreditCardDto.builder()
                .creditCardId(1)
                .name("qwerty")
                .number(124566464546L)
                .amount(123.0)
                .customerId(1)
                .build());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals("Not found!", response.getMessage());

        Mockito.verify(this.creditCardRepository, Mockito.times(1)).findByCreditCardIdAndDeletedAtIsNull(id);
    }

    @Test
    public void testUpdateMethodException() {
        Integer id = 1;
        Mockito.when(this.creditCardRepository.findByCreditCardIdAndDeletedAtIsNull(id))
                .thenThrow(RuntimeException.class);
        ResponseDto<CreditCardDto> response = this.creditCardService.update(id, CreditCardDto.builder()
                .creditCardId(1)
                .name("qwerty")
                .number(124566464546L)
                .amount(123.0)
                .customerId(1)
                .build());
        Assertions.assertNull(response.getData());
        Assertions.assertFalse(response.isSuccess());

        Mockito.verify(this.creditCardRepository, Mockito.times(1)).findByCreditCardIdAndDeletedAtIsNull(id);
    }

    @Test
    public void testDeleteMethodPositive() {
        Integer id = 1;
        Mockito.when(this.creditCardRepository.findByCreditCardIdAndDeletedAtIsNull(id))
                .thenReturn(Optional.of(CreditCard.builder()
                        .creditCardId(1)
                        .name("qwerty")
                        .number(124566464546L)
                        .amount(123.0)
                        .customerId(1)
                        .build()));
        Mockito.when(this.creditCardMapper.toDto(Mockito.any()))
                .thenReturn(CreditCardDto.builder()
                        .creditCardId(1)
                        .name("qwerty")
                        .number(124566464546L)
                        .amount(123.0)
                        .customerId(1)
                        .build());
        ResponseDto<CreditCardDto> response = this.creditCardService.delete(id);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("Credit card successful deleted!", response.getMessage());
        Assertions.assertNotNull(response.getData());

        Mockito.verify(this.creditCardRepository, Mockito.times(1)).findByCreditCardIdAndDeletedAtIsNull(id);
        Mockito.verify(this.creditCardMapper, Mockito.times(1)).toDto(Mockito.any());
    }

    @Test
    public void testDeleteMethodNegative() {
        Integer id = 1;
        Mockito.when(this.creditCardRepository.findByCreditCardIdAndDeletedAtIsNull(id))
                .thenReturn(Optional.empty());
        ResponseDto<CreditCardDto> response = this.creditCardService.delete(id);
        Assertions.assertNull(response.getData());
        Assertions.assertEquals("Not found!", response.getMessage());
        Assertions.assertFalse(response.isSuccess());

        Mockito.verify(this.creditCardRepository, Mockito.times(1)).findByCreditCardIdAndDeletedAtIsNull(id);

    }

    @Test
    public void testDeleteMethodException() {
        Integer id = 1;
        Mockito.when(this.creditCardRepository.findByCreditCardIdAndDeletedAtIsNull(id))
                .thenThrow(RuntimeException.class);
        ResponseDto<CreditCardDto> response = this.creditCardService.delete(id);
        Assertions.assertNull(response.getData());
        Assertions.assertNotNull(response.getMessage());
        Assertions.assertFalse(response.isSuccess());
        Mockito.verify(this.creditCardRepository, Mockito.times(1)).findByCreditCardIdAndDeletedAtIsNull(id);
    }
}
