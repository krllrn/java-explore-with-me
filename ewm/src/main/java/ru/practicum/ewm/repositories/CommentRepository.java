package ru.practicum.ewm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.models.comment.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findByIdIs(Long commentId);

    @Query(value = "select * from comments where id = ?1 and event_id = ?2")
    Comment findByIdAndEventId(Long commentId, Long eventId);
}
