package ru.practicum.ewm.statistic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "")
@Slf4j
public class StatisticController {
    /*
    запись информации о том, что был обработан запрос к эндпоинту API (GET /events, GET /events/{id});
    предоставление статистики за выбранные даты по выбранному эндпоинту.
     */
}
