package com.example.Bank.Schema.junity;

import com.example.Bank.Schema.dto.AccountDto;
import com.example.Bank.Schema.dto.CustomerDto;
import com.example.Bank.Schema.dto.ResponseDto;
import com.example.Bank.Schema.entity.Account;
import com.example.Bank.Schema.entity.Customer;
import com.example.Bank.Schema.repository.AccountRepository;
import com.example.Bank.Schema.service.AccountService;
import com.example.Bank.Schema.service.mapper.AccountMapper;
import com.example.Bank.Schema.service.validate.AccountValidate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class TestAccountService {
    private AccountService accountService;
    @Mock
    private AccountMapper accountMapper;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private AccountValidate accountValidate;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void initMethod() {
        this.autoCloseable = MockitoAnnotations.openMocks(this);
        this.accountService = new AccountService(accountMapper, accountRepository, accountValidate);
    }

    @AfterEach
    void destroyMethod() throws Exception {
        autoCloseable.close();
    }

    @Test
    public void testCreateMethodPositive() {
        Mockito.when(this.accountMapper.toDto(Mockito.any()))
                .thenReturn(AccountDto.builder()
                        .accountId(1)
                        .balance(789.1)
                        .type("qwerty")
                        .branchId(1)
                        .enable(true)
                        .build());

        ResponseDto<AccountDto> response = this.accountService.create(AccountDto.builder()
                .accountId(1)
                .balance(789.1)
                .type("qwerty")
                .branchId(1)
                .enable(true)
                .build());

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getData());
        Assertions.assertEquals("Account successful created!", response.getMessage());

        Mockito.verify(this.accountRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(this.accountMapper, Mockito.times(1)).toEntity(Mockito.any());
        Mockito.verify(this.accountMapper, Mockito.times(1)).toDto(Mockito.any());
    }

    @Test
    public void testCreateMethodException() {
        Mockito.when(this.accountMapper.toDto(Mockito.any()))
                .thenThrow(RuntimeException.class);
        ResponseDto<AccountDto> response = this.accountService.create(AccountDto.builder()
                .accountId(1)
                .balance(789.1)
                .type("qwerty")
                .branchId(1)
                .enable(true)
                .build());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
    }

    @Test
    public void testGetMethodPositive() {
        Integer accountId = 1;
        Mockito.when(this.accountRepository.findByAccountIdAndDeletedAtIsNull(accountId))
                .thenReturn(Optional.of(Account.builder()
                        .accountId(1)
                        .balance(789.1)
                        .type("qwerty")
                        .branchId(1)
                        .enable(true)
                        .build()));
        Mockito.when(this.accountMapper.toDto(Mockito.any()))
                .thenReturn(AccountDto.builder()
                        .accountId(1)
                        .balance(789.1)
                        .type("qwerty")
                        .branchId(1)
                        .enable(true)
                        .build());
        ResponseDto<AccountDto> response = this.accountService.get(accountId);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("OK", response.getMessage());
        Assertions.assertNotNull(response.getData());

        Mockito.verify(this.accountRepository, Mockito.times(1)).findByAccountIdAndDeletedAtIsNull(accountId);
        Mockito.verify(this.accountMapper, Mockito.times(1)).toDto(Mockito.any());
    }

    @Test
    public void testGetMethodNegative() {
        Integer accountId = 1;
        Mockito.when(this.accountRepository.findByAccountIdAndDeletedAtIsNull(accountId))
                .thenReturn(Optional.empty());

        ResponseDto<AccountDto> response = this.accountService.get(accountId);
        Assertions.assertNull(response.getData());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Not found!", response.getMessage());

        Mockito.verify(this.accountRepository, Mockito.times(1)).findByAccountIdAndDeletedAtIsNull(accountId);
    }

    @Test
    public void testUpdateMethodPositive() {
        Integer accountId = 1;
        Mockito.when(this.accountRepository.findByAccountIdAndDeletedAtIsNull(accountId))
                .thenReturn(Optional.of(Account.builder()
                        .accountId(1)
                        .balance(789.1)
                        .type("qwerty")
                        .branchId(1)
                        .enable(true)
                        .build()));
        Mockito.when(this.accountMapper.toDto(Mockito.any()))
                .thenReturn(AccountDto.builder()
                        .accountId(1)
                        .balance(789.1)
                        .type("qwerty")
                        .branchId(1)
                        .enable(true)
                        .build());
        ResponseDto<AccountDto> response = this.accountService.update(accountId, AccountDto.builder()
                .accountId(1)
                .balance(789.1)
                .type("qwerty")
                .branchId(1)
                .enable(true)
                .build());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getData());
        Assertions.assertEquals("Account successful updated!", response.getMessage());

        Mockito.verify(this.accountRepository, Mockito.times(1)).findByAccountIdAndDeletedAtIsNull(accountId);
        Mockito.verify(this.accountRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(this.accountMapper, Mockito.times(1)).update(Mockito.any(), Mockito.any());
        Mockito.verify(this.accountMapper, Mockito.times(1)).toDto(Mockito.any());
    }

    @Test
    public void testUpdateMethodNegative() {
        Integer accountId = 1;
        Mockito.when(this.accountRepository.findByAccountIdAndDeletedAtIsNull(accountId))
                .thenReturn(Optional.empty());
        ResponseDto<AccountDto> response = this.accountService.update(accountId, AccountDto.builder()
                .accountId(1)
                .balance(789.1)
                .type("qwerty")
                .branchId(1)
                .enable(true)
                .build());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals("Not found!", response.getMessage());

        Mockito.verify(this.accountRepository, Mockito.times(1)).findByAccountIdAndDeletedAtIsNull(accountId);
    }

    @Test
    public void testUpdateMethodException() {
        Integer accountId = 1;
        Mockito.when(this.accountMapper.toDto(Mockito.any()))
                .thenThrow(RuntimeException.class);
        ResponseDto<AccountDto> response = this.accountService.update(accountId, AccountDto.builder()
                .accountId(1)
                .balance(789.1)
                .type("qwerty")
                .branchId(1)
                .enable(true)
                .build());
        Assertions.assertNull(response.getData());
        Assertions.assertFalse(response.isSuccess());

        Mockito.verify(this.accountRepository, Mockito.times(1)).findByAccountIdAndDeletedAtIsNull(accountId);
    }

    @Test
    public void testDeleteMethodPositive() {
        Integer accountId = 1;
        Mockito.when(this.accountRepository.findByAccountIdAndDeletedAtIsNull(accountId))
                .thenReturn(Optional.of(Account.builder()
                        .accountId(1)
                        .balance(789.1)
                        .type("qwerty")
                        .branchId(1)
                        .enable(true)
                        .build()));
        Mockito.when(this.accountMapper.toDto(Mockito.any()))
                .thenReturn(AccountDto.builder()
                        .accountId(1)
                        .balance(789.1)
                        .type("qwerty")
                        .branchId(1)
                        .enable(true)
                        .build());
        ResponseDto<AccountDto> response = this.accountService.delete(accountId);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("Account successful deleted!", response.getMessage());
        Assertions.assertNotNull(response.getData());

        Mockito.verify(this.accountRepository, Mockito.times(1)).findByAccountIdAndDeletedAtIsNull(accountId);
        Mockito.verify(this.accountMapper, Mockito.times(1)).toDto(Mockito.any());
    }

    @Test
    public void testDeleteMethodNegative() {
        Integer accountId = 1;
        Mockito.when(this.accountRepository.findByAccountIdAndDeletedAtIsNull(accountId))
                .thenReturn(Optional.empty());
        ResponseDto<AccountDto> response = this.accountService.delete(accountId);
        Assertions.assertNull(response.getData());
        Assertions.assertEquals("Not found!", response.getMessage());
        Assertions.assertFalse(response.isSuccess());

        Mockito.verify(this.accountRepository, Mockito.times(1)).findByAccountIdAndDeletedAtIsNull(accountId);
    }

    @Test
    public void testDeleteMethodException() {
        Integer accountId = 1;
        Mockito.when(this.accountMapper.toDto(Mockito.any()))
                .thenThrow(RuntimeException.class);
        ResponseDto<AccountDto> response = this.accountService.delete(accountId);
        Assertions.assertNull(response.getData());
        Assertions.assertNotNull(response.getMessage());
        Assertions.assertFalse(response.isSuccess());
        Mockito.verify(this.accountRepository, Mockito.times(1)).findByAccountIdAndDeletedAtIsNull(accountId);
    }
    @Test
    void testGetAll() {
        Map<String, String> params = new HashMap<>();
        params.put("page", "1");
        params.put("size", "10");

        Account account = new Account();

        Page<Account> page1 = new PageImpl<>(List.of(account), PageRequest.of(1, 10), 1);

        when(accountRepository.findByAccountsByPaginationSearch(any(), any(), any(), any(), any()))
                .thenReturn(page1);
        when(accountMapper.toDto(any())).thenReturn(AccountDto.builder()
                        .accountId(1)
                        .enable(true)
                        .type("qwerty")
                        .balance(789.0)
                .build());
        ResponseDto<Page<AccountDto>> response = accountService.getAll(params);

        assertTrue(response.isSuccess());

        assertEquals("Ok", response.getMessage());
        assertEquals(1, response.getData().getContent().size());
        Mockito.verify(this.accountRepository,times(1)).findByAccountsByPaginationSearch(any(), any(), any(), any(), any());
        Mockito.verify(this.accountMapper,times(1)).toDto(any());
    }
}

/*
    @Test
    public void testCreateMethodPositive() {
    }

    @Test
    public void testCreateMethodException() {
    }

    @Test
    public void testGetMethodPositive() {
    }

    @Test
    public void testGetMethodNegative() {
    }

    @Test
    public void testUpdateMethodPositive() {
    }

    @Test
    public void testUpdateMethodNegative() {
    }

    @Test
    public void testUpdateMethodException() {
    }

    @Test
    public void testDeleteMethodPositive() {
    }

    @Test
    public void testDeleteMethodNegative() {
    }

    @Test
    public void testDeleteMethodException() {
    }*/