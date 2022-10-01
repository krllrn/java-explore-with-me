package ru.practicum.ewm.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.models.event.*;
import ru.practicum.ewm.models.location.Location;
import ru.practicum.ewm.models.user.UserShortDto;

@Component
public class EventMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public EventMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public EventFullDto entityToFullDto(Event event) {
        EventFullDto newEvenFullDto = modelMapper.map(event, EventFullDto.class);
        newEvenFullDto.setLocation(new Location(event.getLocation().get(0), event.getLocation().get(1)));
        newEvenFullDto.setInitiator(modelMapper.map(event.getInitiator(), UserShortDto.class));
        return newEvenFullDto;
    }

    public Event updateToEntity(Event event, AdminUpdateEventRequest adminUpdateEventRequest) {
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(adminUpdateEventRequest, event);

        return event;
    }

    public EventShortDto entityToShort(Event event) {
        return modelMapper.map(event, EventShortDto.class);
    }
}
