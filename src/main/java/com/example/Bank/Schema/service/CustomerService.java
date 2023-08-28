package com.example.Bank.Schema.service;

import com.example.Bank.Schema.dto.*;
import com.example.Bank.Schema.entity.Customer;
import com.example.Bank.Schema.entity.RefreshToken;
import com.example.Bank.Schema.entity.UserSession;
import com.example.Bank.Schema.repository.CustomerRepository;
import com.example.Bank.Schema.repository.RefreshTokenRepository;
import com.example.Bank.Schema.repository.UserSessionRepository;
import com.example.Bank.Schema.security.JwtUtil;
import com.example.Bank.Schema.service.mapper.CustomerMapper;
import com.example.Bank.Schema.service.validate.CustomerValidate;
import com.example.Bank.Schema.util.SimpleCrud;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService implements SimpleCrud<Integer, CustomerDto>, UserDetailsService {
    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;
    private final CustomerValidate customerValidate;
    private final PasswordEncoder passwordEncoder;
    private final UserSessionRepository userSessionRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    @Override
    public ResponseDto<CustomerDto> create(CustomerDto dto) {
        List<ErrorDto> errors = this.customerValidate.validate(dto);
        if (!errors.isEmpty()) {
            return ResponseDto.<CustomerDto>builder()
                    .message("Validate error!")
                    .code(-3)
                    .errors(errors)
                    .build();
        }
        try {

            return ResponseDto.<CustomerDto>builder()
                    .data(this.customerMapper.toDto(this.customerRepository.save(this.customerMapper.toEntity(dto))))
                    .message("Customer successful created!")
                    .success(true)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<CustomerDto>builder()
                    .message("while saving error!")
                    .code(-3)
                    .build();
        }
    }

    @Override
    public ResponseDto<CustomerDto> get(Integer id) {
        return this.customerRepository.findByCustomerIdAndDeletedAtIsNull(id)
                .map(customer -> ResponseDto.<CustomerDto>builder()
                        .message("OK")
                        .success(true)
                        .data(this.customerMapper.toDtoWithAll(customer))
                        .build())
                .orElse(ResponseDto.<CustomerDto>builder()
                        .message("Not found!")
                        .code(-1)
                        .build());
    }

    @Override
    public ResponseDto<CustomerDto> update(Integer id, CustomerDto dto) {
        try {
            return this.customerRepository.findByCustomerIdAndDeletedAtIsNull(id)
                    .map(customer -> {
                        this.customerMapper.update(customer, dto);
                        this.customerRepository.save(customer);
                        return ResponseDto.<CustomerDto>builder()
                                .message("Customer successful updated!")
                                .success(true)
                                .data(this.customerMapper.toDto(customer))
                                .build();
                    })
                    .orElse(ResponseDto.<CustomerDto>builder()
                            .message("Not found!")
                            .code(-1)
                            .build());
        } catch (Exception e) {
            return ResponseDto.<CustomerDto>builder()
                    .message("While saving error " + e.getMessage())
                    .code(-3)
                    .build();
        }
    }

    @Override
    public ResponseDto<CustomerDto> delete(Integer id) {
        try {
            return this.customerRepository.findByCustomerIdAndDeletedAtIsNull(id)
                    .map(customer -> {
                        customer.setDeletedAt(LocalDateTime.now());
                        this.customerRepository.save(customer);
                        return ResponseDto.<CustomerDto>builder()
                                .message("Customer successful deleted!")
                                .success(true)
                                .data(this.customerMapper.toDto(customer))
                                .build();
                    }).orElse(ResponseDto.<CustomerDto>builder()
                            .message("Not found!")
                            .code(-1)
                            .build());
        } catch (Exception e) {
            return ResponseDto.<CustomerDto>builder()
                    .message("While deleting error " + e.getMessage())
                    .code(-3)
                    .build();
        }
    }

    public ResponseDto<Page<CustomerDto>> getAll(Map<String, String> params) {
        int page = 0, size = 10;
        if (params.containsKey("page")) {
            page = Integer.parseInt(params.get("page"));
        }
        if (params.containsKey("size")) {
            size = Integer.parseInt(params.get("size"));
        }
        Page<CustomerDto> customers = this.customerRepository.findByCustomerByPaginationSearch(
                params.get("customerId") == null ? null : Integer.valueOf(params.get("customerId")),
                params.get("firstName"),
                params.get("lastName"),
                params.get("age") == null ? null : Integer.valueOf(params.get("age")),
                params.get("email"),
                params.get("password"),
                params.get("address"),
                PageRequest.of(page, size)
        ).map(customerMapper::toDto);
        return ResponseDto.<Page<CustomerDto>>builder()
                .message("Ok")
                .success(true)
                .data(customers)
                .build();
    }

    @Override
    public CustomerDto loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.customerRepository.findByUsernameAndDeletedAtIsNull(username)
                .map(customerMapper::toDto).orElseThrow(() -> new UsernameNotFoundException("Account with username is not found!"));
    }

    @Transactional
    public ResponseDto<TokenResponseDto> signIn(@NotNull LoginDto loginDto) {
        CustomerDto customerDto = loadUserByUsername(loginDto.getUsername());
        if (customerDto == null) {
            throw new UsernameNotFoundException("User with username not found!");
        }
        if (this.passwordEncoder.matches(loginDto.getPassword(), customerDto.getPassword())) {
            String sessionId = UUID.randomUUID().toString();
            this.userSessionRepository.save(new UserSession(sessionId, customerDto));
            String refreshToken = UUID.randomUUID().toString();
            this.refreshTokenRepository.save(new RefreshToken(refreshToken, customerDto));
            return ResponseDto.<TokenResponseDto>builder()
                    .message("OK")
                    .success(true)
                    .data(TokenResponseDto.builder()
                            .accessToken(this.jwtUtil.generateToken(sessionId))
                            .refreshToken(refreshToken)
                            .build())
                    .build();
        }
        throw new BadCredentialsException("Password uncorrected!");
    }

    public ResponseDto<TokenResponseDto> refresh(@NotNull String refreshToken) {
        return this.refreshTokenRepository.findById(refreshToken)
                .map(rt -> {
                    String sessionId = UUID.randomUUID().toString();
                    this.userSessionRepository.save(new UserSession(sessionId, rt.getDto()));
                    return ResponseDto.<TokenResponseDto>builder()
                            .success(true)
                            .message("Ok")
                            .data(TokenResponseDto.builder()
                                    .refreshToken(refreshToken)
                                    .accessToken(this.jwtUtil.generateToken(sessionId))
                                    .build())
                            .build();
                })
                .orElseThrow(() -> new BadCredentialsException("Your credentials are incorrect or expired. Log in again!"));
    }
}
