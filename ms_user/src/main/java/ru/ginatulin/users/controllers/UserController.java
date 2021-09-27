package ru.ginatulin.users.controllers;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ginatulin.riuting_lib.dto.AuthRequestDto;
import ru.ginatulin.riuting_lib.dto.AuthResponseDto;
import ru.ginatulin.riuting_lib.dto.SignUpRequestDto;
import ru.ginatulin.riuting_lib.dto.UserDto;
import ru.ginatulin.users.models.User;
import ru.ginatulin.users.services.UserService;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public User registerUser(@RequestBody SignUpRequestDto signUpRequestDto) {
        return service.createUSer(signUpRequestDto);
    }

    @GetMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(@RequestHeader("Authorization") String token) {
        // TODO: 26.09.2021 Crete logout with redis
    }

    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody AuthRequestDto request) {
        return service.login(request);
    }

    @GetMapping("/{id}")
    @HystrixCommand(fallbackMethod = "faultedGetUserById")
    public UserDto getUserById(@PathVariable String id) {
        return service.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public UserDto deleteUser(@PathVariable String id) {
        return service.delete(id);
    }

    @PutMapping
    public UserDto updateUser(@RequestBody UserDto userDto) {
        return service.updateUser(userDto);
    }

    public UserDto faultedGetUserById(String id) {
        return null;
    }
}
