package ru.practicum.ewm.models.comment;

import lombok.*;
import ru.practicum.ewm.models.event.Event;
import ru.practicum.ewm.models.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(name = "text", nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "author")
    private User author;

    @Column(name = "created")
    private LocalDateTime created;

    public Comment(Event event, String text, User author, LocalDateTime created) {
        this.event = event;
        this.text = text;
        this.author = author;
        this.created = created;
    }
}
