package ru.practicum.ewm.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.models.comment.CommentDto;
import ru.practicum.ewm.models.event.*;
import ru.practicum.ewm.models.user.User;
import ru.practicum.ewm.models.user.UserShortDto;
import ru.practicum.ewm.repositories.CategoryRepository;
import ru.practicum.ewm.repositories.CommentRepository;
import ru.practicum.ewm.repositories.RequestRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventMapper {
    private final ModelMapper modelMapper;
    private final CommentMapper commentMapper;
    private final CategoryRepository categoryRepository;
    private final CommentRepository commentRepository;
    private final RequestRepository requestRepository;

    @Autowired
    public EventMapper(ModelMapper modelMapper, CommentMapper commentMapper, CategoryRepository categoryRepository, CommentRepository commentRepository, RequestRepository requestRepository) {
        this.modelMapper = modelMapper;
        this.commentMapper = commentMapper;
        this.categoryRepository = categoryRepository;
        this.commentRepository = commentRepository;
        this.requestRepository = requestRepository;
    }

    public EventFullDto entityToFullDto(Event event) {
        EventFullDto newEvenFullDto = modelMapper.map(event, EventFullDto.class);
        newEvenFullDto.setLocation(event.getLocation());
        newEvenFullDto.setInitiator(modelMapper.map(event.getInitiator(), UserShortDto.class));
        newEvenFullDto.setComments(commentRepository.findByEventId(event.getId()).stream()
                .map(commentMapper::entityToDto).collect(Collectors.toList()));
        return newEvenFullDto;
    }

    public Event updateToEntity(Event event, AdminUpdateEventRequest adminUpdateEventRequest) {
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(adminUpdateEventRequest, event);
        return event;
    }

    public EventShortDto entityToShort(Event event) {
        EventShortDto eventShortDto = modelMapper.map(event, EventShortDto.class);
        if (commentRepository.findByEventId(event.getId()).size() > 0) {
            List<CommentDto> commentDto = commentRepository.findByEventId(event.getId()).stream()
                    .map(commentMapper::entityToDto)
                    .collect(Collectors.toList());
            eventShortDto.setComments(commentDto);
        }
        return eventShortDto;
    }

    public Event updateReqToEntity(Event event, UpdateEventRequest updateEventRequest) {
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(updateEventRequest, event);
        if (event.getState().equals(EventStates.CANCELED)) {
            event.setState(EventStates.PENDING);
        }
        return event;
    }

    public Event newToEntity(User user, NewEventDto newEventDto) {
        Event event = modelMapper.map(newEventDto, Event.class);
        event.setInitiator(user);
        event.setCreatedOn(LocalDateTime.now());
        event.setState(EventStates.PENDING);
        event.setViews(0L);
        event.setCategory(categoryRepository.findByIdIs(newEventDto.getCategory()));
        return event;
    }
}
