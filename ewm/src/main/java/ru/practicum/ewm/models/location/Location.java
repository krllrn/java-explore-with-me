package ru.practicum.ewm.models.location;

import lombok.*;
import ru.practicum.ewm.models.event.Event;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Location {

    private Float lat;

    private Float lon;
}
