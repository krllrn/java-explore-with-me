package ru.practicum.ewm.models.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.ewm.models.location.Location;

import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDateTime;

import static ru.practicum.ewm.ExploreWithMeServer.LDT_PATTERN;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class AdminUpdateEventRequest {
    private String annotation;

    private Long category;

    private String description;

    @FutureOrPresent(message = "Date in past - WRONG!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = LDT_PATTERN)
    private LocalDateTime eventDate;

    private Location location;

    private Boolean paid;

    private int participantLimit;

    private Boolean requestModeration;

    private String title;
}
