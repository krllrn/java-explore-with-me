package ru.practicum.ewm.general.admin.events;

import ru.practicum.ewm.models.event.AdminUpdateEventRequest;
import ru.practicum.ewm.models.event.EventFullDto;
import ru.practicum.ewm.models.event.EventShortDto;

import java.util.List;
import java.util.Map;

public interface AdmEventService {
    List<EventFullDto> getEvents(Map<String, Object> parameters);

    EventFullDto editEvent(Long eventId, AdminUpdateEventRequest adminUpdateEventRequest);

    EventFullDto publishEvent(Long eventId);

    EventFullDto rejectEvent(Long eventId);
}
