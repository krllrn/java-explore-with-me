package ru.practicum.ewm.general.pub.events;

import ru.practicum.ewm.models.event.EventShortDto;

import java.util.List;
import java.util.Map;

public interface PubEventService {
    List<EventShortDto> getEvents(Map<String, Object> parameters);

    EventShortDto getEventById(Long id);
}
