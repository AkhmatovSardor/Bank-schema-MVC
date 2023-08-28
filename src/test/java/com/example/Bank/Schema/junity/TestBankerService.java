package com.example.Bank.Schema.junity;

import com.example.Bank.Schema.dto.BankerDto;
import com.example.Bank.Schema.dto.ResponseDto;
import com.example.Bank.Schema.entity.Banker;
import com.example.Bank.Schema.repository.BankerRepository;
import com.example.Bank.Schema.service.BankerService;
import com.example.Bank.Schema.service.mapper.BankerMapper;
import com.example.Bank.Schema.service.validate.BankerValidate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class TestBankerService {
    private BankerService bankerService;
    @Mock
    private BankerMapper bankerMapper;
    @Mock
    private BankerRepository bankerRepository;
    @Mock
    private BankerValidate bankerValidate;
    private AutoCloseable autoCloseable;
    @BeforeEach
    void initMethod(){
        this.autoCloseable= MockitoAnnotations.openMocks(this);
        this.bankerService=new BankerService(bankerRepository,bankerMapper,bankerValidate);
    }
    @AfterEach
    void destroyMethod() throws Exception{
        this.autoCloseable.close();
    }

    @Test
    public void testCreateMethodPositive() {
        Mockito.when(this.bankerMapper.toDto(Mockito.any()))
                .thenReturn(BankerDto.builder()
                        .bankerId(1)
                        .status(true)
                        .branchId(1)
                        .bankerName("qwerty")
                        .build());
        ResponseDto<BankerDto> response=this.bankerService.create(BankerDto.builder()
                .bankerId(1)
                .status(true)
                .branchId(1)
                .bankerName("qwerty")
                .build());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getData());
        Assertions.assertEquals("Banker successful created!", response.getMessage());

        Mockito.verify(this.bankerRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(this.bankerMapper, Mockito.times(1)).toEntity(Mockito.any());
        Mockito.verify(this.bankerMapper, Mockito.times(1)).toDto(Mockito.any());

    }

    @Test
    public void testCreateMethodException() {
        Mockito.when(this.bankerMapper.toDto(Mockito.any()))
                .thenThrow(RuntimeException.class);
        ResponseDto<BankerDto> response=this.bankerService.create(BankerDto.builder()
                .bankerId(1)
                .status(true)
                .branchId(1)
                .bankerName("qwerty")
                .build());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
    }

    @Test
    public void testGetMethodPositive() {
        Integer id=1;
        Mockito.when(this.bankerRepository.findByBankerIdAndDeletedAtIsNull(id))
                .thenReturn(Optional.of(Banker.builder()
                        .bankerId(1)
                        .status(true)
                        .branchId(1)
                        .bankerName("qwerty")
                        .build()));
        Mockito.when(this.bankerMapper.toDto(Mockito.any()))
                .thenReturn(BankerDto.builder()
                        .bankerId(1)
                        .status(true)
                        .branchId(1)
                        .bankerName("qwerty")
                        .build());
        ResponseDto<BankerDto> response=this.bankerService.get(id);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("OK", response.getMessage());
        Assertions.assertNotNull(response.getData());

        Mockito.verify(this.bankerRepository, Mockito.times(1)).findByBankerIdAndDeletedAtIsNull(id);
        Mockito.verify(this.bankerMapper, Mockito.times(1)).toDto(Mockito.any());
    }

    @Test
    public void testGetMethodNegative() {
        Integer id=1;
        Mockito.when(this.bankerRepository.findByBankerIdAndDeletedAtIsNull(id))
                .thenReturn(Optional.empty());
        ResponseDto<BankerDto> response=this.bankerService.get(id);
        Assertions.assertNull(response.getData());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Not found!", response.getMessage());

        Mockito.verify(this.bankerRepository, Mockito.times(1)).findByBankerIdAndDeletedAtIsNull(id);
    }

    @Test
    public void testUpdateMethodPositive() {
        Integer id=1;
        Mockito.when(this.bankerRepository.findByBankerIdAndDeletedAtIsNull(id))
                .thenReturn(Optional.of(Banker.builder()
                        .bankerId(1)
                        .status(true)
                        .branchId(1)
                        .bankerName("qwerty")
                        .build()));
        Mockito.when(this.bankerMapper.toDto(Mockito.any()))
                .thenReturn(BankerDto.builder()
                        .bankerId(1)
                        .status(true)
                        .branchId(1)
                        .bankerName("qwerty")
                        .build());
        ResponseDto<BankerDto> response=this.bankerService.update(id,BankerDto.builder()
                .bankerId(1)
                .status(true)
                .branchId(1)
                .bankerName("qwerty")
                .build());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getData());
        Assertions.assertEquals("Banker successful updated!", response.getMessage());

        Mockito.verify(this.bankerRepository, Mockito.times(1)).findByBankerIdAndDeletedAtIsNull(id);
        Mockito.verify(this.bankerRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(this.bankerMapper, Mockito.times(1)).update(Mockito.any(), Mockito.any());
        Mockito.verify(this.bankerMapper, Mockito.times(1)).toDto(Mockito.any());

    }

    @Test
    public void testUpdateMethodNegative() {
        Integer id=1;
Mockito.when(this.bankerRepository.findByBankerIdAndDeletedAtIsNull(id))
        .thenReturn(Optional.empty());
        ResponseDto<BankerDto> response=this.bankerService.update(id,BankerDto.builder()
                .bankerId(1)
                .status(true)
                .branchId(1)
                .bankerName("qwerty")
                .build());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals("Not found!", response.getMessage());

        Mockito.verify(this.bankerRepository, Mockito.times(1)).findByBankerIdAndDeletedAtIsNull(id);
    }

    @Test
    public void testUpdateMethodException() {
Integer id=1;
Mockito.when(this.bankerRepository.findByBankerIdAndDeletedAtIsNull(id))
        .thenThrow(RuntimeException.class);
        ResponseDto<BankerDto> response=this.bankerService.update(id,BankerDto.builder()
                .bankerId(1)
                .status(true)
                .branchId(1)
                .bankerName("qwerty")
                .build());
        Assertions.assertNull(response.getData());
        Assertions.assertFalse(response.isSuccess());

        Mockito.verify(this.bankerRepository, Mockito.times(1)).findByBankerIdAndDeletedAtIsNull(id);
    }

    @Test
    public void testDeleteMethodPositive() {
        Integer id=1;
        Mockito.when(this.bankerRepository.findByBankerIdAndDeletedAtIsNull(id))
                .thenReturn(Optional.of(Banker.builder()
                        .bankerId(1)
                        .status(true)
                        .branchId(1)
                        .bankerName("qwerty")
                        .build()));
        Mockito.when(this.bankerMapper.toDto(Mockito.any()))
                .thenReturn(BankerDto.builder()
                        .bankerId(1)
                        .status(true)
                        .branchId(1)
                        .bankerName("qwerty")
                        .build());
        ResponseDto<BankerDto> response=this.bankerService.delete(id);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("Banker successful deleted!", response.getMessage());
        Assertions.assertNotNull(response.getData());

        Mockito.verify(this.bankerRepository, Mockito.times(1)).findByBankerIdAndDeletedAtIsNull(id);
        Mockito.verify(this.bankerMapper, Mockito.times(1)).toDto(Mockito.any());
    }

    @Test
    public void testDeleteMethodNegative() {
        Integer id=1;
        Mockito.when(this.bankerRepository.findByBankerIdAndDeletedAtIsNull(id))
                .thenReturn(Optional.empty());
        ResponseDto<BankerDto> response=this.bankerService.delete(id);
        Assertions.assertNull(response.getData());
        Assertions.assertEquals("Not found!", response.getMessage());
        Assertions.assertFalse(response.isSuccess());

        Mockito.verify(this.bankerRepository, Mockito.times(1)).findByBankerIdAndDeletedAtIsNull(id);
    }

    @Test
    public void testDeleteMethodException() {
        Integer id=1;
        Mockito.when(this.bankerRepository.findByBankerIdAndDeletedAtIsNull(id))
                .thenThrow(RuntimeException.class);
        ResponseDto<BankerDto> response=this.bankerService.delete(id);
        Assertions.assertNull(response.getData());
        Assertions.assertNotNull(response.getMessage());
        Assertions.assertFalse(response.isSuccess());
        Mockito.verify(this.bankerRepository, Mockito.times(1)).findByBankerIdAndDeletedAtIsNull(id);
    }
}