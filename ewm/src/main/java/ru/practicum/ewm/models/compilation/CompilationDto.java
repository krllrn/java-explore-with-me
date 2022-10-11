package ru.practicum.ewm.models.compilation;

import lombok.*;
import ru.practicum.ewm.models.event.EventShortDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CompilationDto {
    private List<EventShortDto> events;

    @NotNull
    @NotEmpty
    @NotBlank
    private Long id;

    @NotNull
    @NotEmpty
    @NotBlank
    private Boolean pinned;

    @NotNull
    @NotEmpty
    @NotBlank
    private String title;

}
