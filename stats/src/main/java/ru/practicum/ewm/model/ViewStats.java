package ru.practicum.ewm.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ViewStats {
    private String app;
    private String uri;
    private Long hits;
}
