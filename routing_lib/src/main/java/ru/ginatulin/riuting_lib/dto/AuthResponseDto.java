package ru.ginatulin.riuting_lib.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDto {
    private String id;
    private String token;
}
