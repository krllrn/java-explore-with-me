package ru.practicum.ewm.statistic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.practicum.ewm.models.event.Hit;
import ru.practicum.ewm.models.event.ViewStats;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class Statistic {
    @Value("${stat-server.url}")
    String statisticUri;
    private final RestTemplate restTemplate;

    @Autowired
    public Statistic(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<ViewStats> getViews(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        String startEnc = URLEncoder.encode(start.toString(), StandardCharsets.UTF_8);
        String endEnc = URLEncoder.encode(end.toString(), StandardCharsets.UTF_8);
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(statisticUri + "/stats")
            .queryParam("start", startEnc)
            .queryParam("end", endEnc);

        if (uris != null && uris.size() > 0) {
            builder.queryParam("uris", uris);
        }
        if (unique != null) {
            builder.queryParam("unique", unique);
        }
        ResponseEntity<ViewStats[]> response = restTemplate.getForEntity(builder.build().toUri(), ViewStats[].class);
        return List.of(Objects.requireNonNull(response.getBody()));
    }

    public void hit(Hit hit) {
        HttpEntity<Hit> request = new HttpEntity<>(hit);
        restTemplate.postForObject(statisticUri + "/hit", request, Hit.class);
    }
}
