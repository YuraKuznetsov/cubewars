package org.geekhub.yurii.model.news;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class News {

    private Integer id;
    private String username;
    private String title;
    private String content;
    private List<Like> likes;
    private List<Comment> comments;
    private Timestamp date;
}
