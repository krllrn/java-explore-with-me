package ru.practicum.ewm.models.event;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Formula;
import ru.practicum.ewm.models.category.Category;
import ru.practicum.ewm.models.comment.Comment;
import ru.practicum.ewm.models.location.Location;
import ru.practicum.ewm.models.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
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
    @OneToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Transient
    @Formula(value = "SELECT COUNT(*) FROM requests WHERE requests.event_id = id")
    private int confirmedRequests;

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

    @JoinColumn(name = "location", nullable = false)
    @ElementCollection
    private List<Float> location;

    @Column(name = "paid", nullable = false)
    private Boolean paid;

    @Column(name = "participant_limit")
    private int participantLimit;

    @Column(name = "published_on")
    private LocalDateTime publishedOn;

    @Transient
    private Boolean requestModeration;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private EventStates state;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "views")
    private Long views;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

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

    public Long getViews() {
        return views;
    }

    @Override
    public int compareTo(Event o) {
        if (annotation.equals(o.annotation) && title.equals(o.title)) {
            return 0;
        } else {
            return -1;
        }
    }
}
