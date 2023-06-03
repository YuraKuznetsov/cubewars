package org.geekhub.yurii.dto.solve;

import lombok.Getter;
import lombok.Setter;
import org.geekhub.yurii.model.solve.Status;
import org.geekhub.yurii.model.solve.Time;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SolveUpdateDTO {

    @NotNull
    private Integer id;
    @NotNull
    private Time time;
    @NotNull
    private Status status;
}
