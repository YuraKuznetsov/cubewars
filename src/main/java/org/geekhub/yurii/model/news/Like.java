package org.geekhub.yurii.model.news;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Like {

    private Integer id;
    private Integer newsId;
    private String username;

    public Like(Integer newsId, String username) {
        this.newsId = newsId;
        this.username = username;
    }
}
