package ru.ginatulin.group.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ginatulin.group.models.Sensor;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, String> {
}
