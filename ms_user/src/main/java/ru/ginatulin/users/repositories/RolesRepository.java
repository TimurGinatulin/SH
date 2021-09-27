package ru.ginatulin.users.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ginatulin.users.models.Role;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByTitle(String title);
}
