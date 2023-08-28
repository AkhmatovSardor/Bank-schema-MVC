package com.example.Bank.Schema.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BorrowerDto {
    private Integer borrowId;
    private Integer loanId;
    private Integer customerId;
    @NotNull(message = "amount is cannot be null!")
    private Double amount;
    private Boolean status;

    private LoanDto loan;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
