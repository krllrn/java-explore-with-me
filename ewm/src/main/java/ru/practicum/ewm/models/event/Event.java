package ru.practicum.ewm.models.event;

import lombok.*;
import org.hibernate.Hibernate;
import ru.practicum.ewm.models.category.Category;
import ru.practicum.ewm.models.location.Location;
import ru.practicum.ewm.models.location.LocationConverter;
import ru.practicum.ewm.models.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "events")
public class Event implements Comparable<Event> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "annotation", nullable = false)
    private String annotation;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "description")
    private String description;

    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "initiator", nullable = false)
    private User initiator;

    @Column(name = "location")
    @Convert(converter = LocationConverter.class)
    private Location location;

    @Column(name = "paid", nullable = false)
    @Convert(converter = BooleanConverter.class)
    private Boolean paid;

    @Column(name = "participant_limit")
    private int participantLimit;

    @Column(name = "published_on")
    private LocalDateTime publishedOn;

    @Column(name = "request_moderation")
    @Convert(converter = BooleanConverter.class)
    private Boolean requestModeration;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private EventStates state;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "views")
    private Long views;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Event event = (Event) o;
        return id != null && Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public int compareTo(Event o) {
        if (annotation.equals(o.annotation) && title.equals(o.title)) {
            return 0;
        } else {
            return -1;
        }
    }

    public Event(String annotation, Category category, LocalDateTime createdOn, String description, LocalDateTime eventDate,
                 User initiator, Location location, Boolean paid, int participantLimit, Boolean requestModeration, String title) {
        this.annotation = annotation;
        this.category = category;
        this.createdOn = createdOn;
        this.description = description;
        this.eventDate = eventDate;
        this.initiator = initiator;
        this.location = location;
        this.paid = paid;
        this.participantLimit = participantLimit;
        this.requestModeration = requestModeration;
        this.title = title;
    }
}
