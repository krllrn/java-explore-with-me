package ru.practicum.ewm.models.comment;

import lombok.*;

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

    @JoinColumn(name = "event_id")
    private Long eventId;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "author_name")
    private String authorName;

    @Column(name = "created")
    private LocalDateTime created;
}
