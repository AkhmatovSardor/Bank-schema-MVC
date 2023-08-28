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
@Table(name = ("branch"))
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer branchId;
    private String name;
    private String address;
    private Boolean status;

    @OneToMany(mappedBy = "branchId",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Banker> banker;

    @OneToMany(mappedBy = "branchId",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Account> account;

    @OneToMany(mappedBy = "branchId",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Loan> loan;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
