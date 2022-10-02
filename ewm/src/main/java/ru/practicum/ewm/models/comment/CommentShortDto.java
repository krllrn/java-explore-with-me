package ru.practicum.ewm.models.comment;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CommentShortDto {
    @NotBlank
    @NotEmpty
    @NotNull
    private String text;
}
