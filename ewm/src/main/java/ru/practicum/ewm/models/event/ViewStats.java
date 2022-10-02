package ru.practicum.ewm.models.event;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ViewStats {
    private String app;
    private String uri;
    private Long hits;
}
