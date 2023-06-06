package org.geekhub.yurii.repository.news.like;

import lombok.RequiredArgsConstructor;
import org.geekhub.yurii.model.news.Like;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class LikeRepository {

    private final NamedParameterJdbcTemplate template;

    public List<Like> getLikesForNews(Integer newsId) {
        String sql = "SELECT like_id, news_id, username " +
                     "FROM (SELECT * FROM \"like\" WHERE news_id = :news_id) l " +
                     "    JOIN \"user\" u ON l.user_id = u.user_id ";

        return template.query(sql, Map.of("news_id", newsId), new LikeRowMapper());
    }

    public void create(Like like) {
        String sql = "INSERT INTO \"like\" (news_id, user_id) " +
                     "VALUES (:news_id, (SELECT user_id FROM \"user\" WHERE username = :username))";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("news_id", like.getNewsId());
        parameters.put("username", like.getUsername());

        template.update(sql, parameters);
    }

    public void delete(Like like) {
        String sql = "DELETE FROM \"like\" " +
                     "WHERE news_id = :news_id AND " +
                     "      user_id = (SELECT user_id FROM \"user\" WHERE username = :username)";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("news_id", like.getNewsId());
        parameters.put("username", like.getUsername());

        template.update(sql, parameters);
    }

    public Boolean isDuplicatedLike(Like like) {
        String sql = "SELECT COUNT(*) " +
                     "FROM \"like\" " +
                     "WHERE news_id = :news_id AND " +
                     "      user_id = (SELECT user_id FROM \"user\" WHERE username = :username)";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("news_id", like.getNewsId());
        parameters.put("username", like.getUsername());

        Integer count = template.queryForObject(sql, parameters, Integer.class);

        return count != null && count > 0;
    }
}
