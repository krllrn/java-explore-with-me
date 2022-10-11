package ru.practicum.ewm.general.reg.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.models.comment.CommentDto;
import ru.practicum.ewm.models.comment.CommentShortDto;
import ru.practicum.ewm.models.event.EventFullDto;
import ru.practicum.ewm.models.event.EventShortDto;
import ru.practicum.ewm.models.event.NewEventDto;
import ru.practicum.ewm.models.event.UpdateEventRequest;
import ru.practicum.ewm.models.request.RequestDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/users")
@Slf4j
public class RegEventController {
    private final RegEventService regEventService;

    @Autowired
    public RegEventController(RegEventService regEventService) {
        this.regEventService = regEventService;
    }

    @GetMapping("/{userId}/events")
    public List<EventShortDto> getCurrentUserEvents(@PathVariable Long userId,
                                                    @RequestParam(value = "from", required = false, defaultValue = "0") Integer from,
                                                    @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        log.info("Get events added by user with ID: {}", userId);
        return regEventService.getCurrentUserEvents(userId, from, size);
    }

    @PatchMapping("/{userId}/events")
    public EventFullDto editEvent(@PathVariable Long userId, @Valid @RequestBody UpdateEventRequest updateEventRequest){
        log.info("Edit event with ID: {}", updateEventRequest.getId());
        return regEventService.editEvent(userId, updateEventRequest);
    }

    @PostMapping("/{userId}/events")
    public EventFullDto addEvent(@PathVariable Long userId, @Valid @RequestBody NewEventDto newEventDto) {
        log.info("Add new event with title: {}", newEventDto.getTitle());
        return regEventService.addEvent(userId, newEventDto);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public EventFullDto getEventByIdCurrentUser(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("Get event with ID: {}, by user with ID: {}", eventId, userId);
        return regEventService.getEventByIdCurrentUser(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public EventFullDto cancelEvent(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("Cancel event with ID: {}, by user with ID: {}", eventId, userId);
        return regEventService.cancelEvent(userId, eventId);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public List<RequestDto> getEventRequests(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("Get event requests with ID: {}", eventId);
        return regEventService.getEventRequests(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/confirm")
    public RequestDto confirmEventRequest(@PathVariable Long userId, @PathVariable Long eventId, @PathVariable Long reqId) {
        log.info("Confirm event request with ID: {}, event ID: {}", reqId, eventId);
        return regEventService.confirmEventRequest(userId, eventId, reqId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/reject")
    public RequestDto rejectEventRequest(@PathVariable Long userId, @PathVariable Long eventId, @PathVariable Long reqId) {
        log.info("Reject event request with ID: {}, event ID: {}", reqId, eventId);
        return regEventService.rejectEventRequest(userId, eventId, reqId);
    }

    @PostMapping("/{userId}/events/{eventId}/comments")
    public CommentDto addComment(@PathVariable Long userId, @PathVariable Long eventId, @Valid @RequestBody CommentShortDto commentShortDto) {
        log.info("Add new comment to event with ID: {}", eventId);
        return regEventService.addComment(userId, eventId, commentShortDto);
    }

    @PatchMapping("/{userId}/events/{eventId}/comments/{commentId}")
    public CommentDto editComment(@PathVariable Long userId, @PathVariable Long eventId, @PathVariable Long commentId,
                                  @Valid @RequestBody CommentShortDto commentShortDto){
        log.info("Edit comment.");
        return regEventService.editComment(userId, eventId, commentId, commentShortDto);
    }

    @DeleteMapping("/{userId}/events/{eventId}/comments/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEvent(@PathVariable Long userId, @PathVariable Long eventId, @PathVariable Long commentId) {
        log.info("Delete comment with ID: {}, by user with ID: {}", commentId, userId);
        regEventService.deleteComment(userId, eventId, commentId);
    }
}
