package org.geekhub.yurii.repository.solve;

import org.geekhub.yurii.model.cube.Discipline;
import org.geekhub.yurii.model.solve.Solve;
import org.geekhub.yurii.model.solve.Status;
import org.geekhub.yurii.model.solve.Time;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class SolveRowMapper implements RowMapper<Solve> {

    @Override
    public Solve mapRow(ResultSet rs, int rowNum) throws SQLException {
        Solve solve = new Solve();

        solve.setId(rs.getInt("solve_id"));
        solve.setUsername(rs.getString("username"));
        solve.setTime(Time.valueOf(rs.getString("time")));
        solve.setStatus(Status.valueOf(rs.getString("status")));
        solve.setDiscipline(Discipline.valueOf(rs.getString("discipline")));
        solve.setScramble(rs.getString("scramble"));
        solve.setDate(rs.getTimestamp("date"));

        return solve;
    }
}
