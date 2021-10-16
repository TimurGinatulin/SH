package ru.ginatulin.users.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ginatulin.cor_lib.exception.models.Forbidden;
import ru.ginatulin.cor_lib.exception.models.NotFoundException;
import ru.ginatulin.cor_lib.interfacces.ITokenService;
import ru.ginatulin.cor_lib.models.UserInfo;
import ru.ginatulin.cor_lib.services.RedisService;
import ru.ginatulin.riuting_lib.dto.AuthRequestDto;
import ru.ginatulin.riuting_lib.dto.AuthResponseDto;
import ru.ginatulin.riuting_lib.dto.SignUpRequestDto;
import ru.ginatulin.riuting_lib.dto.UserDto;
import ru.ginatulin.users.models.Enum.RoleEnums;
import ru.ginatulin.users.models.Role;
import ru.ginatulin.users.models.User;
import ru.ginatulin.users.models.dto.UserDtoImp;
import ru.ginatulin.users.repositories.RolesRepository;
import ru.ginatulin.users.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private ITokenService iTokenService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RedisService redisService;

    public User createUSer(SignUpRequestDto signUpRequestDto) {
        if (!checkUserRequestCorrectly(signUpRequestDto))
            throw new IllegalArgumentException();
        User user = new User(signUpRequestDto);
        Role role = rolesRepository.findByTitle(RoleEnums.USER.name())
                .orElseThrow(() -> new NotFoundException("Role not found"));
        user.setRoles(Collections.singletonList(role));
        return userRepository.save(user);
    }

    public AuthResponseDto login(AuthRequestDto request) {
        if (checkPassword(request.getPassword()) || checkEmail(request.getEmail()))
            throw new IllegalArgumentException();
        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);
        if (user != null)
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
                throw new Forbidden("Bad credentials");
        List<String> roles = new ArrayList<>();
        assert user != null;
        user.getRoles().forEach(role -> roles.add(role.getTitle()));
        UserInfo userInfo = UserInfo.builder()
                .id(user.getId())
                .userEmail(user.getEmail())
                .role(roles)
                .build();
        String token = iTokenService.generateToken(userInfo);
        return new AuthResponseDto(user.getId(), token);
    }

    public UserDto getUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id: " + id + " not found"));
        UserDtoImp dtoImp = new UserDtoImp(user);
        return dtoImp.castToDto();
    }

    public UserDto delete(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id: " + id + " not found"));
        user.setDeleted_at(LocalDateTime.now());
        User deleteUser = userRepository.save(user);
        UserDtoImp dtoImp = new UserDtoImp(deleteUser);
        return dtoImp.castToDto();
    }

    public UserDto updateUser(UserDto userDto) {
        if (!checkUserDtoCorrectly(userDto))
            throw new IllegalArgumentException();
        User user = userRepository.findById(userDto.getId())
                .orElseThrow(() -> new NotFoundException("User with id: " + userDto.getId() + " not found"));
        updateUser(user, userDto);
        User updatedUser = userRepository.save(user);
        return new UserDtoImp(updatedUser).castToDto();
    }

    private void updateUser(User entity, UserDto dto) {
        if (dto.getEmail() != null)
            entity.setEmail(dto.getEmail());
        if (dto.getPhone() != null)
            entity.setPhone(dto.getPhone());
        if (dto.getTelegramId() != null)
            entity.setTelegramId(dto.getTelegramId());
        if (dto.getRoles() != null) {
            dto.getRoles().forEach(role ->
                    rolesRepository.findByTitle(role).ifPresent(newRole -> entity.getRoles().add(newRole)));
        }
    }

    public void logout(String token) {
        redisService.saveToken(token.replace("Bearer ", ""));
    }

    private boolean checkUserRequestCorrectly(SignUpRequestDto requestDto) {
        return checkEmail(requestDto.getEmail()) &&
                checkPhone(requestDto.getPhone()) &&
                checkPassword(requestDto.getPassword());
    }

    private boolean checkUserDtoCorrectly(UserDto requestDto) {
        return checkEmail(requestDto.getEmail()) &&
                checkPhone(requestDto.getPhone());
    }

    private boolean checkEmail(String email) {
        String regex = "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(email).find();
    }

    private boolean checkPhone(String phone) {
        String regex = "^\\+7-\\(+\\d{3}+\\)-+\\d{3}+-+\\d{2}+-\\d{2}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(phone).find();
    }

    private boolean checkPassword(String password) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(password).find();
    }
}
