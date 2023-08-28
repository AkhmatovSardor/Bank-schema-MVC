package com.example.Bank.Schema.entity;

import com.example.Bank.Schema.dto.CustomerDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(timeToLive = 60 * 60 * 60 * 60)
public class RefreshToken {
    @Id
    private String refreshToken;
    private CustomerDto dto;
}
