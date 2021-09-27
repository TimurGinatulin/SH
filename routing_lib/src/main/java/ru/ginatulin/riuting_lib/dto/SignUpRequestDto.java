package ru.ginatulin.riuting_lib.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpRequestDto {
    private String email;
    private String telegramId;
    private String phone;
    private String password;
}
