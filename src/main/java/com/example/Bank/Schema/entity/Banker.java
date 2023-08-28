package com.example.Bank.Schema.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = ("banker"))
public class Banker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bankerId;
    private String bankerName;
    private Integer branchId;
    private Boolean status;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
