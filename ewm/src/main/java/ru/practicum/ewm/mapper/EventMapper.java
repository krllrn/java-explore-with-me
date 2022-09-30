package ru.practicum.ewm.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.models.event.Event;
import ru.practicum.ewm.models.event.EventFullDto;

@Component
public class EventMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public EventMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public EventFullDto entityToFullDto(Event event) {
        return modelMapper.map(event, EventFullDto.class);
    }
}
