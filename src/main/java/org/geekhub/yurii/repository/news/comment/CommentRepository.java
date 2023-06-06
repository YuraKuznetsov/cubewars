package org.geekhub.yurii.repository.news.comment;

import lombok.RequiredArgsConstructor;
import org.geekhub.yurii.dto.news.comment.CommentCreateDTO;
import org.geekhub.yurii.dto.news.comment.CommentUpdateDTO;
import org.geekhub.yurii.model.news.Comment;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final NamedParameterJdbcTemplate template;

    public List<Comment> getCommentsForNews(Integer newsId) {
        String sql = "SELECT comment_id, news_id, username, content, date " +
                     "FROM (SELECT * FROM comment WHERE news_id = :news_id) c " +
                     "    JOIN \"user\" u ON c.user_id = u.user_id " +
                     "ORDER BY date";

        return template.query(sql, Map.of("news_id", newsId), new CommentRowMapper());
    }

    public Integer create(CommentCreateDTO commentCreateDTO) {
        String sql = "INSERT INTO comment (news_id, user_id, content) " +
                     "VALUES (:news_id, " +
                     "        (SELECT user_id FROM \"user\" WHERE username = :username), " +
                     "        :content)";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("news_id", commentCreateDTO.getNewsId());
        parameters.put("username", commentCreateDTO.getUsername());
        parameters.put("content", commentCreateDTO.getContent());

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        template.update(sql, new MapSqlParameterSource(parameters), generatedKeyHolder);

        return (Integer) generatedKeyHolder.getKeys().get("comment_id");
    }

    public Integer getCommentsCountInLastMinute(String username) {
        String sql = "SELECT COUNT(*) " +
                     "FROM comment " +
                     "    JOIN \"user\" u ON comment.user_id = u.user_id " +
                     "WHERE username = :username AND " +
                     "      date > now() - interval '1 minute'";

        return template.queryForObject(sql, Map.of("username", username), Integer.class);
    }

    public void update(CommentUpdateDTO commentUpdateDTO) {
        String sql = "UPDATE comment " +
                     "SET content = :content " +
                     "WHERE comment_id = :comment_id";

        template.update(sql, Map.of(
                "comment_id", commentUpdateDTO.getId(),
                "content", commentUpdateDTO.getContent())
        );
    }

    public void delete(Integer id) {
        template.update("DELETE FROM comment WHERE comment_id = :id", Map.of("id", id));
    }

    public Optional<Comment> find(Integer id) {
        String sql = "SELECT comment_id, news_id, username, content, date " +
                     "FROM (SELECT * FROM comment WHERE comment_id = :id) c " +
                     "    JOIN \"user\" u ON u.user_id = c.user_id";

        List<Comment> comments = template.query(sql, Map.of("id", id), new CommentRowMapper());

        if (comments.isEmpty()) return Optional.empty();

        return Optional.of(comments.get(0));
    }
}
