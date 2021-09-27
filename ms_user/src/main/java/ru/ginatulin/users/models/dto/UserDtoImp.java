package ru.ginatulin.users.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ginatulin.riuting_lib.dto.UserDto;
import ru.ginatulin.users.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoImp {
    private String id;
    private String email;
    private String telegramId;
    private String phone;
    private List<String> roles;

    public UserDtoImp(User user) {
        roles = new ArrayList<>();
        this.id = user.getId();
        this.email = user.getEmail();
        this.telegramId = user.getTelegramId();
        this.phone = user.getPhone();
        user.getRoles().forEach(role -> roles.add(role.getTitle()));
    }

    public UserDto castToDto() {
        return new UserDto(id, email, telegramId, phone, roles);
    }
}
