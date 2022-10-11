package ru.practicum.ewm.models.user;

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
public class UserShortDto {
    @NotBlank
    @NotEmpty
    @NotNull
    private long id;

    @NotBlank
    @NotEmpty
    @NotNull
    private String name;
}
