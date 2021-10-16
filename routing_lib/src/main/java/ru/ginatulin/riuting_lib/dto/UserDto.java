package ru.ginatulin.riuting_lib.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String id;
    private String email;
    private String telegramId;
    private String phone;
    private List<String> roles;

    public SignUpRequestDto castToRequest() {
        return SignUpRequestDto.builder()
                .email(this.email)
                .phone(this.phone)
                .telegramId(this.telegramId)
                .build();
    }
}
