package ru.practicum.ewm.model;

import lombok.*;

import java.time.LocalDateTime;

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

    private LocalDateTime timestamp;
}
