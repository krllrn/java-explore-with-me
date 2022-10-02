package ru.practicum.ewm.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.models.comment.Comment;
import ru.practicum.ewm.models.comment.CommentDto;
import ru.practicum.ewm.models.comment.CommentShortDto;

import java.time.LocalDateTime;

@Component
public class CommentMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public CommentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CommentDto entityToDto(Comment comment) {
        return modelMapper.map(comment, CommentDto.class);
    }

    public Comment shortToEntity(String username, Long eventId, CommentShortDto commentShortDto) {
        Comment comment = modelMapper.map(commentShortDto, Comment.class);
        comment.setCreated(LocalDateTime.now());
        comment.setEventId(eventId);
        comment.setAuthorName(username);

        return comment;
    }
}
