package org.geekhub.yurii.dto.solve;

import lombok.Getter;
import lombok.Setter;
import org.geekhub.yurii.model.cube.Discipline;
import org.geekhub.yurii.model.solve.Time;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SolveCreateDTO {

    @NotBlank
    private String username;
    @NotNull
    private Time time;
    @NotNull
    private Discipline discipline;
    @NotBlank
    private String scramble;
}
