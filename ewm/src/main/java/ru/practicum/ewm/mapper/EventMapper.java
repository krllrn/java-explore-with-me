package ru.practicum.ewm.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.models.comment.CommentDto;
import ru.practicum.ewm.models.event.*;
import ru.practicum.ewm.models.user.User;
import ru.practicum.ewm.models.user.UserShortDto;
import ru.practicum.ewm.repositories.CategoryRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventMapper {
    private final ModelMapper modelMapper;
    private final CommentMapper commentMapper;
    private final CategoryRepository categoryRepository;

    @Autowired
    public EventMapper(ModelMapper modelMapper, @Lazy CommentMapper commentMapper, CategoryRepository categoryRepository) {
        this.modelMapper = modelMapper;
        this.commentMapper = commentMapper;
        this.categoryRepository = categoryRepository;
    }

    public EventFullDto entityToFullDto(Event event) {
        EventFullDto newEvenFullDto = modelMapper.map(event, EventFullDto.class);
        newEvenFullDto.setLocation(event.getLocation());
        newEvenFullDto.setInitiator(modelMapper.map(event.getInitiator(), UserShortDto.class));
        newEvenFullDto.setComments(event.getComments());
        return newEvenFullDto;
    }

    public Event updateToEntity(Event event, AdminUpdateEventRequest adminUpdateEventRequest) {
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(adminUpdateEventRequest, event);

        return event;
    }

    public EventShortDto entityToShort(Event event) {
        EventShortDto eventShortDto = modelMapper.map(event, EventShortDto.class);
        if (event.getComments() != null) {
            List<CommentDto> commentDto = event.getComments().stream()
                    .map(commentMapper::entityToDto)
                    .collect(Collectors.toList());
            eventShortDto.setComments(commentDto);
        }

        return eventShortDto;
    }

    public Event updateReqToEntity(Event event, UpdateEventRequest updateEventRequest) {
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(updateEventRequest, event);

        if(event.getState().equals(EventStates.CANCELED)) {
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
