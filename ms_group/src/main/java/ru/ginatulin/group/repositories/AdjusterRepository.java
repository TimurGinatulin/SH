package ru.ginatulin.group.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ginatulin.group.models.Adjuster;

@Repository
public interface AdjusterRepository extends JpaRepository<Adjuster, String> {
}
