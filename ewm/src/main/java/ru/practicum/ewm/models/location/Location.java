package ru.practicum.ewm.models.location;

import lombok.*;

import java.io.Serializable;

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
