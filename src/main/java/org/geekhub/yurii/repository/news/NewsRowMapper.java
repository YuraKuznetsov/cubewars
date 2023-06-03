package org.geekhub.yurii.repository.news;

import org.geekhub.yurii.model.news.News;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NewsRowMapper implements RowMapper<News> {

    @Override
    public News mapRow(ResultSet rs, int rowNum) throws SQLException {
        News news = new News();

        news.setId(rs.getInt("news_id"));
        news.setUsername(rs.getString("username"));
        news.setTitle(rs.getString("title"));
        news.setContent(rs.getString("content"));
        news.setDate(rs.getTimestamp("date"));

        return news;
    }
}
