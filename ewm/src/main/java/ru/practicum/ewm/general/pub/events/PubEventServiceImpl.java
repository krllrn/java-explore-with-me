package ru.practicum.ewm.general.pub.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.models.event.EventShortDto;
import ru.practicum.ewm.repositories.EventRepository;

import java.util.List;
import java.util.Map;

@Service
public class PubEventServiceImpl implements PubEventService {

    private final EventRepository eventRepository;

    @Autowired
    public PubEventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public List<EventShortDto> getEvents(Map<String, Object> parameters) {
        return null;
    }

    @Override
    public EventShortDto getEventById(Long id) {
        return null;
    }
}
