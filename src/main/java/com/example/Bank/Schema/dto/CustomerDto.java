package com.example.Bank.Schema.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDto implements UserDetails {
    private Integer customerId;
    @NotBlank(message = "firstName is cannot be null or empty!")
    private String firstName;
    @NotBlank(message = "lastName is cannot be null or empty!")
    private String lastName;
    @NotNull(message = "age cannot be null!")
    private Integer age;
    @NotBlank(message = "email is cannot be null or empty!")
    private String email;
    @NotBlank(message = "username is cannot be null or empty!")
    private String username;
    @NotBlank(message = "password is cannot be null or empty!")
    private String password;
    @NotBlank(message = "address is cannot be null or empty!")
    private String address;
    private Boolean enable;
    private Set<AuthoritiesDto> authority;
    private Set<CreditCardDto> creditCard;
    private BorrowerDto borrower;
    private TransactionDto transaction;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Optional.ofNullable(authority)
                .map(auth -> auth.stream()
                        .map(a -> new SimpleGrantedAuthority(a.getAuthority().toString()))
                        .toList())
                .orElse(new ArrayList<>());
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
