package ru.practicum.ewm.models.location;

import lombok.*;
import ru.practicum.ewm.models.event.Event;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @OneToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(name = "lat")
    private Float lat;

    @Column(name = "lon")
    private Float lon;
}
