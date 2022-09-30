package ru.practicum.ewm.models.compilation;

import lombok.*;

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
    private SortedSet<Long> events;

    @Column(name = "pinned")
    private Boolean pinned;

    @Column(name = "title")
    private String title;
}
