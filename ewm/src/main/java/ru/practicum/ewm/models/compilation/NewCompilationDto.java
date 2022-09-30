package ru.practicum.ewm.models.compilation;

import lombok.*;
import ru.practicum.ewm.models.event.EventShortDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.SortedSet;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class NewCompilationDto {

    private SortedSet<Long> events;

    private Boolean pinned;

    @NotNull
    @NotEmpty
    @NotBlank
    private String title;

    public NewCompilationDto(String title) {
        this.title = title;
    }
}
