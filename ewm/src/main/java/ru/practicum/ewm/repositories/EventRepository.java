package ru.practicum.ewm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.models.event.Event;
import ru.practicum.ewm.models.event.EventStates;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Event findByIdIs(Long eventId);

    // Find events linked with category
    @Query(value = "select * from events where category_id = ?1", nativeQuery = true)
    List<Event> findEventsByCategory(Long catId);

    //Find events by initiator
    @Query(value = "select * from events where initiator = ?1", nativeQuery = true)
    List<Event> findEventsByInitiator(Long initiatorId);

    //Find events by state
    @Query(value = "select * from events where state like ?1%", nativeQuery = true)
    List<Event> findEventsByState(String state);
}
