package ru.practicum.ewm.general.admin.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.models.comment.CommentDto;
import ru.practicum.ewm.models.comment.CommentShortDto;
import ru.practicum.ewm.models.event.AdminUpdateEventRequest;
import ru.practicum.ewm.models.event.EventFullDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/admin/events")
@Slf4j
@RequiredArgsConstructor
public class AdmEventController {
    private final AdmEventService admEventService;

    @GetMapping
    public List<EventFullDto> getEvents(@RequestParam(value = "users", required = false) String users,
                                        @RequestParam(value = "states", required = false) String states,
                                        @RequestParam(value = "categories", required = false) String categories,
                                        @RequestParam(value = "rangeStart", required = false) String rangeStart,
                                        @RequestParam(value = "rangeEnd", required = false) String rangeEnd,
                                        @RequestParam(value = "from", required = false, defaultValue = "0") String from,
                                        @RequestParam(value = "size", required = false, defaultValue = "10") String size) {
        Map<String, String> parameters = Map.of(
                "users", users,
                "states", states,
                "categories", categories,
                "rangeStart", rangeStart,
                "rangeEnd", rangeEnd,
                "from", from,
                "size", size
        );
        log.info("Get full info about events with parameters: {}", parameters.values());
        return admEventService.getEvents(parameters);
    }

    @PutMapping("/{eventId}")
    public EventFullDto editEvent(@PathVariable Long eventId, @Valid @RequestBody AdminUpdateEventRequest adminUpdateEventRequest) {
        log.info("Edit event with ID: {}", eventId);
        return admEventService.editEvent(eventId, adminUpdateEventRequest);
    }

    @PatchMapping("/{eventId}/publish")
    public EventFullDto publishEvent(@PathVariable Long eventId) {
        log.info("Publish event with ID: {}", eventId);
        return admEventService.publishEvent(eventId);
    }

    @PatchMapping("/{eventId}/reject")
    public EventFullDto rejectEvent(@PathVariable Long eventId) {
        log.info("Reject publish event with ID: {}", eventId);
        return admEventService.rejectEvent(eventId);
    }

    @PatchMapping("/{eventId}/comments/{commentId}")
    public CommentDto editComment(@PathVariable Long eventId, @PathVariable Long commentId,
                                  @Valid @RequestBody CommentShortDto commentShortDto) {
        log.info("Edit comment with ID: {}, from event with ID: {}", commentId, eventId);
        return admEventService.editComment(eventId, commentId, commentShortDto);
    }

    @DeleteMapping("/{eventId}/comments/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(@PathVariable Long eventId, @PathVariable Long commentId) {
        log.info("Admin delete comment from event {} with ID: {}", eventId, commentId);
        admEventService.deleteComment(eventId, commentId);
    }
}
