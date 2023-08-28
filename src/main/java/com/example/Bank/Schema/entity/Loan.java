package com.example.Bank.Schema.entity;

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
@Table(name = ("loan"))
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer loanId;
    private String goal;
    private Double issuedAmount;
    private Double remainingAmount;
    private Integer branchId;
    private Integer accountId;
    private Boolean status;
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = ("loanId"),referencedColumnName = ("loanId"),insertable = false,updatable = false,unique = true)
    private Payment payment;

    @OneToMany(mappedBy = "loanId",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Account> account;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
