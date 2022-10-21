package ru.practicum.ewm.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.models.comment.Comment;
import ru.practicum.ewm.models.comment.CommentDto;
import ru.practicum.ewm.models.comment.CommentShortDto;
import ru.practicum.ewm.models.event.Event;
import ru.practicum.ewm.models.user.User;
import ru.practicum.ewm.models.user.UserShortDto;

import java.time.LocalDateTime;

@Component
public class CommentMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public CommentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CommentDto entityToDto(Comment comment) {
        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
        commentDto.setAuthor(modelMapper.map(comment.getAuthor(), UserShortDto.class));
        commentDto.setEventId(comment.getEvent().getId());
        return commentDto;
    }

    public Comment shortToEntity(User user, Event event, CommentShortDto commentShortDto) {
        Comment comment = modelMapper.map(commentShortDto, Comment.class);
        comment.setCreated(LocalDateTime.now());
        comment.setEvent(event);
        comment.setAuthor(user);
        return comment;
    }
}
