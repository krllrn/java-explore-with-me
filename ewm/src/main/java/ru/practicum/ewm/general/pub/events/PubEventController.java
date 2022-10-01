package ru.practicum.ewm.general.pub.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.models.event.EventShortDto;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/events")
@Slf4j
public class PubEventController {
    /*
    Сортировка списка событий должна быть организована либо по количеству просмотров, которое должно запрашиваться в сервисе статистики, либо по датам событий.
    При просмотре списка событий возвращается только краткая информация о мероприятиях.
    Просмотр подробной информации о конкретном событии нужно настроить отдельно (через отдельный эндпоинт).
    Каждое событие должно относиться к какой-то из закреплённых в приложении категорий.
    Должна быть настроена возможность получения всех имеющихся категорий и подборок событий (такие подборки будут составлять администраторы ресурса).
    Каждый публичный запрос для получения списка событий или полной информации о мероприятии должен фиксироваться сервисом статистики.
     */

    private final PubEventService pubEventService;

    @Autowired
    public PubEventController(PubEventService pubEventService) {
        this.pubEventService = pubEventService;
    }


    @GetMapping
    public List<EventShortDto> getEvents(@RequestParam(value = "text", required = false) String text,
                                         @RequestParam(value = "categories", required = false) String categories,
                                         @RequestParam(value = "paid", required = false) String paid,
                                         @RequestParam(value = "rangeStart", required = false) String rangeStart,
                                         @RequestParam(value = "rangeEnd", required = false) String rangeEnd,
                                         @RequestParam(value = "onlyAvailable", required = false, defaultValue = "false") String onlyAvailable,
                                         @RequestParam(value = "sort", required = false) String sort,
                                         @RequestParam(value = "from", required = false, defaultValue = "0") String from,
                                         @RequestParam(value = "size", required = false, defaultValue = "10") String size) {
        Map<String, String> parameters = Map.of(
                "text", text,
                "categories", categories,
                "paid", paid,
                "rangeStart", rangeStart,
                "rangeEnd", rangeEnd,
                "onlyAvailable", onlyAvailable,
                "sort", sort,
                "from", from,
                "size", size
        );
        log.info("Get events with parameters: {}", parameters.values());
        return pubEventService.getEvents(parameters);
    }

    @GetMapping("/{id}")
    public EventShortDto getEventById(@PathVariable Long id) {
        log.info("Get event by id: {}", id);
        return pubEventService.getEventById(id);
    }
}
