package com.example.Bank.Schema.controller;

import com.example.Bank.Schema.dto.BranchDto;
import com.example.Bank.Schema.dto.ResponseDto;
import com.example.Bank.Schema.service.BranchService;
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
@RequestMapping("branch")
@RequiredArgsConstructor
@Slf4j
@RateLimiter(name = "first-rate-limiter",fallbackMethod = "fallBack")
public class BranchController implements SimpleCrud<Integer, BranchDto> {
    private final BranchService branchService;

    @PostMapping("/create")
    @Override
    @Operation(
            tags = "Create",
            summary = "Your summary create branch method!",
            description = "This is method for create branch!",
            responses = @ApiResponse(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = BranchDto.class
                            )
                    )
            )
    )
    public ResponseDto<BranchDto> create(@Valid @RequestBody BranchDto dto) {
        return this.branchService.create(dto);
    }

    @GetMapping("/get/{id}")
    @Override
    @Operation(
            tags = "Get",
            summary = "Your summary get by branch method!",
            description = "This is method for get branch!"
    )
    public ResponseDto<BranchDto> get(@PathVariable(value = "id") Integer id) {
        return this.branchService.get(id);
    }

    @PutMapping("/update/{id}")
    @Override
    @Operation(
            tags = "Update",
            summary = "Your summary update branch method!",
            description = "This is method for update branch!",
            responses = @ApiResponse(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = BranchDto.class
                            )
                    )
            )
    )
    public ResponseDto<BranchDto> update(@PathVariable(value = "id") Integer id, @RequestBody BranchDto dto) {
        return this.branchService.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    @Override
    @Operation(
            tags = "Delete",
            summary = "Your summary delete by branch method!",
            description = "This is method for delete branch!"
    )
    public ResponseDto<BranchDto> delete(@PathVariable(value = "id") Integer id) {
        return this.branchService.delete(id);
    }
    public ResponseDto<BranchDto> fallBack(Exception e){
        log.warn("inti fallBack method");
        return ResponseDto.<BranchDto>builder()
                .message("inti fallBack method")
                .build();
    }
}
