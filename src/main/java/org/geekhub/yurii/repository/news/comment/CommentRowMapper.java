package org.geekhub.yurii.repository.news.comment;

import org.geekhub.yurii.model.news.Comment;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentRowMapper implements RowMapper<Comment> {

    @Override
    public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
        Comment comment = new Comment();

        comment.setId(rs.getInt("comment_id"));
        comment.setNewsId(rs.getInt("news_id"));
        comment.setUsername(rs.getString("username"));
        comment.setContent(rs.getString("content"));
        comment.setDate(rs.getTimestamp("date"));

        return comment;
    }
}
