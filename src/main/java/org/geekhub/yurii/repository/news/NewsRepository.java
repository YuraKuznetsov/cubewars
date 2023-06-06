package org.geekhub.yurii.repository.news;

import lombok.RequiredArgsConstructor;
import org.geekhub.yurii.dto.news.NewsCreateDTO;
import org.geekhub.yurii.dto.news.NewsUpdateDTO;
import org.geekhub.yurii.model.news.News;
import org.geekhub.yurii.model.page.Pageable;
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
public class NewsRepository {

    private final NamedParameterJdbcTemplate template;

    public List<News> getNewsList(Pageable pageable) {
        String sql = "SELECT news_id, username, title, content, date " +
                     "FROM news " +
                     "    JOIN \"user\" u ON u.user_id = news.user_id " +
                     "ORDER BY date " + pageable.getDirection() + " " +
                     "LIMIT :limit " +
                     "OFFSET :offset";

        Map<String, Object> params = new HashMap<>();
        params.put("limit", pageable.getLimit());
        params.put("offset", pageable.getOffset());

        return template.query(sql, params, new NewsRowMapper());
    }

    public Integer getCount() {
        return template.queryForObject("SELECT COUNT(*) FROM news", Map.of(), Integer.class);
    }

    public Integer create(NewsCreateDTO newsCreateDTO) {
        String sql = "INSERT INTO news (user_id, title, content) " +
                     "VALUES ((SELECT user_id FROM \"user\" WHERE username = :username), " +
                     "        :title, " +
                     "        :content)";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("username", newsCreateDTO.getUsername());
        parameters.put("title", newsCreateDTO.getTitle());
        parameters.put("content", newsCreateDTO.getContent());

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        template.update(sql, new MapSqlParameterSource(parameters), generatedKeyHolder);

        return (Integer) generatedKeyHolder.getKeys().get("news_id");
    }

    public Optional<News> find(Integer id) {
        String sql = "SELECT news_id, username, title, content, date " +
                     "FROM (SELECT * FROM news WHERE news_id = :id) n " +
                     "    JOIN \"user\" u ON u.user_id = n.user_id";

        List<News> news = template.query(sql, Map.of("id", id), new NewsRowMapper());

        if (news.isEmpty()) return Optional.empty();

        return Optional.of(news.get(0));
    }

    public void update(NewsUpdateDTO newsUpdateDTO) {
        String sql = "UPDATE news " +
                     "SET title = :time, " +
                     "    content = :content " +
                     "WHERE news_id = :news_id";

        template.update(sql, Map.of(
                "news_id", newsUpdateDTO.getId(),
                "title", newsUpdateDTO.getTitle(),
                "content", newsUpdateDTO.getContent())
        );
    }

    public void delete(Integer id) {
        template.update("DELETE FROM news WHERE news_id = :id", Map.of("id", id));
    }
}
