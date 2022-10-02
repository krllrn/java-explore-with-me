package ru.practicum.ewm.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.models.comment.CommentDto;
import ru.practicum.ewm.models.event.*;
import ru.practicum.ewm.models.location.Location;
import ru.practicum.ewm.models.user.User;
import ru.practicum.ewm.models.user.UserShortDto;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventMapper {
    private final ModelMapper modelMapper;
    private final CommentMapper commentMapper;

    @Autowired
    public EventMapper(ModelMapper modelMapper, @Lazy CommentMapper commentMapper) {
        this.modelMapper = modelMapper;
        this.commentMapper = commentMapper;
    }

    public EventFullDto entityToFullDto(Event event) {
        EventFullDto newEvenFullDto = modelMapper.map(event, EventFullDto.class);
        newEvenFullDto.setLocation(new Location(event.getLocation().get(0), event.getLocation().get(1)));
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
        List<CommentDto> commentDto = event.getComments().stream()
                .map(commentMapper::entityToDto)
                .collect(Collectors.toList());
        EventShortDto eventShortDto = modelMapper.map(event, EventShortDto.class);
        eventShortDto.setComments(commentDto);
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
        event.setLocation(Arrays.asList(newEventDto.getLocation().getLat(), newEventDto.getLocation().getLon()));
        event.setCreatedOn(LocalDateTime.now());
        event.setState(EventStates.PENDING);
        event.setViews(0L);

        return event;
    }
}
