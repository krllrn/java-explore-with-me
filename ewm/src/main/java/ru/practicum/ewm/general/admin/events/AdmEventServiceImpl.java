package ru.practicum.ewm.general.admin.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.models.event.AdminUpdateEventRequest;
import ru.practicum.ewm.models.event.EventFullDto;
import ru.practicum.ewm.repositories.EventRepository;

import java.util.List;
import java.util.Map;

@Service
public class AdmEventServiceImpl implements AdmEventService {

    private final EventRepository eventRepository;

    @Autowired
    public AdmEventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public List<EventFullDto> getEvents(Map<String, Object> parameters) {
        return null;
    }

    @Override
    public EventFullDto editEvent(Long eventId, AdminUpdateEventRequest adminUpdateEventRequest) {
        return null;
    }

    @Override
    public EventFullDto publishEvent(Long eventId) {
        return null;
    }

    @Override
    public EventFullDto rejectEvent(Long eventId) {
        return null;
    }
}
