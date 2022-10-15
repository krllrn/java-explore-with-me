package ru.practicum.ewm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.models.request.Request;
import ru.practicum.ewm.models.request.RequestStatus;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    //Find by event ID
    @Query(value = "select * from requests where event_id = ?1", nativeQuery = true)
    List<Request> findAllByEventId(Long eventId);

    //Find by event ID with status
    @Query(value = "select * from requests where event_id = ?1 and status like ?2%", nativeQuery = true)
    List<Request> findAllByEventIdAndStatus(Long eventId, RequestStatus status);

    //Find by requester ID
    @Query(value = "select * from requests where requester = ?1", nativeQuery = true)
    List<Request> findAllByRequesterId(Long requester);

    //Find by requester ID and event ID
    @Query(value = "select * from requests where event_id = ?1 and requester = ?2", nativeQuery = true)
    Request findByRequesterAndEvent(Long eventId, Long requester);

    Request findByIdIs(Long reqId);
}
