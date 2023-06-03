package org.geekhub.yurii.repository.user;

import lombok.RequiredArgsConstructor;
import org.geekhub.yurii.model.user.User;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final NamedParameterJdbcTemplate template;

    public Optional<User> findByUsername(String username) {
        String sql = "SELECT user_id, email, username, password, role_name as role " +
                     "FROM (SELECT * FROM public.user WHERE username = :username) as u " +
                     "    JOIN role r ON u.role_id = r.role_id ";

        List<User> users = template.query(sql, Map.of("username", username), new UserRowMapper());

        if (users.isEmpty()) return Optional.empty();

        return Optional.of(users.get(0));
    }

    public void save(User user) {
        String sql = "INSERT INTO public.user (email, username, password, role_id) " +
                     "VALUES (:email, :username, :password, (SELECT role_id FROM role WHERE role_name = :role))";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("email", user.getEmail());
        parameters.put("username", user.getUsername());
        parameters.put("password", user.getPassword());
        parameters.put("role", user.getRole().toString());

        template.update(sql, new MapSqlParameterSource(parameters));
    }

    public List<User> getAll() {
        String sql = "SELECT user_id, email, username, password, role_name as role " +
                     "FROM public.user as u" +
                     "    JOIN role r ON u.role_id = r.role_id ";

        return template.query(sql, new UserRowMapper());
    }

    public Integer getCount() {
        return template.queryForObject("SELECT COUNT(*) FROM public.user", Map.of(), Integer.class);
    }
}


