package ru.ginatulin.group.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ginatulin.group.models.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, String> {
}
