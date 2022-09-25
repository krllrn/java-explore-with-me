package ru.practicum.ewm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.models.event.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
