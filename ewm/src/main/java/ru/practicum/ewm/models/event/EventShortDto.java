package ru.practicum.ewm.models.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.ewm.models.category.Category;
import ru.practicum.ewm.models.user.UserShortDto;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static ru.practicum.ewm.ExploreWithMeServer.LDT_PATTERN;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class EventShortDto {
    @NotNull
    @NotEmpty
    @NotBlank
    private String annotation;

    @NotNull
    @NotEmpty
    @NotBlank
    private Category category;

    private String description;

    private int confirmedRequests;

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
    private Boolean paid;

    @NotNull
    @NotEmpty
    @NotBlank
    private String title;

    private Long views;

    public EventShortDto(String annotation, Category category, String description, LocalDateTime eventDate, UserShortDto initiator,
                         Boolean paid, String title) {
        this.annotation = annotation;
        this.category = category;
        this.description = description;
        this.eventDate = eventDate;
        this.initiator = initiator;
        this.paid = paid;
        this.title = title;
    }
}
