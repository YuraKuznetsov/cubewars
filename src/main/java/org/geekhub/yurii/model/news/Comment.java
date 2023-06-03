package org.geekhub.yurii.model.news;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Comment {

    private Integer id;
    private Integer newsId;
    private String username;
    private String content;
    private Timestamp date;
}
