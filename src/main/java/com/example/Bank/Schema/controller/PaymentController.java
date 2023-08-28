package com.example.Bank.Schema.controller;

import com.example.Bank.Schema.dto.PaymentDto;
import com.example.Bank.Schema.dto.ResponseDto;
import com.example.Bank.Schema.service.PaymentService;
import com.example.Bank.Schema.util.SimpleCrud;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("payment")
@RequiredArgsConstructor
@Slf4j
@RateLimiter(name = "first-rate-limiter",fallbackMethod = "fallBack")
public class PaymentController implements SimpleCrud<Integer, PaymentDto> {
    private final PaymentService paymentService;

    @PostMapping("/create")
    @Override
    @Operation(
            tags = "Create",
            summary = "Your summary create payment method!",
            description = "This is method for create payment!",
            responses = @ApiResponse(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = PaymentDto.class
                            )
                    )
            )
    )
    public ResponseDto<PaymentDto> create(@Valid @RequestBody PaymentDto dto) {
        return this.paymentService.create(dto);
    }

    @GetMapping("/get/{id}")
    @Override
    @Operation(
            tags = "Get",
            summary = "Your summary get by payment method!",
            description = "This is method for get payment!"
    )
    public ResponseDto<PaymentDto> get(@PathVariable(value = "id") Integer id) {
        return this.paymentService.get(id);
    }

    @PutMapping("/update/{id}")
    @Override
    @Operation(
            tags = "Update",
            summary = "Your summary update payment method!",
            description = "This is method for update payment!",
            responses = @ApiResponse(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = PaymentDto.class
                            )
                    )
            )
    )
    public ResponseDto<PaymentDto> update(@PathVariable(value = "id") Integer id, @RequestBody PaymentDto dto) {
        return this.paymentService.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    @Override
    @Operation(
            tags = "Delete",
            summary = "Your summary delete by payment method!",
            description = "This is method for delete payment!"
    )
    public ResponseDto<PaymentDto> delete(@PathVariable(value = "id") Integer id) {
        return this.paymentService.delete(id);
    }
    public ResponseDto<PaymentDto> fallBack(Exception e){
        log.warn("inti fallBack method");
        return ResponseDto.<PaymentDto>builder()
                .message("inti fallBack method")
                .build();
    }
}
