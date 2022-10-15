package ru.practicum.ewm.models.event;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Hit {
    private String app;

    private String uri;

    private String ip;

    private LocalDateTime timestamp;
}
