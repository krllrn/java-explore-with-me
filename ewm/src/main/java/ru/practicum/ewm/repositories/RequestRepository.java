package ru.practicum.ewm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.models.request.Request;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    //Find by event ID
    @Query(value = "select * from requests where event_id = ?1", nativeQuery = true)
    List<Request> findAllByEventId(Long eventId);
}
