package ru.ginatulin.users.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ginatulin.riuting_lib.dto.SignUpRequestDto;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
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

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {
        private final User user;

        private UserBuilder() {
            user = new User();
        }

        public UserBuilder id(String id) {
            user.id = id;
            return this;
        }

        public UserBuilder email(String email) {
            user.email = email;
            return this;
        }

        public UserBuilder telegramId(String telegramId) {
            user.telegramId = telegramId;
            return this;
        }

        public UserBuilder phone(String phone) {
            user.phone = phone;
            return this;
        }

        public UserBuilder password(String password) {
            user.password = password;
            return this;
        }

        public UserBuilder roles(List<Role> roles) {
            user.roles = roles;
            return this;
        }

        public UserBuilder timeStamp(LocalDateTime timeStamp) {
            user.timeStamp = timeStamp;
            return this;
        }

        public UserBuilder updatedAt(LocalDateTime updatedAt) {
            user.updatedAt = updatedAt;
            return this;
        }

        public UserBuilder deleted_at(LocalDateTime deleted_at) {
            user.deleted_at = deleted_at;
            return this;
        }

        public User build() {
            return user;
        }

    }
}
