package ru.practicum.ewm.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.model.EndpointHitDto;
import ru.practicum.ewm.model.ViewStats;

import java.util.List;

@RestController
@Slf4j
public class StatisticController {
    private final StatisticService statisticService;

    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @GetMapping("/stats")
    public List<ViewStats> viewStats(@RequestParam(value = "start") String start, @RequestParam(value = "end") String end,
                                     @RequestParam(value = "ids", required = false) String uris,
                                     @RequestParam(value = "unique", required = false, defaultValue = "false") String unique) {
        log.info("Get statistics.");
        return statisticService.viewStats(start, end, uris, unique);
    }

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.OK)
    public void hit(@RequestBody EndpointHitDto endpointHitDto) {
        log.info("Post info about view.");
        statisticService.hit(endpointHitDto);
    }
}
