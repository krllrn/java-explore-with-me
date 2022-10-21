package ru.practicum.ewm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.models.comment.Comment;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByIdIs(Long commentId);

    @Query(value = "select * from comments where id = ?1 and event_id = ?2", nativeQuery = true)
    Comment findByIdAndEventId(Long commentId, Long eventId);

    @Query(value = "select * from comments where event_id = ?1", nativeQuery = true)
    List<Comment> findByEventId(Long eventId);
}
