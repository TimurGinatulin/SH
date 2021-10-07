package ru.ginatulin.cor_lib.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ginatulin.cor_lib.models.TokenRedis;
import ru.ginatulin.cor_lib.repositoreis.TokenRedisRepository;


@Service
@RequiredArgsConstructor
public class RedisService {
    @Autowired
    private TokenRedisRepository repository;

    public TokenRedis saveToken(TokenRedis tokenRedis) {
        TokenRedis save = repository.save(tokenRedis);
        System.out.println(save);
        return save;
    }

    public Boolean checkTokenInRedis(String token) {
        TokenRedis tokenRedis = repository.findById(token).orElse(null);
        if (tokenRedis != null){
            System.out.println(tokenRedis.getCondition());
            return tokenRedis.getCondition();
        }
        return false;
    }
    public boolean update(String token,Boolean condition){
        System.out.println(token);
        TokenRedis entity = repository.findById(token).orElse(null);
        if (entity != null){
            entity.setCondition(condition);
            repository.save(entity);
            System.out.println(entity);
            return true;
        }
        return false;
    }
}
