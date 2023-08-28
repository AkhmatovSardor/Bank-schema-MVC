package com.example.Bank.Schema.junity;

import com.example.Bank.Schema.dto.LoanDto;
import com.example.Bank.Schema.dto.ResponseDto;
import com.example.Bank.Schema.entity.Loan;
import com.example.Bank.Schema.repository.LoanRepository;
import com.example.Bank.Schema.service.LoanService;
import com.example.Bank.Schema.service.mapper.LoanMapper;
import com.example.Bank.Schema.service.validate.LoanValidate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class TestLoanService {
    private LoanService loanService;
    @Mock
    private LoanMapper loanMapper;
    @Mock
    private LoanRepository loanRepository;
    @Mock
    private LoanValidate loanValidate;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void initMethod() {
        this.autoCloseable = MockitoAnnotations.openMocks(this);
        this.loanService = new LoanService(loanMapper, loanRepository, loanValidate);
    }

    @AfterEach
    void destroyMethod() throws Exception {
        this.autoCloseable.close();
    }

    @Test
    public void testCreateMethodPositive() {
        Mockito.when(this.loanMapper.toDto(Mockito.any()))
                .thenReturn(LoanDto.builder()
                        .loanId(1)
                        .goal("qwerty")
                        .issuedAmount(1.0)
                        .remainingAmount(1.0)
                        .branchId(1)
                        .accountId(1)
                        .status(true)
                        .build());
        ResponseDto<LoanDto> response = this.loanService.create(LoanDto.builder()
                .loanId(1)
                .goal("qwerty")
                .issuedAmount(1.0)
                .remainingAmount(1.0)
                .branchId(1)
                .accountId(1)
                .status(true)
                .build());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getData());
        Assertions.assertEquals("Loan successful created!", response.getMessage());

        Mockito.verify(this.loanRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(this.loanMapper, Mockito.times(1)).toEntity(Mockito.any());
        Mockito.verify(this.loanMapper, Mockito.times(1)).toDto(Mockito.any());
    }

    @Test
    public void testCreateMethodException() {
        Mockito.when(this.loanMapper.toDto(Mockito.any()))
                .thenThrow(RuntimeException.class);
        ResponseDto<LoanDto> response = this.loanService.create(LoanDto.builder()
                .loanId(1)
                .goal("qwerty")
                .issuedAmount(1.0)
                .remainingAmount(1.0)
                .branchId(1)
                .accountId(1)
                .status(true)
                .build());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
    }

    @Test
    public void testGetMethodPositive() {
        Integer id = 1;
        Mockito.when(this.loanRepository.findByLoanIdAndDeletedAtIsNull(id))
                .thenReturn(Optional.of(Loan.builder()
                        .loanId(1)
                        .goal("qwerty")
                        .issuedAmount(1.0)
                        .remainingAmount(1.0)
                        .branchId(1)
                        .accountId(1)
                        .status(true)
                        .build()));
        Mockito.when(this.loanMapper.toDtoWithAll(Mockito.any()))
                .thenReturn(LoanDto.builder()
                        .loanId(1)
                        .goal("qwerty")
                        .issuedAmount(1.0)
                        .remainingAmount(1.0)
                        .branchId(1)
                        .accountId(1)
                        .status(true)
                        .build());
        ResponseDto<LoanDto> response = this.loanService.get(id);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("OK", response.getMessage());
        Assertions.assertNotNull(response.getData());

        Mockito.verify(this.loanRepository, Mockito.times(1)).findByLoanIdAndDeletedAtIsNull(id);
        Mockito.verify(this.loanMapper, Mockito.times(1)).toDtoWithAll(Mockito.any());
    }

    @Test
    public void testGetMethodNegative() {
        Integer id = 1;
        Mockito.when(this.loanRepository.findByLoanIdAndDeletedAtIsNull(id))
                .thenReturn(Optional.empty());
        ResponseDto<LoanDto> response = this.loanService.get(id);
        Assertions.assertNull(response.getData());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Not found!", response.getMessage());

        Mockito.verify(this.loanRepository, Mockito.times(1)).findByLoanIdAndDeletedAtIsNull(id);
    }

    @Test
    public void testUpdateMethodPositive() {
        Integer id = 1;
        Mockito.when(this.loanRepository.findByLoanIdAndDeletedAtIsNull(id))
                .thenReturn(Optional.of(Loan.builder()
                        .loanId(1)
                        .goal("qwerty")
                        .issuedAmount(1.0)
                        .remainingAmount(1.0)
                        .branchId(1)
                        .accountId(1)
                        .status(true)
                        .build()));
        Mockito.when(this.loanMapper.toDto(Mockito.any()))
                .thenReturn(LoanDto.builder()
                        .loanId(1)
                        .goal("qwerty")
                        .issuedAmount(1.0)
                        .remainingAmount(1.0)
                        .branchId(1)
                        .accountId(1)
                        .status(true)
                        .build());
        ResponseDto<LoanDto> response = this.loanService.update(id, LoanDto.builder()
                .loanId(1)
                .goal("qwerty")
                .issuedAmount(1.0)
                .remainingAmount(1.0)
                .branchId(1)
                .accountId(1)
                .status(true)
                .build());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getData());
        Assertions.assertEquals("Loan successful updated!", response.getMessage());

        Mockito.verify(this.loanRepository, Mockito.times(1)).findByLoanIdAndDeletedAtIsNull(id);
        Mockito.verify(this.loanRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(this.loanMapper, Mockito.times(1)).update(Mockito.any(), Mockito.any());
        Mockito.verify(this.loanMapper, Mockito.times(1)).toDto(Mockito.any());
    }

    @Test
    public void testUpdateMethodNegative() {
        Integer id = 1;
        Mockito.when(this.loanRepository.findByLoanIdAndDeletedAtIsNull(id))
                .thenReturn(Optional.empty());
        ResponseDto<LoanDto> response = this.loanService.update(id, LoanDto.builder()
                .loanId(1)
                .goal("qwerty")
                .issuedAmount(1.0)
                .remainingAmount(1.0)
                .branchId(1)
                .accountId(1)
                .status(true)
                .build());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals("Not found!", response.getMessage());

        Mockito.verify(this.loanRepository, Mockito.times(1)).findByLoanIdAndDeletedAtIsNull(id);
    }

    @Test
    public void testUpdateMethodException() {
        Integer id = 1;
        Mockito.when(this.loanMapper.toDto(Mockito.any()))
                .thenThrow(RuntimeException.class);
        ResponseDto<LoanDto> response = this.loanService.update(id, LoanDto.builder()
                .loanId(1)
                .goal("qwerty")
                .issuedAmount(1.0)
                .remainingAmount(1.0)
                .branchId(1)
                .accountId(1)
                .status(true)
                .build());
        Assertions.assertNull(response.getData());
        Assertions.assertFalse(response.isSuccess());

        Mockito.verify(this.loanRepository, Mockito.times(1)).findByLoanIdAndDeletedAtIsNull(id);
    }

    @Test
    public void testDeleteMethodPositive() {
        Integer id = 1;
        Mockito.when(this.loanRepository.findByLoanIdAndDeletedAtIsNull(id))
                .thenReturn(Optional.of(Loan.builder()
                        .loanId(1)
                        .goal("qwerty")
                        .issuedAmount(1.0)
                        .remainingAmount(1.0)
                        .branchId(1)
                        .accountId(1)
                        .status(true)
                        .build()));
        Mockito.when(this.loanMapper.toDto(Mockito.any()))
                .thenReturn(LoanDto.builder()
                        .loanId(1)
                        .goal("qwerty")
                        .issuedAmount(1.0)
                        .remainingAmount(1.0)
                        .branchId(1)
                        .accountId(1)
                        .status(true)
                        .build());
        ResponseDto<LoanDto> response = this.loanService.delete(id);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("Loan successful deleted!", response.getMessage());
        Assertions.assertNotNull(response.getData());

        Mockito.verify(this.loanRepository, Mockito.times(1)).findByLoanIdAndDeletedAtIsNull(id);
        Mockito.verify(this.loanMapper, Mockito.times(1)).toDto(Mockito.any());
    }

    @Test
    public void testDeleteMethodNegative() {
        Integer id = 1;
        Mockito.when(this.loanRepository.findByLoanIdAndDeletedAtIsNull(id))
                .thenReturn(Optional.empty());
        ResponseDto<LoanDto> response = this.loanService.delete(id);
        Assertions.assertNull(response.getData());
        Assertions.assertEquals("Not found!", response.getMessage());
        Assertions.assertFalse(response.isSuccess());

        Mockito.verify(this.loanRepository, Mockito.times(1)).findByLoanIdAndDeletedAtIsNull(id);
    }

    @Test
    public void testDeleteMethodException() {
        Integer id = 1;
        Mockito.when(this.loanMapper.toDto(Mockito.any()))
                .thenThrow(RuntimeException.class);
        ResponseDto<LoanDto> response = this.loanService.delete(id);
        Assertions.assertNull(response.getData());
        Assertions.assertNotNull(response.getMessage());
        Assertions.assertFalse(response.isSuccess());
        Mockito.verify(this.loanRepository, Mockito.times(1)).findByLoanIdAndDeletedAtIsNull(id);
    }
}
