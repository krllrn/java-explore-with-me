package ru.practicum.ewm.models.compilation;

import lombok.*;

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
public class CompilationDto {

    private SortedSet<Long> events;

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
