package ru.ginatulin.users;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.ginatulin.users.models.User;
import ru.ginatulin.users.repositories.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void migrationTest() {
        User user = userRepository.findByEmail("test@mail.ru").orElse(null);
        assertNotNull(user, "Check migration");
    }

    @Test
    public void checkRoleSizeMigrationTest() {
        User user = userRepository.findByEmail("test@mail.ru").orElse(null);
        assertEquals(2, user.getRoles().size(),"Not all role was migrated");
    }
    @Test
    public void checkRoleMigrationTest() {
        User user = userRepository.findByEmail("test@mail.ru").orElse(null);
        assertEquals("ADMIN", user.getRoles().get(0).getTitle());
        assertEquals("USER", user.getRoles().get(1).getTitle());
    }
}
