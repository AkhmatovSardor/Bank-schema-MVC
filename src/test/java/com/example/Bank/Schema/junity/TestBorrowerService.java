package com.example.Bank.Schema.junity;

import com.example.Bank.Schema.dto.BorrowerDto;
import com.example.Bank.Schema.dto.ResponseDto;
import com.example.Bank.Schema.entity.Borrower;
import com.example.Bank.Schema.repository.BorrowerRepository;
import com.example.Bank.Schema.service.BorrowerService;
import com.example.Bank.Schema.service.mapper.BorrowerMapper;
import com.example.Bank.Schema.service.validate.BorrowerValidate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class TestBorrowerService {
    private BorrowerService borrowerService;
    @Mock
    private BorrowerMapper borrowerMapper;
    @Mock
    private BorrowerRepository borrowerRepository;
    @Mock
    private BorrowerValidate borrowerValidate;
    private AutoCloseable autoCloseable;
    @BeforeEach
    void initMethod(){
        this.autoCloseable= MockitoAnnotations.openMocks(this);
        this.borrowerService=new BorrowerService(borrowerRepository,borrowerMapper,borrowerValidate);
    }
    @AfterEach
    void destroyMethod() throws Exception{
        this.autoCloseable.close();
    }

    @Test
    public void testCreateMethodPositive() {
        Mockito.when(this.borrowerMapper.toDto(Mockito.any()))
                .thenReturn(BorrowerDto.builder()
                        .borrowId(1)
                        .build());

        ResponseDto<BorrowerDto> response = this.borrowerService.create(BorrowerDto.builder().build());

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getData());
        Assertions.assertEquals("Borrower successful created!", response.getMessage());

        Mockito.verify(this.borrowerRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(this.borrowerMapper, Mockito.times(1)).toEntity(Mockito.any());
        Mockito.verify(this.borrowerMapper, Mockito.times(1)).toDto(Mockito.any());

    }

    @Test
    public void testCreateMethodException() {
        Mockito.when(this.borrowerMapper.toDto(Mockito.any()))
                .thenThrow(RuntimeException.class);
        ResponseDto<BorrowerDto> response = this.borrowerService.create(BorrowerDto.builder()
                .borrowId(1)
                .build());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
    }

    @Test
    public void testGetMethodPositive() {
        Integer accountId = 1;
        Mockito.when(this.borrowerRepository.findByBorrowIdAndDeletedAtIsNull(accountId))
                .thenReturn(Optional.of(Borrower.builder()
                        .borrowId(1)
                        .build()));
        Mockito.when(this.borrowerMapper.toDtoWithLoan(Mockito.any()))
                .thenReturn(BorrowerDto.builder()
                        .borrowId(1)
                        .build());
        ResponseDto<BorrowerDto> response = this.borrowerService.get(accountId);


        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("OK", response.getMessage());
        Assertions.assertNotNull(response.getData());

        Mockito.verify(this.borrowerRepository, Mockito.times(1)).findByBorrowIdAndDeletedAtIsNull(accountId);
        Mockito.verify(this.borrowerMapper, Mockito.times(1)).toDtoWithLoan(Mockito.any());
    }

    @Test
    public void testGetMethodNegative() {
        Integer accountId = 1;
        Mockito.when(this.borrowerRepository.findByBorrowIdAndDeletedAtIsNull(accountId))
                .thenReturn(Optional.empty());

        ResponseDto<BorrowerDto> response = this.borrowerService.get(accountId);
        Assertions.assertNull(response.getData());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Not found!", response.getMessage());

        Mockito.verify(this.borrowerRepository, Mockito.times(1)).findByBorrowIdAndDeletedAtIsNull(accountId);
    }

    @Test
    public void testUpdateMethodPositive() {
        Integer accountId = 1;
        Mockito.when(this.borrowerRepository.findByBorrowIdAndDeletedAtIsNull(accountId))
                .thenReturn(Optional.of(Borrower.builder()
                        .borrowId(1)
                        .build()));
        Mockito.when(this.borrowerMapper.toDtoWithLoan(Mockito.any()))
                .thenReturn(BorrowerDto.builder()
                        .borrowId(1)
                        .build());
        ResponseDto<BorrowerDto> response = this.borrowerService.update(accountId, BorrowerDto.builder()
                .borrowId(1)
                .build());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("Borrower successful updated!", response.getMessage());

        Mockito.verify(this.borrowerRepository, Mockito.times(1)).findByBorrowIdAndDeletedAtIsNull(accountId);
        Mockito.verify(this.borrowerRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(this.borrowerMapper, Mockito.times(1)).update(Mockito.any(), Mockito.any());
        Mockito.verify(this.borrowerMapper, Mockito.times(1)).toDto(Mockito.any());

    }

    @Test
    public void testUpdateMethodNegative() {
        Integer accountId = 1;
        Mockito.when(this.borrowerRepository.findByBorrowIdAndDeletedAtIsNull(accountId))
                .thenReturn(Optional.empty());
        ResponseDto<BorrowerDto> response = this.borrowerService.update(accountId, BorrowerDto.builder()
                .borrowId(1)
                .build());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals("Not found!", response.getMessage());

        Mockito.verify(this.borrowerRepository, Mockito.times(1)).findByBorrowIdAndDeletedAtIsNull(accountId);
    }

    @Test
    public void testUpdateMethodException() {
        Integer accountId = 1;
        Mockito.when(this.borrowerMapper.toDto(Mockito.any()))
                .thenThrow(RuntimeException.class);
        ResponseDto<BorrowerDto> response = this.borrowerService.update(accountId, BorrowerDto.builder()
                .borrowId(1)
                .build());
        Assertions.assertNull(response.getData());
        Assertions.assertFalse(response.isSuccess());

        Mockito.verify(this.borrowerRepository, Mockito.times(1)).findByBorrowIdAndDeletedAtIsNull(accountId);
    }

    @Test
    public void testDeleteMethodPositive() {
        Integer accountId = 1;
        Mockito.when(this.borrowerRepository.findByBorrowIdAndDeletedAtIsNull(accountId))
                .thenReturn(Optional.of(Borrower.builder()
                        .borrowId(1)
                        .build()));
        Mockito.when(this.borrowerMapper.toDtoWithLoan(Mockito.any()))
                .thenReturn(BorrowerDto.builder()
                        .borrowId(1)
                        .build());
        ResponseDto<BorrowerDto> response = this.borrowerService.delete(accountId);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("Borrower successful deleted!", response.getMessage());

        Mockito.verify(this.borrowerRepository, Mockito.times(1)).findByBorrowIdAndDeletedAtIsNull(accountId);
        Mockito.verify(this.borrowerMapper, Mockito.times(1)).toDto(Mockito.any());
    }

    @Test
    public void testDeleteMethodNegative() {
        Integer accountId = 1;
        Mockito.when(this.borrowerRepository.findByBorrowIdAndDeletedAtIsNull(accountId))
                .thenReturn(Optional.empty());
        ResponseDto<BorrowerDto> response = this.borrowerService.delete(accountId);
        Assertions.assertNull(response.getData());
        Assertions.assertEquals("Not found!", response.getMessage());
        Assertions.assertFalse(response.isSuccess());

        Mockito.verify(this.borrowerRepository, Mockito.times(1)).findByBorrowIdAndDeletedAtIsNull(accountId);
    }

    @Test
    public void testDeleteMethodException() {
        Integer accountId = 1;
        Mockito.when(this.borrowerMapper.toDto(Mockito.any()))
                .thenThrow(RuntimeException.class);
        ResponseDto<BorrowerDto> response = this.borrowerService.delete(accountId);
        Assertions.assertNull(response.getData());
        Assertions.assertNotNull(response.getMessage());
        Assertions.assertFalse(response.isSuccess());
        Mockito.verify(this.borrowerRepository, Mockito.times(1)).findByBorrowIdAndDeletedAtIsNull(accountId);
    }
}
