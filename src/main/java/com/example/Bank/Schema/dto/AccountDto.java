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
public class AccountDto {
    private Integer accountId;
    @NotNull(message = "balance is cannot be null!")
    private Double balance;
    private String type;
    private Integer branchId;
    private Integer loanId;
    private Boolean enable;
    private Integer transactionId;
    private Set<CreditCardDto> creditCard;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
