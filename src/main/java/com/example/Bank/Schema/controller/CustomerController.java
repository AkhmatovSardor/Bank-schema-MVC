package com.example.Bank.Schema.controller;

import com.example.Bank.Schema.dto.CustomerDto;
import com.example.Bank.Schema.dto.LoginDto;
import com.example.Bank.Schema.dto.ResponseDto;
import com.example.Bank.Schema.dto.TokenResponseDto;
import com.example.Bank.Schema.service.CustomerService;
import com.example.Bank.Schema.util.SimpleCrud;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("customer")
@RequiredArgsConstructor
@Slf4j
@RateLimiter(name = "first-rate-limiter",fallbackMethod = "fallBack")
public class CustomerController implements SimpleCrud<Integer, CustomerDto> {
    private final CustomerService customerService;

    @PostMapping("/create")
    @Override
    @Operation(
            tags = "Create",
            summary = "Your summary create customer method!",
            description = "This is method for create customer!",
            responses = @ApiResponse(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = CustomerDto.class
                            )
                    )
            )
    )
    public ResponseDto<CustomerDto> create(@Valid @RequestBody CustomerDto dto) {
        return this.customerService.create(dto);
    }

    @GetMapping("/get/{id}")
    @Override
    @Operation(
            tags = "Get",
            summary = "Your summary get by customer method!",
            description = "This is method for get customer!"
    )
    public ResponseDto<CustomerDto> get(@PathVariable(value = "id") Integer id) {
        return this.customerService.get(id);
    }

    @PutMapping("/update/{id}")
    @Override
    @Operation(
            tags = "Update",
            summary = "Your summary update customer method!",
            description = "This is method for update customer!",
            responses = @ApiResponse(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = CustomerDto.class
                            )
                    )
            )
    )
    public ResponseDto<CustomerDto> update(@PathVariable(value = "id") Integer id, @RequestBody CustomerDto dto) {
        return this.customerService.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    @Override
    @Operation(
            tags = "Delete",
            summary = "Your summary delete by customer method!",
            description = "This is method for delete customer!"
    )
    public ResponseDto<CustomerDto> delete(@PathVariable(value = "id") Integer id) {
        return this.customerService.delete(id);
    }

    @GetMapping("/get-all")
    @Operation(
            tags = "Universal search; Page!",
            summary = "Your summary get page by customer method.",
            description = "This is method for get Page!",
            parameters = {
                    @Parameter(
                            name = "page",
                            example = "0",
                            in = ParameterIn.COOKIE
                    ),
                    @Parameter(
                            name = "size",
                            example = "10",
                            in = ParameterIn.COOKIE
                    )
            }
    )
    public ResponseDto<Page<CustomerDto>> getALL(@RequestParam Map<String, String> params) {
        return this.customerService.getAll(params);
    }

    @Operation(
            tags = "Register",
            summary = "sign-in!",
            description = "Bu metod user sign-in!",
            responses = @ApiResponse(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = LoginDto.class
                            )
                    )
            )
    )
    @PostMapping("/sign-in")
    public ResponseDto<TokenResponseDto> signIn(@NotNull @RequestBody LoginDto loginDto) {
        return this.customerService.signIn(loginDto);
    }

    @Operation(
            tags = "Register",
            summary = "refresh token!",
            description = "Bu metod token yangilab turadi!",
            responses = @ApiResponse(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = LoginDto.class
                            )
                    )
            )
    )
    @PostMapping("/refresh-token")
    public ResponseDto<TokenResponseDto> refresh(@NotNull @RequestParam(value = "token") String token) {
        return this.customerService.refresh(token);
    }
    public ResponseDto<CustomerDto> fallBack(Exception e){
        log.warn("inti fallBack method");
        return ResponseDto.<CustomerDto>builder()
                .message("inti fallBack method")
                .build();
    }
}
