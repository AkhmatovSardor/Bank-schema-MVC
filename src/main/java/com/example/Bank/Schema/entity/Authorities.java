package com.example.Bank.Schema.entity;

import jakarta.persistence.*;
import lombok.*;
@Data
@Entity
@Table(name = ("authorities"))
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Authorities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String authority;
    private Integer customerId;
}
