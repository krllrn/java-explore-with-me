package ru.practicum.ewm.general.pub.events;

import ru.practicum.ewm.models.event.EventShortDto;

import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public interface PubEventService {
    List<EventShortDto> getEvents(Map<String, String> parameters, HttpServletRequest request) throws URISyntaxException;

    EventShortDto getEventById(Long id, HttpServletRequest request) throws URISyntaxException;
}
