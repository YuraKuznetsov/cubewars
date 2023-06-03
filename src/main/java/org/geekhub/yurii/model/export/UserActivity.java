package org.geekhub.yurii.model.export;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class UserActivity {

    private String username;
    private int solvesCount;
    private int commentsCount;
    private int likesCount;
    private Timestamp lastActivity;
}
