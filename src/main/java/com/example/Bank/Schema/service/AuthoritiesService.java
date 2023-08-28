package com.example.Bank.Schema.service;

import com.example.Bank.Schema.dto.AuthoritiesDto;
import com.example.Bank.Schema.dto.CustomerDto;
import com.example.Bank.Schema.dto.ResponseDto;
import com.example.Bank.Schema.repository.AuthoritiesRepository;
import com.example.Bank.Schema.service.mapper.AuthoritiesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthoritiesService {
    private final AuthoritiesMapper authoritiesMapper;
    private final AuthoritiesRepository authoritiesRepository;
    private final CustomerService customerService;

    public ResponseDto<AuthoritiesDto> create(AuthoritiesDto dto) {
        try {
            return ResponseDto.<AuthoritiesDto>builder()
                    .data(this.authoritiesMapper.toDto(this.authoritiesRepository.save(this.authoritiesMapper.toEntity(dto))))
                    .success(true)
                    .message("Authority successful created!")
                    .build();
        } catch (Exception e) {
            return ResponseDto.<AuthoritiesDto>builder()
                    .message("While saving error " + e.getMessage())
                    .code(-3)
                    .build();
        }
    }

    public ResponseDto<AuthoritiesDto> get(Integer id) {
        return this.authoritiesRepository.findById(id)
                .map(auth ->
                        ResponseDto.<AuthoritiesDto>builder()
                                .data(this.authoritiesMapper.toDto(auth))
                                .success(true)
                                .message("Ok")
                                .build())
                .orElse(ResponseDto.<AuthoritiesDto>builder()
                        .message("Not found!")
                        .code(-1)
                        .build());

    }

    public ResponseDto<AuthoritiesDto> blockRole(Integer userId) {
        if (this.customerService.get(userId).getData() == null) {
            return ResponseDto.<AuthoritiesDto>builder()
                    .code(-1)
                    .message("Customer is not found!")
                    .build();
        }
        if (this.customerService.get(userId).getData().getUsername() == null) {
            return ResponseDto.<AuthoritiesDto>builder()
                    .message("Customer with username not found!")
                    .code(-1)
                    .build();
        }
        if (this.customerService.get(userId).getData().getAuthority() == null) {
            return ResponseDto.<AuthoritiesDto>builder()
                    .message("This is user already blocked!")
                    .success(true)
                    .build();
        }
        this.customerService.update(userId, CustomerDto.builder()
                .authority(null)
                .build());
        return ResponseDto.<AuthoritiesDto>builder()
                .message("This is customer successful blocked!")
                .success(true)
                .build();
    }
}
