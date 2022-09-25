package ru.practicum.ewm.general.reg.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.models.event.EventFullDto;
import ru.practicum.ewm.models.event.EventShortDto;
import ru.practicum.ewm.models.event.NewEventDto;
import ru.practicum.ewm.models.event.UpdateEventRequest;
import ru.practicum.ewm.models.request.RequestDto;
import ru.practicum.ewm.repositories.EventRepository;
import ru.practicum.ewm.repositories.RequestRepository;

import java.util.List;

@Service
public class RegEventServiceImpl implements RegEventService {

    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;

    @Autowired
    public RegEventServiceImpl(EventRepository eventRepository, RequestRepository requestRepository) {
        this.eventRepository = eventRepository;
        this.requestRepository = requestRepository;
    }

    @Override
    public List<EventShortDto> getCurrentUserEvents(Long userId, Integer from, Integer size) {
        return null;
    }

    @Override
    public EventFullDto editEvent(Long userId, UpdateEventRequest updateEventRequest) {
        return null;
    }

    @Override
    public EventFullDto addEvent(Long userId, NewEventDto newEventDto) {
        return null;
    }

    @Override
    public EventFullDto getEventByIdCurrentUser(Long userId, Long eventId) {
        return null;
    }

    @Override
    public EventFullDto cancelEvent(Long userId, Long eventId) {
        return null;
    }

    @Override
    public List<RequestDto> getEventRequests(Long userId, Long eventId) {
        return null;
    }

    @Override
    public RequestDto confirmEventRequest(Long userId, Long eventId, Long reqId) {
        return null;
    }

    @Override
    public RequestDto rejectEventRequest(Long userId, Long eventId, Long reqId) {
        return null;
    }
}
