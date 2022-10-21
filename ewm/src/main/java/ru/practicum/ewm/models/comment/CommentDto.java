package ru.practicum.ewm.models.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.ewm.models.user.UserShortDto;

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
public class CommentDto {
    private Long id;

    @NotBlank
    @NotEmpty
    @NotNull
    private Long eventId;

    @NotBlank
    @NotEmpty
    @NotNull
    private String text;

    @NotBlank
    @NotEmpty
    @NotNull
    private UserShortDto author;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = LDT_PATTERN)
    private LocalDateTime created;

    public CommentDto(String text) {
        this.text = text;
    }
}
