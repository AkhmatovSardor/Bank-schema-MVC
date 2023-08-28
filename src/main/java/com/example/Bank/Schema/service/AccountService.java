package com.example.Bank.Schema.service;

import com.example.Bank.Schema.dto.AccountDto;
import com.example.Bank.Schema.dto.ErrorDto;
import com.example.Bank.Schema.dto.ResponseDto;
import com.example.Bank.Schema.repository.AccountRepository;
import com.example.Bank.Schema.service.mapper.AccountMapper;
import com.example.Bank.Schema.service.validate.AccountValidate;
import com.example.Bank.Schema.util.SimpleCrud;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AccountService implements SimpleCrud<Integer, AccountDto>{
    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;
    private final AccountValidate accountValidate;

    @Override
    public ResponseDto<AccountDto> create(AccountDto dto) {
        List<ErrorDto> errors = this.accountValidate.validate(dto);
        if (!errors.isEmpty()) {
            return ResponseDto.<AccountDto>builder()
                    .message("Validate error!")
                    .errors(errors)
                    .code(-2)
                    .build();
        }
        try {
            return ResponseDto.<AccountDto>builder()
                    .message("Account successful created!")
                    .data(this.accountMapper.toDto(accountRepository.save(accountMapper.toEntity(dto))))
                    .success(true)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<AccountDto>builder()
                    .message("While saving error " + e.getMessage())
                    .code(-3)
                    .build();
        }
    }

    @Override
    public ResponseDto<AccountDto> get(Integer id) {
        return this.accountRepository.findByAccountIdAndDeletedAtIsNull(id)
                .map(account -> ResponseDto.<AccountDto>builder()
                        .message("OK")
                        .success(true)
                        .data(this.accountMapper.toDto(account))
                        .build())
                .orElse(ResponseDto.<AccountDto>builder()
                        .message("Not found!")
                        .code(-1)
                        .build());
    }

    @Override
    public ResponseDto<AccountDto> update(Integer id, AccountDto dto) {
        try {
            return this.accountRepository.findByAccountIdAndDeletedAtIsNull(id).map(account -> {
                this.accountMapper.update(account, dto);
                this.accountRepository.save(account);
                return ResponseDto.<AccountDto>builder()
                        .message("Account successful updated!")
                        .success(true)
                        .data(this.accountMapper.toDto(account))
                        .build();
            }).orElse(ResponseDto.<AccountDto>builder()
                    .message("Not found!")
                    .code(-1)
                    .build());
        } catch (Exception e) {
            return ResponseDto.<AccountDto>builder()
                    .message("While updating error " + e.getMessage())
                    .code(-3)
                    .build();
        }
    }

    @Override
    public ResponseDto<AccountDto> delete(Integer id) {
        try {
            return this.accountRepository.findByAccountIdAndDeletedAtIsNull(id).map(account -> {
                        account.setDeletedAt(LocalDateTime.now());
                        this.accountRepository.save(account);
                        return ResponseDto.<AccountDto>builder()
                                .message("Account successful deleted!")
                                .success(true)
                                .data(this.accountMapper.toDto(account))
                                .build();
                    })
                    .orElse(ResponseDto.<AccountDto>builder()
                            .message("Not found!")
                            .code(-1)
                            .build());
        } catch (Exception e) {
            return ResponseDto.<AccountDto>builder()
                    .message("While deleting error " + e.getMessage())
                    .code(-3)
                    .build();
        }
    }

    public ResponseDto<Page<AccountDto>> getAll(Map<String, String> params) {
        int page = 0, size = 10;
        if (params.containsKey("page")){
            page=Integer.parseInt(params.get("page"));
        }
        if (params.containsKey("size")){
            size=Integer.parseInt(params.get("size"));
        }
        Page<AccountDto> accounts=this.accountRepository.findByAccountsByPaginationSearch(
                params.get("accountId")==null?null:Integer.valueOf(params.get("accountId")),
                params.get("balance")==null?null:Double.valueOf(params.get("balance")),
                params.get("type"),
                params.get("branchId")==null?null:Integer.valueOf(params.get("branchId")),
                PageRequest.of(page,size)).map(accountMapper::toDto);
        return ResponseDto.<Page<AccountDto>>builder()
                .message("Ok")
                .success(true)
                .data(accounts)
                .build();
    }
}
