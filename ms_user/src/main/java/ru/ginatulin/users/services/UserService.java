package ru.ginatulin.users.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ginatulin.cor_lib.exception.models.Forbidden;
import ru.ginatulin.cor_lib.exception.models.NotFoundException;
import ru.ginatulin.cor_lib.interfacces.ITokenService;
import ru.ginatulin.cor_lib.models.UserInfo;
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

    public User createUSer(SignUpRequestDto signUpRequestDto) {
        User user = new User(signUpRequestDto);
        Role role = rolesRepository.findByTitle(RoleEnums.USER.name())
                .orElseThrow(() -> new NotFoundException("Role not found"));
        user.setRoles(Collections.singletonList(role));
        return userRepository.save(user);
    }

    public AuthResponseDto login(AuthRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);
        if (user != null)
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
                throw new Forbidden("Bad credentials");
        List<String> roles = new ArrayList<>();
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
            dto.getRoles().forEach(role -> {
                Role newRole = rolesRepository.findByTitle(role)
                        .orElse(null);
                if (newRole != null)
                    entity.getRoles().add(newRole);
            });
        }
    }
}
