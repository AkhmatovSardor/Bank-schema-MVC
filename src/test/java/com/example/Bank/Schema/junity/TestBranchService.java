package com.example.Bank.Schema.junity;

import com.example.Bank.Schema.dto.BranchDto;
import com.example.Bank.Schema.dto.ResponseDto;
import com.example.Bank.Schema.entity.Branch;
import com.example.Bank.Schema.repository.BranchRepository;
import com.example.Bank.Schema.service.BranchService;
import com.example.Bank.Schema.service.mapper.BranchMapper;
import com.example.Bank.Schema.service.validate.BranchValidate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class TestBranchService {
    private BranchService branchService;
    @Mock
    private BranchMapper branchMapper;
    @Mock
    private BranchRepository branchRepository;
    @Mock
    private BranchValidate branchValidate;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void initMethod() {
        this.autoCloseable = MockitoAnnotations.openMocks(this);
        this.branchService = new BranchService(branchMapper, branchRepository, branchValidate);
    }

    @AfterEach
    void destroyMethod() throws Exception {
        this.autoCloseable.close();
    }

    @Test
    public void testCreateMethodPositive() {
        Mockito.when(this.branchMapper.toDto(Mockito.any()))
                .thenReturn(BranchDto.builder()
                        .branchId(1)
                        .name("qwerty")
                        .address("qwerty")
                        .status(true)
                        .build());
        ResponseDto<BranchDto> response = this.branchService.create(BranchDto.builder()
                .branchId(1)
                .name("qwerty")
                .address("qwerty")
                .status(true)
                .build());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getData());
        Assertions.assertEquals("Branch successful created!", response.getMessage());

        Mockito.verify(this.branchRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(this.branchMapper, Mockito.times(1)).toEntity(Mockito.any());
        Mockito.verify(this.branchMapper, Mockito.times(1)).toDto(Mockito.any());
    }

    @Test
    public void testCreateMethodException() {
        Mockito.when(this.branchMapper.toDto(Mockito.any()))
                .thenThrow(RuntimeException.class);
        ResponseDto<BranchDto> response = this.branchService.create(BranchDto.builder()
                .branchId(1)
                .name("qwerty")
                .address("qwerty")
                .status(true)
                .build());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
    }

    @Test
    public void testGetMethodPositive() {
        Integer id = 1;
        Mockito.when(this.branchRepository.findByBranchIdAndDeletedAtIsNull(id))
                .thenReturn(Optional.of(Branch.builder()
                        .branchId(1)
                        .name("qwerty")
                        .address("qwerty")
                        .status(true)
                        .build()));
        Mockito.when(this.branchMapper.toDtoWithAll(Mockito.any()))
                .thenReturn(BranchDto.builder()
                        .branchId(1)
                        .name("qwerty")
                        .address("qwerty")
                        .status(true)
                        .build());
        ResponseDto<BranchDto> response = this.branchService.get(id);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("OK", response.getMessage());
        Assertions.assertNotNull(response.getData());

        Mockito.verify(this.branchRepository, Mockito.times(1)).findByBranchIdAndDeletedAtIsNull(id);
        Mockito.verify(this.branchMapper, Mockito.times(1)).toDtoWithAll(Mockito.any());
    }

    @Test
    public void testGetMethodNegative() {
        Integer id = 1;
        Mockito.when(this.branchRepository.findByBranchIdAndDeletedAtIsNull(id))
                .thenReturn(Optional.empty());
        ResponseDto<BranchDto> response = this.branchService.get(id);
        Assertions.assertNull(response.getData());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Not found!", response.getMessage());

        Mockito.verify(this.branchRepository, Mockito.times(1)).findByBranchIdAndDeletedAtIsNull(id);
    }

    @Test
    public void testUpdateMethodPositive() {
        Integer id = 1;
        Mockito.when(this.branchRepository.findByBranchIdAndDeletedAtIsNull(id))
                .thenReturn(Optional.of(Branch.builder()
                        .branchId(1)
                        .name("qwerty")
                        .address("qwerty")
                        .status(true)
                        .build()));
        Mockito.when(this.branchMapper.toDto(Mockito.any()))
                .thenReturn(BranchDto.builder()
                        .branchId(1)
                        .name("qwerty")
                        .address("qwerty")
                        .status(true)
                        .build());
        ResponseDto<BranchDto> response = this.branchService.update(id, BranchDto.builder()
                .branchId(1)
                .name("qwerty")
                .address("qwerty")
                .status(true)
                .build());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getData());
        Assertions.assertEquals("Brunch successful updated!", response.getMessage());

        Mockito.verify(this.branchRepository, Mockito.times(1)).findByBranchIdAndDeletedAtIsNull(id);
        Mockito.verify(this.branchRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(this.branchMapper, Mockito.times(1)).update(Mockito.any(), Mockito.any());
        Mockito.verify(this.branchMapper, Mockito.times(1)).toDto(Mockito.any());
    }

    @Test
    public void testUpdateMethodNegative() {
        Integer id = 1;
        Mockito.when(this.branchRepository.findByBranchIdAndDeletedAtIsNull(id))
                .thenReturn(Optional.empty());
        ResponseDto<BranchDto> response = this.branchService.update(id, BranchDto.builder()
                .branchId(1)
                .name("qwerty")
                .address("qwerty")
                .status(true)
                .build());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals("Not found!", response.getMessage());

        Mockito.verify(this.branchRepository, Mockito.times(1)).findByBranchIdAndDeletedAtIsNull(id);
    }

    @Test
    public void testUpdateMethodException() {
        Integer id = 1;
        Mockito.when(this.branchRepository.findByBranchIdAndDeletedAtIsNull(id))
                .thenThrow(RuntimeException.class);
        ResponseDto<BranchDto> response = this.branchService.update(id, BranchDto.builder()
                .branchId(1)
                .name("qwerty")
                .address("qwerty")
                .status(true)
                .build());
        Assertions.assertNull(response.getData());
        Assertions.assertFalse(response.isSuccess());

        Mockito.verify(this.branchRepository, Mockito.times(1)).findByBranchIdAndDeletedAtIsNull(id);
    }

    @Test
    public void testDeleteMethodPositive() {
        Integer id = 1;
        Mockito.when(this.branchRepository.findByBranchIdAndDeletedAtIsNull(id))
                .thenReturn(Optional.of(Branch.builder()
                        .branchId(1)
                        .name("qwerty")
                        .address("qwerty")
                        .status(true)
                        .build()));
        Mockito.when(this.branchMapper.toDto(Mockito.any()))
                .thenReturn(BranchDto.builder()
                        .branchId(1)
                        .name("qwerty")
                        .address("qwerty")
                        .status(true)
                        .build());
        ResponseDto<BranchDto> response = this.branchService.delete(id);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("Brunch successful deleted!", response.getMessage());
        Assertions.assertNotNull(response.getData());

        Mockito.verify(this.branchRepository, Mockito.times(1)).findByBranchIdAndDeletedAtIsNull(id);
        Mockito.verify(this.branchMapper, Mockito.times(1)).toDto(Mockito.any());

    }

    @Test
    public void testDeleteMethodNegative() {
        Integer id = 1;
        Mockito.when(this.branchRepository.findByBranchIdAndDeletedAtIsNull(id))
                .thenReturn(Optional.empty());
        ResponseDto<BranchDto> response = this.branchService.delete(id);
        Assertions.assertNull(response.getData());
        Assertions.assertEquals("Not found!", response.getMessage());
        Assertions.assertFalse(response.isSuccess());

        Mockito.verify(this.branchRepository, Mockito.times(1)).findByBranchIdAndDeletedAtIsNull(id);
    }

    @Test
    public void testDeleteMethodException() {
        Integer id = 1;
        Mockito.when(this.branchRepository.findByBranchIdAndDeletedAtIsNull(id))
                .thenThrow(RuntimeException.class);
        ResponseDto<BranchDto> response = this.branchService.delete(id);
        Assertions.assertNull(response.getData());
        Assertions.assertNotNull(response.getMessage());
        Assertions.assertFalse(response.isSuccess());
        Mockito.verify(this.branchRepository, Mockito.times(1)).findByBranchIdAndDeletedAtIsNull(id);
    }
}