package ru.ginatulin.cor_lib.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@Builder
@RedisHash("TokenRedis")
public class TokenRedis implements Serializable {
    private String id;
    private Boolean condition;
}
