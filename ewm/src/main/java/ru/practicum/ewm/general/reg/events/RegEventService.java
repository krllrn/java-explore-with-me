package ru.practicum.ewm.general.reg.events;

import ru.practicum.ewm.models.event.EventFullDto;
import ru.practicum.ewm.models.event.EventShortDto;
import ru.practicum.ewm.models.event.NewEventDto;
import ru.practicum.ewm.models.event.UpdateEventRequest;
import ru.practicum.ewm.models.request.RequestDto;

import java.util.List;

public interface RegEventService {
    List<EventShortDto> getCurrentUserEvents(Long userId, Integer from, Integer size);

    EventFullDto editEvent(Long userId, UpdateEventRequest updateEventRequest);

    EventFullDto addEvent(Long userId, NewEventDto newEventDto);

    EventFullDto getEventByIdCurrentUser(Long userId, Long eventId);

    EventFullDto cancelEvent(Long userId, Long eventId);

    List<RequestDto> getEventRequests(Long userId, Long eventId);

    RequestDto confirmEventRequest(Long userId, Long eventId, Long reqId);

    RequestDto rejectEventRequest(Long userId, Long eventId, Long reqId);
}
