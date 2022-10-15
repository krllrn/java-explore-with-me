package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.model.EndpointHit;
import ru.practicum.ewm.model.EndpointHitDto;
import ru.practicum.ewm.model.ViewStats;
import ru.practicum.ewm.repository.StatisticRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static ru.practicum.ewm.ExploreWithMeStats.LDT_PATTERN;

@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {
    private final StatisticRepository statisticRepository;

    @Override
    public List<ViewStats> viewStats(String start, String end, String uris, String unique) {
        LocalDateTime startDate = LocalDateTime.parse(start, DateTimeFormatter.ofPattern(LDT_PATTERN));
        LocalDateTime endDate = LocalDateTime.parse(end, DateTimeFormatter.ofPattern(LDT_PATTERN));
        List<ViewStats> viewStatsList = new ArrayList<>();
        Map<String, List<EndpointHit>> hitsMap = new HashMap<>();
        if (unique == null || unique.equals("false")) {
            List<EndpointHit> endpointHits = statisticRepository.findAll().stream()
                    .filter(h -> h.getTimestamp().isAfter(startDate) && h.getTimestamp().isBefore(endDate))
                    .collect(Collectors.toList());
            if (uris != null) {
                String[] urisList = uris.split(",");
                for (EndpointHit h : endpointHits) {
                    for (String s : urisList) {
                        if (!h.getUri().equals(s)) {
                            endpointHits.remove(h);
                        }
                    }
                }
            }
            for (EndpointHit h : endpointHits) {
                if (hitsMap.get(h.getApp()) == null) {
                    List<EndpointHit> list = new ArrayList<>();
                    list.add(h);
                    hitsMap.put(h.getApp(), list);
                } else {
                    List<EndpointHit> list = hitsMap.get(h.getApp());
                    list.add(h);
                    hitsMap.put(h.getApp(), list);
                }
            }
            for (String s : hitsMap.keySet()) {
                viewStatsList.add(new ViewStats(s, hitsMap.get(s).get(0).getUri(), (long) hitsMap.get(s).size()));
            }
        }
        if (unique != null && unique.equals("true")) {
            Set<EndpointHit> uniqueSet = statisticRepository.findAll().stream()
                    .filter(h -> h.getTimestamp().isAfter(startDate) && h.getTimestamp().isBefore(endDate))
                    .collect(Collectors.toCollection(TreeSet::new));
            if (uris != null) {
                String[] urisList = uris.split(",");
                for (EndpointHit h : uniqueSet) {
                    for (String s : urisList) {
                        if (!h.getUri().equals(s)) {
                            uniqueSet.remove(h);
                        }
                    }
                }
            }
            for (EndpointHit h : uniqueSet) {
                if (hitsMap.get(h.getApp()) == null) {
                    List<EndpointHit> list = new ArrayList<>();
                    list.add(h);
                    hitsMap.put(h.getApp(), list);
                } else {
                    List<EndpointHit> list = hitsMap.get(h.getApp());
                    list.add(h);
                    hitsMap.put(h.getApp(), list);
                }
            }
            for (String s : hitsMap.keySet()) {
                viewStatsList.add(new ViewStats(s, hitsMap.get(s).get(0).getUri(), (long) hitsMap.get(s).size()));
            }
        }
        return viewStatsList;
    }

    @Override
    public void hit(EndpointHitDto endpointHitDto) {
        EndpointHit endpointHit = new EndpointHit();
        if (endpointHitDto.getApp() != null) {
            endpointHit.setApp(endpointHitDto.getApp());
        }
        if (endpointHitDto.getUri() != null) {
            endpointHit.setUri(endpointHitDto.getUri());
        }
        if (endpointHitDto.getIp() != null) {
            endpointHit.setIp(endpointHitDto.getIp());
        }
        if (endpointHitDto.getTimestamp() != null) {
            endpointHit.setTimestamp(endpointHitDto.getTimestamp());
        } else {
            endpointHit.setTimestamp(LocalDateTime.now());
        }
        statisticRepository.save(endpointHit);
    }
}
