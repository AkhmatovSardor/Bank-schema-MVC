package com.example.Bank.Schema.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthoritiesDto {
    private Integer id;
    private String username;
    private String authority;
    private Integer customerId;
}
