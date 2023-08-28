package com.example.Bank.Schema.junity;

import com.example.Bank.Schema.dto.CustomerDto;
import com.example.Bank.Schema.dto.ResponseDto;
import com.example.Bank.Schema.entity.Customer;
import com.example.Bank.Schema.repository.CustomerRepository;
import com.example.Bank.Schema.repository.RefreshTokenRepository;
import com.example.Bank.Schema.repository.UserSessionRepository;
import com.example.Bank.Schema.security.JwtUtil;
import com.example.Bank.Schema.service.CustomerService;
import com.example.Bank.Schema.service.mapper.CustomerMapper;
import com.example.Bank.Schema.service.validate.CustomerValidate;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class TestCustomerService {
    private CustomerService customerService;
    @Mock
    private CustomerMapper customerMapper;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CustomerValidate customerValidate;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserSessionRepository userSessionRepository;
    @Mock
    private RefreshTokenRepository refreshTokenRepository;
    @Mock
    private JwtUtil jwtUtil;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void initMethod() {
        this.autoCloseable = MockitoAnnotations.openMocks(this);
        this.customerService = new CustomerService(customerMapper, customerRepository, customerValidate, passwordEncoder, userSessionRepository, refreshTokenRepository, jwtUtil);
    }

    @AfterEach
    void destroyMethod() throws Exception {
        this.autoCloseable.close();
    }

    @Test
    public void testCreateMethodPositive() {
        when(this.customerMapper.toDto(any()))
                .thenReturn(CustomerDto.builder()
                        .customerId(1)
                        .firstName("qwerty")
                        .lastName("qwerty")
                        .address("qwerty")
                        .age(1)
                        .email("qwerty")
                        .password("qwerty")
                        .build());
        ResponseDto<CustomerDto> response = this.customerService.create(CustomerDto.builder()
                .customerId(1)
                .firstName("qwerty")
                .lastName("qwerty")
                .address("qwerty")
                .age(1)
                .email("qwerty")
                .password("qwerty")
                .build());
        assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getData());
        assertEquals("Customer successful created!", response.getMessage());

        Mockito.verify(this.customerRepository, Mockito.times(1)).save(any());
        Mockito.verify(this.customerMapper, Mockito.times(1)).toEntity(any());
        Mockito.verify(this.customerMapper, Mockito.times(1)).toDto(any());
    }

    @Test
    public void testCreateMethodException() {
        when(this.customerMapper.toDto(any()))
                .thenThrow(RuntimeException.class);
        ResponseDto<CustomerDto> response = this.customerService.create(CustomerDto.builder()
                .customerId(1)
                .firstName("qwerty")
                .lastName("qwerty")
                .address("qwerty")
                .age(1)
                .email("qwerty")
                .password("qwerty")
                .build());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
    }

    @Test
    public void testGetMethodPositive() {
        Integer id = 1;
        when(this.customerRepository.findByCustomerIdAndDeletedAtIsNull(id))
                .thenReturn(Optional.of(Customer.builder()
                        .customerId(1)
                        .firstName("qwerty")
                        .lastName("qwerty")
                        .address("qwerty")
                        .age(1)
                        .email("qwerty")
                        .password("qwerty")
                        .build()));
        when(this.customerMapper.toDtoWithAll(any()))
                .thenReturn(CustomerDto.builder()
                        .customerId(1)
                        .firstName("qwerty")
                        .lastName("qwerty")
                        .address("qwerty")
                        .age(1)
                        .email("qwerty")
                        .password("qwerty")
                        .build());
        ResponseDto<CustomerDto> response = this.customerService.get(id);
        assertTrue(response.isSuccess());
        assertEquals("OK", response.getMessage());
        Assertions.assertNotNull(response.getData());

        Mockito.verify(this.customerRepository, Mockito.times(1)).findByCustomerIdAndDeletedAtIsNull(id);
        Mockito.verify(this.customerMapper, Mockito.times(1)).toDtoWithAll(any());
    }

    @Test
    public void testGetMethodNegative() {
        Integer id = 1;
        when(this.customerRepository.findByCustomerIdAndDeletedAtIsNull(id))
                .thenReturn(Optional.empty());
        ResponseDto<CustomerDto> response = this.customerService.get(id);
        Assertions.assertNull(response.getData());
        Assertions.assertFalse(response.isSuccess());
        assertEquals("Not found!", response.getMessage());

        Mockito.verify(this.customerRepository, Mockito.times(1)).findByCustomerIdAndDeletedAtIsNull(id);
    }

    @Test
    public void testUpdateMethodPositive() {
        Integer id = 1;
        when(this.customerRepository.findByCustomerIdAndDeletedAtIsNull(id))
                .thenReturn(Optional.of(Customer.builder()
                        .customerId(1)
                        .firstName("qwerty")
                        .lastName("qwerty")
                        .address("qwerty")
                        .age(1)
                        .email("qwerty")
                        .password("qwerty")
                        .build()));
        when(this.customerMapper.toDto(any()))
                .thenReturn(CustomerDto.builder()
                        .customerId(1)
                        .firstName("qwerty")
                        .lastName("qwerty")
                        .address("qwerty")
                        .age(1)
                        .email("qwerty")
                        .password("qwerty")
                        .build());
        ResponseDto<CustomerDto> response = this.customerService.update(id, CustomerDto.builder()
                .customerId(1)
                .firstName("qwerty")
                .lastName("qwerty")
                .address("qwerty")
                .age(1)
                .email("qwerty")
                .password("qwerty")
                .build());
        assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getData());
        assertEquals("Customer successful updated!", response.getMessage());

        Mockito.verify(this.customerRepository, Mockito.times(1)).findByCustomerIdAndDeletedAtIsNull(id);
        Mockito.verify(this.customerRepository, Mockito.times(1)).save(any());
        Mockito.verify(this.customerMapper, Mockito.times(1)).update(any(), any());
        Mockito.verify(this.customerMapper, Mockito.times(1)).toDto(any());
    }

    @Test
    public void testUpdateMethodNegative() {
        Integer id = 1;
        when(this.customerRepository.findByCustomerIdAndDeletedAtIsNull(id))
                .thenReturn(Optional.empty());
        ResponseDto<CustomerDto> response = this.customerService.delete(id);
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        assertEquals("Not found!", response.getMessage());

        Mockito.verify(this.customerRepository, Mockito.times(1)).findByCustomerIdAndDeletedAtIsNull(id);
    }

    @Test
    public void testUpdateMethodException() {
        Integer id = 1;
        when(this.customerMapper.toDto(any()))
                .thenThrow(RuntimeException.class);
        ResponseDto<CustomerDto> response = this.customerService.delete(id);

        Assertions.assertNull(response.getData());
        Assertions.assertFalse(response.isSuccess());

        Mockito.verify(this.customerRepository, Mockito.times(1)).findByCustomerIdAndDeletedAtIsNull(id);
    }

    @Test
    public void testDeleteMethodPositive() {
        Integer id = 1;
        when(this.customerRepository.findByCustomerIdAndDeletedAtIsNull(id))
                .thenReturn(Optional.of(Customer.builder()
                        .customerId(1)
                        .firstName("qwerty")
                        .lastName("qwerty")
                        .address("qwerty")
                        .age(1)
                        .email("qwerty")
                        .password("qwerty")
                        .build()));
        when(this.customerMapper.toDto(any()))
                .thenReturn(CustomerDto.builder()
                        .customerId(1)
                        .firstName("qwerty")
                        .lastName("qwerty")
                        .address("qwerty")
                        .age(1)
                        .email("qwerty")
                        .password("qwerty")
                        .build());
        ResponseDto<CustomerDto> response = this.customerService.delete(id);
        assertTrue(response.isSuccess());
        assertEquals("Customer successful deleted!", response.getMessage());
        Assertions.assertNotNull(response.getData());

        Mockito.verify(this.customerRepository, Mockito.times(1)).findByCustomerIdAndDeletedAtIsNull(id);
        Mockito.verify(this.customerMapper, Mockito.times(1)).toDto(any());
    }

    @Test
    public void testDeleteMethodNegative() {
        Integer id = 1;
        when(this.customerRepository.findByCustomerIdAndDeletedAtIsNull(id))
                .thenReturn(Optional.empty());
        ResponseDto<CustomerDto> response = this.customerService.delete(id);
        Assertions.assertNull(response.getData());
        assertEquals("Not found!", response.getMessage());
        Assertions.assertFalse(response.isSuccess());

        Mockito.verify(this.customerRepository, Mockito.times(1)).findByCustomerIdAndDeletedAtIsNull(id);
    }

    @Test
    public void testDeleteMethodException() {
        Integer id = 1;
        when(this.customerMapper.toDto(any()))
                .thenThrow(RuntimeException.class);
        ResponseDto<CustomerDto> response = this.customerService.delete(id);
        Assertions.assertNull(response.getData());
        Assertions.assertNotNull(response.getMessage());
        Assertions.assertFalse(response.isSuccess());
        Mockito.verify(this.customerRepository, Mockito.times(1)).findByCustomerIdAndDeletedAtIsNull(id);
    }


    @Test
   public void testGetAll() {
        Map<String, String> params = new HashMap<>();
        params.put("page", "1");
        params.put("size", "10");

        Customer customer = new Customer();

        Page<Customer> page1 = new PageImpl<>(List.of(customer), PageRequest.of(1, 10), 1);

        when(customerRepository.findByCustomerByPaginationSearch(any(), any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(page1);
        when(customerMapper.toDto(any())).thenReturn(CustomerDto.builder()
                .password("qwerty")
                .email("qwerty")
                .customerId(1)
                .username("qwerty")
                .age(45)
                .address("qwerty")
                .build());
        ResponseDto<Page<CustomerDto>> response = customerService.getAll(params);

        assertTrue(response.isSuccess());

        assertEquals("Ok", response.getMessage());
        assertEquals(1, response.getData().getContent().size());
        Mockito.verify(this.customerRepository,times(1)).findByCustomerByPaginationSearch(any(), any(), any(), any(), any(), any(), any(), any());
        Mockito.verify(this.customerMapper,times(1)).toDto(any());
    }


}
