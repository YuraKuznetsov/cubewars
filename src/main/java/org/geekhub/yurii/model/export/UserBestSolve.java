package org.geekhub.yurii.model.export;

import lombok.Getter;
import lombok.Setter;
import org.geekhub.yurii.model.solve.Status;
import org.geekhub.yurii.model.solve.Time;

import java.sql.Timestamp;

@Getter
@Setter
public class UserBestSolve {

    private String username;
    private Time bestTime;
    private Status status;
    private Timestamp date;
}
