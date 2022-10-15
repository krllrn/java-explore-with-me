package ru.practicum.ewm.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

import static ru.practicum.ewm.ExploreWithMeStats.LDT_PATTERN;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class EndpointHitDto {
    private String app;

    private String uri;

    private String ip;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = LDT_PATTERN)
    private LocalDateTime timestamp;
}
