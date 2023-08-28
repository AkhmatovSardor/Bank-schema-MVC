package com.example.Bank.Schema.entity;

import com.example.Bank.Schema.dto.AuthoritiesDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = ("customer"))
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;
    private String firstName;
    private String lastName;
    private Integer age;
    private String email;
    private String username;
    private String password;
    private String address;
    private Boolean enable;
    @OneToMany(mappedBy = "customerId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Authorities> authority;
    @OneToMany(mappedBy = "customerId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<CreditCard> creditCard;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = ("customerId"), referencedColumnName = ("customerId"), updatable = false, insertable = false, unique = true)
    private Borrower borrower;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = ("customerId"), referencedColumnName = ("customerId"), updatable = false, insertable = false, unique = true)
    private Transaction transaction;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
