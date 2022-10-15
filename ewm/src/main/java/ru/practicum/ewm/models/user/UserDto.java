package ru.practicum.ewm.models.user;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserDto {
    @Email
    @NotBlank
    @NotEmpty
    @NotNull
    private String email;

    private long id;

    @NotBlank
    @NotEmpty
    @NotNull
    private String name;

    public UserDto(String email, String name) {
        this.email = email;
        this.name = name;
    }
}
