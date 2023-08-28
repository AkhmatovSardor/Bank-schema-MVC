package com.example.Bank.Schema.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
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
@Table(name = ("account"))
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer accountId;
    private Double balance;
    private String type;
    private Integer branchId;
    private Integer loanId;
    private Boolean enable;
    private Integer transactionId;

    @OneToMany(mappedBy = "accountId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<CreditCard> creditCard;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
