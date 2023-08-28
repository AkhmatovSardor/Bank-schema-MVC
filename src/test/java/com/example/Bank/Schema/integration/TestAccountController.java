package com.example.Bank.Schema.integration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.example.Bank.Schema.BankSchemaApplication;
import com.example.Bank.Schema.dto.AccountDto;
import com.example.Bank.Schema.dto.ResponseDto;
import com.example.Bank.Schema.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.util.Json;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

//@SpringJUnitConfig
//@SpringBootTest
//@RunWith(SpringRunner.class)
//@AutoConfigureMockMvc
//@ActiveProfiles("test")
//@BootstrapWith(SpringBootTestContextBootstrapper.class)
//@SpringBootApplication

/*@TestPropertySource("/application.yaml")
@ContextConfiguration(classes= BankSchemaApplication.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DataJpaTest
@BootstrapWith(SpringBootTestContextBootstrapper.class)*/
public class TestAccountController {
  /*  @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Test
    public void testCreateAccount_Positive() throws Exception {
        AccountDto accountDto = new AccountDto(*//* initialize accountDto properties here *//*);
        when(accountService.create(any(AccountDto.class))).thenReturn(
                ResponseDto.<AccountDto>builder()

                        .build()
                *//* create a mock ResponseDto<AccountDto> for success *//*);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsBytes(AccountDto.builder().build())
                                *//* serialize accountDto to JSON *//*))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Add assertions based on the expected behavior of your application
    }

    @Test
    public void testCreateAccount_Exception() throws Exception {
        AccountDto accountDto = new AccountDto(*//* initialize accountDto properties here *//*);
        when(accountService.create(any(AccountDto.class))).thenThrow(new RuntimeException("Some error"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsBytes(AccountDto.builder().build())
                                *//* serialize accountDto to JSON *//*))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Add assertions based on the expected behavior of your application
    }*/
}

