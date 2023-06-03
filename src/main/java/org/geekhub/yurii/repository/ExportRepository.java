package org.geekhub.yurii.repository;

import lombok.RequiredArgsConstructor;
import org.geekhub.yurii.model.cube.Discipline;
import org.geekhub.yurii.model.export.AdminStats;
import org.geekhub.yurii.model.export.UserActivity;
import org.geekhub.yurii.model.export.UserBestSolve;
import org.geekhub.yurii.model.solve.Status;
import org.geekhub.yurii.model.solve.Time;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ExportRepository {

    private final NamedParameterJdbcTemplate template;
    private final SqlReader sqlReader;

    public List<AdminStats> getAdminStats() {
        final String sql = sqlReader.read("get_admin_stats");

        return template.query(sql, (resultSet, i) -> {
            AdminStats adminStats = new AdminStats();

            adminStats.setAdmin(resultSet.getString("admin"));
            adminStats.setNewsCount(resultSet.getInt("news_count"));
            adminStats.setCommentsCount(resultSet.getInt("comments_count"));
            adminStats.setLikesCount(resultSet.getInt("likes_count"));

            return adminStats;
        });
    }

    public List<UserActivity> getUsersActivity() {
        final String sql = sqlReader.read("get_users_activity");

        return template.query(sql, (resultSet, i) -> {
            UserActivity userActivity = new UserActivity();

            userActivity.setUsername(resultSet.getString("username"));
            userActivity.setSolvesCount(resultSet.getInt("solves_count"));
            userActivity.setCommentsCount(resultSet.getInt("comments_count"));
            userActivity.setLikesCount(resultSet.getInt("likes_count"));
            userActivity.setLastActivity(resultSet.getTimestamp("last_activity"));

            return userActivity;
        });
    }

    public List<UserBestSolve> getUsersBestSolve(Discipline discipline) {
        final String sql = sqlReader.read("get_users_best_solve");

        return template.query(sql, Map.of("discipline", discipline.toString()), (resultSet, i) -> {
            UserBestSolve userBestSolve = new UserBestSolve();

            userBestSolve.setUsername(resultSet.getString("username"));
            userBestSolve.setBestTime(Time.valueOf(resultSet.getString("best_time")));
            userBestSolve.setStatus(Status.valueOf(resultSet.getString("status")));
            userBestSolve.setDate(resultSet.getTimestamp("date"));

            return userBestSolve;
        });
    }
}
