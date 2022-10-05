package ru.practicum.ewm.models.location;

import lombok.*;
import ru.practicum.ewm.models.event.Event;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Location implements Serializable {
    Float lat;

    Float lon;
}
