package ru.ginatulin.cor_lib.interfacces;

import ru.ginatulin.cor_lib.models.UserInfo;

public interface ITokenService {
    String generateToken(UserInfo user);

    UserInfo parseToken(String token);
}
