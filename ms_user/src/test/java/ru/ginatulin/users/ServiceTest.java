package ru.ginatulin.users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.ginatulin.riuting_lib.dto.AuthRequestDto;
import ru.ginatulin.riuting_lib.dto.AuthResponseDto;
import ru.ginatulin.riuting_lib.dto.UserDto;
import ru.ginatulin.users.models.Role;
import ru.ginatulin.users.models.User;
import ru.ginatulin.users.repositories.UserRepository;
import ru.ginatulin.users.services.UserService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest()
public class ServiceTest {
    @Autowired
    private UserService service;
    @MockBean
    private UserRepository repository;
    private UserDto testDto;

    @BeforeEach
    public void init() {
        List<Role> roles = Collections.singletonList(
                Role.builder()
                        .id(1)
                        .title("test")
                        .timeStamp(LocalDateTime.MIN)
                        .updatedAt(LocalDateTime.MIN)
                        .deleted_at(null)
                        .build());
        User user = User.builder()
                .id("test")
                .email("test@test")
                .phone("test")
                .telegramId("test")
                .password("$2a$12$e.zXKJXPYz.ypR8J0lfgmehp/SpkoBlUY0flPlSfw7jrLjC.S1XI.")
                .roles(roles)
                .timeStamp(LocalDateTime.MIN)
                .updatedAt(LocalDateTime.MIN)
                .deleted_at(null)
                .build();
        User user2 = User.builder()
                .id("test")
                .email("test@test2")
                .phone("test2")
                .telegramId("test2")
                .password("$2a$12$e.zXKJXPYz.ypR8J0lfgmehp/SpkoBlUY0flPlSfw7jrLjC.S1XI.")
                .roles(roles)
                .timeStamp(LocalDateTime.MIN)
                .updatedAt(LocalDateTime.MIN)
                .deleted_at(null)
                .build();

        Mockito.doReturn(Optional.of(user))
                .when(repository)
                .findById("test");

        Mockito.doReturn(user2)
                .when(repository)
                .save(user2);

        Mockito.doReturn(Optional.of(user))
                .when(repository)
                .findByEmail("test@test");

        testDto = UserDto.builder()
                .id("test")
                .phone("test")
                .email("test@test")
                .telegramId("test")
                .roles(Collections.singletonList("test"))
                .build();
    }

    @Test
    public void testGetById() {
        UserDto repeatDto = service.getUserById("test");
        assertEquals(repeatDto, testDto);

    }
    @Test
    public void login(){
        AuthRequestDto loginRequest = new AuthRequestDto("test@test","100");
        AuthResponseDto authResponseDto = service.login(loginRequest);
        assertEquals("test",authResponseDto.getId());
    }
    @Test
    public void update(){
        UserDto test = UserDto.builder()
                .id("test")
                .email("test@test2")
                .phone("test2")
                .telegramId("test2")
                .roles(Collections.singletonList("test2"))
                .build();
        UserDto receive = service.updateUser(test);
        test.setRoles(Collections.singletonList("test"));//Так сделанно потому что не замокан roleRepository
        assertEquals(test,receive);
    }
}
