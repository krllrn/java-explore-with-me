package ru.practicum.ewm.general.reg.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.models.event.EventFullDto;
import ru.practicum.ewm.models.event.EventShortDto;
import ru.practicum.ewm.models.event.NewEventDto;
import ru.practicum.ewm.models.event.UpdateEventRequest;
import ru.practicum.ewm.models.request.RequestDto;

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

    /*
    изменить можно только отмененные события или события в состоянии ожидания модерации
    если редактируется отменённое событие, то оно автоматически переходит в состояние ожидания модерации
    дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента
     */
    @PatchMapping("/{userId}/events")
    public EventFullDto editEvent(@PathVariable Long userId, @RequestBody UpdateEventRequest updateEventRequest){
        log.info("Edit event with ID: {}", updateEventRequest.getId());
        return regEventService.editEvent(userId, updateEventRequest);
    }

    /*
    Обратите внимание: дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента
     */
    @PostMapping("/{userId}/events")
    public EventFullDto addEvent(@PathVariable Long userId, @RequestBody NewEventDto newEventDto) {
        log.info("Add new event with title: {}", newEventDto.getTitle());
        return regEventService.addEvent(userId, newEventDto);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public EventFullDto getEventByIdCurrentUser(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("Get event with ID: {}, by user with ID: {}", eventId, userId);
        return regEventService.getEventByIdCurrentUser(userId, eventId);
    }

    /*
    Обратите внимание: Отменить можно только событие в состоянии ожидания модерации.
     */
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

    /*
    если для события лимит заявок равен 0 или отключена пре-модерация заявок, то подтверждение заявок не требуется
    нельзя подтвердить заявку, если уже достигнут лимит по заявкам на данное событие
    если при подтверждении данной заявки, лимит заявок для события исчерпан, то все неподтверждённые заявки необходимо отклонить
    */
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
}
