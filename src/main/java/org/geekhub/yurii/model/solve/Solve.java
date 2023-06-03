package org.geekhub.yurii.model.solve;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.geekhub.yurii.model.cube.Discipline;

import java.sql.Timestamp;
import java.util.Objects;

@Data
public class Solve implements Comparable<Solve> {

    private Integer id;
    private String username;
    private Time time;
    private Status status;
    private Discipline discipline;
    private String scramble;
    private Timestamp date;

    @JsonIgnore
    public int getMicroseconds() {
        return time.getTotalMicroseconds();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Solve solve = (Solve) o;

        return Objects.equals(id, solve.id);
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public int compareTo(Solve other) {
        if (status == Status.DNF && other.status != Status.DNF) return 1;
        if (status != Status.DNF && other.status == Status.DNF) return -1;

        return time.compareTo(other.time);
    }
}
