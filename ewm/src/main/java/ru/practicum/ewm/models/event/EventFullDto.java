package ru.practicum.ewm.models.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.ewm.models.category.Category;
import ru.practicum.ewm.models.comment.CommentDto;
import ru.practicum.ewm.models.location.Location;
import ru.practicum.ewm.models.user.UserShortDto;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.ewm.ExploreWithMeServer.LDT_PATTERN;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class EventFullDto {
    @NotNull
    @NotEmpty
    @NotBlank
    private String annotation;

    @NotNull
    @NotEmpty
    @NotBlank
    private Category category;

    private int confirmedRequests;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = LDT_PATTERN)
    private LocalDateTime createdOn;

    private String description;

    @NotNull
    @NotEmpty
    @NotBlank
    @FutureOrPresent(message = "Date in past - WRONG!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = LDT_PATTERN)
    private LocalDateTime eventDate;

    private Long id;

    @NotNull
    @NotEmpty
    @NotBlank
    private UserShortDto initiator;

    @NotNull
    @NotEmpty
    @NotBlank
    private Location location;

    @NotNull
    @NotEmpty
    @NotBlank
    private Boolean paid;

    private int participantLimit;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = LDT_PATTERN)
    private LocalDateTime publishedOn;

    private Boolean requestModeration;

    private EventStates state;

    @NotNull
    @NotEmpty
    @NotBlank
    private String title;

    private Long views;

    private List<CommentDto> comments;

    public EventFullDto(String annotation, Category category, LocalDateTime eventDate, UserShortDto initiator,
                        Location location, Boolean paid, String title) {
        this.annotation = annotation;
        this.category = category;
        this.eventDate = eventDate;
        this.initiator = initiator;
        this.location = location;
        this.paid = paid;
        this.title = title;
    }
}
