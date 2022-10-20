package ru.practicum.ewm.general.admin.events;

import ru.practicum.ewm.models.comment.CommentDto;
import ru.practicum.ewm.models.comment.CommentShortDto;
import ru.practicum.ewm.models.event.AdminUpdateEventRequest;
import ru.practicum.ewm.models.event.EventFullDto;

import java.util.List;
import java.util.Map;

public interface AdmEventService {
    List<EventFullDto> getEvents(Map<String, String> parameters);

    EventFullDto editEvent(Long eventId, AdminUpdateEventRequest adminUpdateEventRequest);

    EventFullDto publishEvent(Long eventId);

    EventFullDto rejectEvent(Long eventId);

    CommentDto editComment(Long eventId, Long commentId, CommentShortDto commentShortDto);

    void deleteComment(Long eventId, Long commentId);
}
