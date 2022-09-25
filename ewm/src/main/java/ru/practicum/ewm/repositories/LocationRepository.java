package ru.practicum.ewm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.models.location.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
