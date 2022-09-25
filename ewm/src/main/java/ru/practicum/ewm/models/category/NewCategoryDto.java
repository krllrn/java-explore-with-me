package ru.practicum.ewm.models.category;

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
public class NewCategoryDto {

    @NotNull
    @NotBlank
    @NotEmpty
    private String name;
}
