package ru.practicum.ewm.models.compilation;

import lombok.*;
import ru.practicum.ewm.models.event.Event;

import javax.persistence.*;
import java.util.List;
import java.util.SortedSet;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "compilations")
public class Compilation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "events")
    @ElementCollection
    private List<Event> events;

    @Column(name = "pinned")
    private Boolean pinned;

    @Column(name = "title")
    private String title;
}
