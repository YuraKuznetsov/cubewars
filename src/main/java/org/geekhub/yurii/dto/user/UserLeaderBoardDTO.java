package org.geekhub.yurii.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.geekhub.yurii.model.solve.Time;

@Data
@AllArgsConstructor
public class UserLeaderBoardDTO {

    private final String username;
    private final Time ao5;
    private final int count;
}
