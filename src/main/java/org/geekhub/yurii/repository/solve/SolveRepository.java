package org.geekhub.yurii.repository.solve;

import lombok.RequiredArgsConstructor;
import org.geekhub.yurii.dto.solve.SolveUpdateDTO;
import org.geekhub.yurii.model.page.Pageable;
import org.geekhub.yurii.model.cube.Discipline;
import org.geekhub.yurii.model.solve.Solve;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class SolveRepository {

    private final NamedParameterJdbcTemplate template;

    public Optional<Solve> find(Integer id) {
        String sql = "SELECT solve_id, " +
                     "       username, " +
                     "       time, " +
                     "       status_name as status, " +
                     "       discipline_name as discipline, " +
                     "       scramble, " +
                     "       date " +
                     "FROM (SELECT * FROM solve WHERE solve_id = :id) s" +
                     "    JOIN public.user u ON s.user_id = u.user_id " +
                     "    JOIN discipline d ON d.discipline_id = s.discipline_id " +
                     "    JOIN status stat ON stat.status_id = s.status_id ";

        List<Solve> solves = template.query(sql, Map.of("id", id), new SolveRowMapper());

        if (solves.isEmpty()) return Optional.empty();

        return Optional.of(solves.get(0));
    }

    public List<Solve> getSolvesForUser(String username, Discipline discipline) {
        String sql = "SELECT solve_id, " +
                     "       username, " +
                     "       time, " +
                     "       status_name as status, " +
                     "       discipline_name as discipline, " +
                     "       scramble, " +
                     "       date " +
                     "FROM solve s " +
                     "    JOIN discipline d ON d.discipline_id = s.discipline_id " +
                     "    JOIN status stat ON stat.status_id = s.status_id " +
                     "    JOIN public.user u on u.user_id = s.user_id " +
                     "WHERE discipline_name = :discipline AND " +
                     "      username = :username " +
                     "ORDER BY date";

        Map<String, Object> params = Map.of("username", username, "discipline", discipline.toString());

        return template.query(sql, params, new SolveRowMapper());
    }

    public List<Solve> getSolvesForUser(String username, Discipline discipline, Pageable pageable) {
        String sql = "SELECT solve_id, " +
                     "       username, " +
                     "       time, " +
                     "       status_name as status, " +
                     "       discipline_name as discipline, " +
                     "       scramble, " +
                     "       date " +
                     "FROM (SELECT user_id, username FROM public.user WHERE username = :username) u " +
                     "    JOIN solve s ON u.user_id = s.user_id" +
                     "    JOIN discipline d ON d.discipline_id = s.discipline_id " +
                     "    JOIN status stat ON stat.status_id = s.status_id " +
                     "WHERE discipline_name = :discipline " +
                     "ORDER BY date " + pageable.getDirection() + " " +
                     "LIMIT :limit " +
                     "OFFSET :offset";

        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("discipline", discipline.toString());
        params.put("limit", pageable.getLimit());
        params.put("offset", pageable.getOffset());

        return template.query(sql, params, new SolveRowMapper());
    }

    public void save(Solve solve) {
        String sql = "INSERT INTO solve (user_id, time, status_id, discipline_id, scramble) " +
                     "VALUES ((SELECT user_id FROM public.user WHERE username = :username), " +
                     "        :time, " +
                     "        (SELECT status_id FROM status WHERE status_name = :status), " +
                     "        (SELECT discipline_id FROM discipline WHERE discipline_name = :discipline), " +
                     "        :scramble)";

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("username", solve.getUsername());
        parameters.put("time", solve.getTime().toString());
        parameters.put("status", solve.getStatus().toString());
        parameters.put("discipline", solve.getDiscipline().toString());
        parameters.put("scramble", solve.getScramble());

        template.update(sql, new MapSqlParameterSource(parameters), generatedKeyHolder);

        solve.setId((Integer) generatedKeyHolder.getKeys().get("solve_id"));
    }

    public void update(SolveUpdateDTO updateDTO) {
        String sql = "UPDATE solve " +
                     "SET time = :time, " +
                     "    status_id = (SELECT status_id FROM status WHERE status_name = :status) " +
                     "WHERE solve_id = :id";

        template.update(sql, Map.of(
                "id", updateDTO.getId(),
                "time", updateDTO.getTime().toString(),
                "status", updateDTO.getStatus().toString())
        );
    }

    public void delete(int id) {
        template.update("DELETE FROM solve WHERE solve_id = :id", Map.of("id", id));
    }

    public Integer getCountForUser(String username, Discipline discipline) {
        String sql = "SELECT COUNT(*) " +
                     "FROM (SELECT user_id FROM public.user WHERE username = :username) u " +
                     "    JOIN solve s ON u.user_id = s.user_id" +
                     "    JOIN discipline d on d.discipline_id = s.discipline_id " +
                     "WHERE discipline_name = :discipline";

        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("discipline", discipline.toString());

        return template.queryForObject(sql, params, Integer.class);
    }
}
