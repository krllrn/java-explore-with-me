package ru.practicum.ewm.controller;

import ru.practicum.ewm.model.EndpointHitDto;
import ru.practicum.ewm.model.ViewStats;

import java.util.List;

public interface StatisticService {
    List<ViewStats> viewStats(String  start, String end, String uris, String unique);

    void hit(EndpointHitDto endpointHitDto);
}
