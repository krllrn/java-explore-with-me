package ru.practicum.ewm.models.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

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

    private String authorName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime created;

    public CommentDto(String text) {
        this.text = text;
    }
}
