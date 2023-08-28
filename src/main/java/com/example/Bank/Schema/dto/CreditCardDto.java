package com.example.Bank.Schema.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreditCardDto {
    private Integer creditCardId;
    @NotNull(message = "number is cannot be null!")
   // @Pattern(regexp = "[0-9]{16}")
    private Long number;
    @NotBlank(message = "name is cannot be null or empty!")
    private String name;
    private Double amount;
    private Integer customerId;
    private Integer accountId;
    private LocalDateTime expireData;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
