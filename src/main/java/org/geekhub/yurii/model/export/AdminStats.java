package org.geekhub.yurii.model.export;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminStats {

    private String admin;
    private Integer newsCount;
    private Integer commentsCount;
    private Integer likesCount;
}
