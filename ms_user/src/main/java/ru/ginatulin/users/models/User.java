package ru.ginatulin.users.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ginatulin.riuting_lib.dto.SignUpRequestDto;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_table")
public class User {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "email")
    private String email;
    @Column(name = "telegram_id")
    private String telegramId;
    @Column(name = "phone")
    private String phone;
    @Column(name = "password")
    private String password;
    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_role")
    )
    private List<Role> roles;
    @Column(name = "time_stamp")
    private LocalDateTime timeStamp;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "deleted_at")
    private LocalDateTime deleted_at;

    public User(SignUpRequestDto signUpRequestDto) {
        this.email = signUpRequestDto.getEmail();
        this.telegramId = signUpRequestDto.getTelegramId();
        this.phone = signUpRequestDto.getPhone();
        this.password = signUpRequestDto.getPassword();
    }
}
