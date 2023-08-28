package com.example.Bank.Schema.controller;

import com.example.Bank.Schema.dto.AuthoritiesDto;
import com.example.Bank.Schema.dto.ResponseDto;
import com.example.Bank.Schema.service.AuthoritiesService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

@RestController("/auth")
@RequiredArgsConstructor
@Slf4j
@EnableMethodSecurity(
        proxyTargetClass = true,
        securedEnabled = true,
        jsr250Enabled = true
)
@Secured(value = "ADMIN")
@RateLimiter(name = "first-rate-limiter",fallbackMethod = "fallBack")
public class AuthoritiesController {
    private final AuthoritiesService authoritiesService;

    @PostMapping("/create")
    public ResponseDto<AuthoritiesDto> create(@Valid @RequestBody AuthoritiesDto dto) {
        return this.authoritiesService.create(dto);
    }

    @GetMapping("/get/{id}")
    public ResponseDto<AuthoritiesDto> get(@PathVariable(value = "id") Integer id) {
        return this.authoritiesService.get(id);
    }

    @PatchMapping("/block-role")
    public ResponseDto<AuthoritiesDto> blockRole(@RequestParam(value = "userId") Integer userId) {
        return this.authoritiesService.blockRole(userId);
    }
    public ResponseDto<AuthoritiesDto> fallBack(Exception e){
        log.warn("inti fallBack method");
        return ResponseDto.<AuthoritiesDto>builder()
                .message("inti fallBack method")
                .build();
    }
}
