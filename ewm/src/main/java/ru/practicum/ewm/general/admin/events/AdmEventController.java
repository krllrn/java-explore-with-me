package ru.practicum.ewm.general.admin.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.models.event.AdminUpdateEventRequest;
import ru.practicum.ewm.models.event.EventFullDto;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/admin/events")
@Slf4j
public class AdmEventController {

    private final AdmEventService admEventService;

    @Autowired
    public AdmEventController(AdmEventService admEventService) {
        this.admEventService = admEventService;
    }

    @GetMapping
    public List<EventFullDto> getEvents(@RequestParam(value = "users", required = false) List<Integer> users,
                                        @RequestParam(value = "states", required = false) List<String> states,
                                        @RequestParam(value = "categories", required = false) List<Integer> categories,
                                        @RequestParam(value = "rangeStart", required = false) String rangeStart,
                                        @RequestParam(value = "rangeEnd", required = false) String rangeEnd,
                                        @RequestParam(value = "from", required = false, defaultValue = "0") Integer from,
                                        @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        Map<String, Object> parameters = Map.of(
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

    /*
    Редактирование данных любого события администратором. Валидация данных не требуется.
     */
    @PutMapping("/{eventId}")
    public EventFullDto editEvent(@PathVariable Long eventId, @RequestBody AdminUpdateEventRequest adminUpdateEventRequest) {
        log.info("Edit event with ID: {}", eventId);
        return admEventService.editEvent(eventId, adminUpdateEventRequest);
    }

    /*
    дата начала события должна быть не ранее чем за час от даты публикации.
    событие должно быть в состоянии ожидания публикации
     */
    @PatchMapping("/{eventId}/publish")
    public EventFullDto publishEvent(@PathVariable Long eventId) {
        log.info("Publish event with ID: {}", eventId);
        return admEventService.publishEvent(eventId);
    }

    /*
    событие не должно быть опубликовано.
    */
    @PatchMapping("/{eventId}/reject")
    public EventFullDto rejectEvent(@PathVariable Long eventId) {
        log.info("Reject publish event with ID: {}", eventId);
        return admEventService.rejectEvent(eventId);
    }
}
