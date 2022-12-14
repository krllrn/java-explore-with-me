package ru.practicum.ewm.general.pub.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.models.event.EventShortDto;

import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/events")
@Slf4j
@RequiredArgsConstructor
public class PubEventController {
    private final PubEventService pubEventService;

    @GetMapping
    public List<EventShortDto> getEvents(@RequestParam(value = "text", required = false) String text,
                                         @RequestParam(value = "categories", required = false) String categories,
                                         @RequestParam(value = "paid", required = false) String paid,
                                         @RequestParam(value = "rangeStart", required = false) String rangeStart,
                                         @RequestParam(value = "rangeEnd", required = false) String rangeEnd,
                                         @RequestParam(value = "onlyAvailable", required = false, defaultValue = "false") String onlyAvailable,
                                         @RequestParam(value = "sort", required = false) String sort,
                                         @RequestParam(value = "from", required = false, defaultValue = "0") String from,
                                         @RequestParam(value = "size", required = false, defaultValue = "10") String size,
                                         HttpServletRequest request) throws URISyntaxException {
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
        log.info("Get events with parameters: {}, {}", parameters.keySet(), parameters.values());
        return pubEventService.getEvents(parameters, request);
    }

    @GetMapping("/{id}")
    public EventShortDto getEventById(@PathVariable Long id, HttpServletRequest request) throws URISyntaxException {
        log.info("Get event by id: {}", id);
        return pubEventService.getEventById(id, request);
    }
}
