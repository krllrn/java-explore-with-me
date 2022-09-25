package ru.practicum.ewm.models.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.ewm.models.category.Category;
import ru.practicum.ewm.models.location.Location;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class NewEventDto {

    @NotNull
    @NotBlank
    @NotEmpty
    private String annotation;

    @NotNull
    @NotBlank
    @NotEmpty
    private Category category;

    @NotNull
    @NotBlank
    @NotEmpty
    private String description;

    @NotNull
    @NotBlank
    @NotEmpty
    @FutureOrPresent(message = "Date in past - WRONG!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime eventDate;

    @NotNull
    @NotBlank
    @NotEmpty
    private Location location;

    private Boolean paid;

    private int participantLimit;

    private Boolean requestModeration;

    @NotNull
    @NotBlank
    @NotEmpty
    private String title;

    public NewEventDto(String annotation, Category category, String description, LocalDateTime eventDate, Location location, String title) {
        this.annotation = annotation;
        this.category = category;
        this.description = description;
        this.eventDate = eventDate;
        this.location = location;
        this.title = title;
    }
}
