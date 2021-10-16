package ru.ginatulin.riuting_lib.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SignUpRequestDto {
    private String email;
    private String telegramId;
    private String phone;
    private String password;
}
