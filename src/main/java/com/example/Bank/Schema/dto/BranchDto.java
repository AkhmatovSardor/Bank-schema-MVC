package com.example.Bank.Schema.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BranchDto {
    private Integer branchId;
    @NotBlank(message = "name is cannot be null or empty!")
    private String name;
    @NotBlank(message = "address is cannot be null or empty!")
    private String address;
    private Boolean status;

    private Set<BankerDto> banker;
    private Set<AccountDto> account;
    private Set<LoanDto> loan;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
