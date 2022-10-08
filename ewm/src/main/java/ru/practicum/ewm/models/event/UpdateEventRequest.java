package ru.practicum.ewm.models.event;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UpdateEventRequest {
    private String annotation;

    private Long category;

    private String description;

    @FutureOrPresent(message = "Date in past - WRONG!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    @JsonAlias({"eventId"})
    private Long id;

    private Boolean paid;

    private int participantLimit;

    private String title;
}
