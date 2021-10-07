package ru.ginatulin.cor_lib.repositoreis;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.ginatulin.cor_lib.models.TokenRedis;

@Repository
public interface TokenRedisRepository extends CrudRepository<TokenRedis, String> {
}
