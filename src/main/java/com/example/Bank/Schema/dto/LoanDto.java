package com.example.Bank.Schema.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoanDto {
    private Integer loanId;
    private String goal;
    @NotNull(message = "issuedAmount cannot be null!")
    private Double issuedAmount;
    @NotNull(message = "remainingAmount cannot be null!")
    private Double remainingAmount;
    private Integer branchId;
    private Integer accountId;
    private Boolean status;

    private PaymentDto payment;
    private Set<AccountDto> account;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
