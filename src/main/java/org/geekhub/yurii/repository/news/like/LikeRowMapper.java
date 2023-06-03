package org.geekhub.yurii.repository.news.like;

import org.geekhub.yurii.model.news.Like;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LikeRowMapper implements RowMapper<Like> {

    @Override
    public Like mapRow(ResultSet rs, int rowNum) throws SQLException {
        Like like = new Like();

        like.setId(rs.getInt("like_id"));
        like.setNewsId(rs.getInt("news_id"));
        like.setUsername(rs.getString("username"));

        return like;
    }
}
