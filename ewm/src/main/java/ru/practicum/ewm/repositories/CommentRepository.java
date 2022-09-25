package ru.practicum.ewm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.models.comment.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
