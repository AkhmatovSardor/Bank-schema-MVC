package com.example.Bank.Schema.controller;

import com.example.Bank.Schema.dto.ResponseDto;
import com.example.Bank.Schema.dto.TransactionDto;
import com.example.Bank.Schema.service.TransactionService;
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
@RequestMapping("transaction")
@RequiredArgsConstructor
@Slf4j
@RateLimiter(name = "first-rate-limiter",fallbackMethod = "fallBack")
public class TransactionController implements SimpleCrud<Integer, TransactionDto> {
    private final TransactionService transactionService;

    @PostMapping("/create")
    @Override
    @Operation(
            tags = "Create",
            summary = "Your summary create transaction method!",
            description = "This is method for create transaction!",
            responses = @ApiResponse(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = TransactionDto.class
                            )
                    )
            )
    )
    public ResponseDto<TransactionDto> create(@Valid @RequestBody TransactionDto dto) {
        return this.transactionService.create(dto);
    }

    @GetMapping("/get/{id}")
    @Override
    @Operation(
            tags = "Get",
            summary = "Your summary get by transaction method!",
            description = "This is method for get transaction!"
    )
    public ResponseDto<TransactionDto> get(@PathVariable(value = "id") Integer id) {
        return this.transactionService.get(id);
    }

    @PutMapping("/update/{id}")
    @Override
    @Operation(
            tags = "Update",
            summary = "Your summary update transaction method!",
            description = "This is method for update transaction!",
            responses = @ApiResponse(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = TransactionDto.class
                            )
                    )
            )
    )
    public ResponseDto<TransactionDto> update(@PathVariable(value = "id") Integer id, @RequestBody TransactionDto dto) {
        return this.transactionService.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    @Override
    @Operation(
            tags = "Delete",
            summary = "Your summary delete by transaction method!",
            description = "This is method for delete transaction!"
    )
    public ResponseDto<TransactionDto> delete(@PathVariable(value = "id") Integer id) {
        return this.transactionService.delete(id);
    }
    public ResponseDto<TransactionDto> fallBack(Exception e){
        log.warn("inti fallBack method");
        return ResponseDto.<TransactionDto>builder()
                .message("inti fallBack method")
                .build();
    }
}
