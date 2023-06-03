package org.geekhub.yurii.model.solve;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

@Getter
public class Time implements Comparable<Time> {

    @JsonIgnore
    private final int minutes;
    @JsonIgnore
    private final int seconds;
    @JsonIgnore
    private final int microseconds;

    private Time(int minutes, int seconds, int microseconds) {
        if (seconds > 59 || microseconds > 99) throw new IllegalArgumentException();

        this.minutes = minutes;
        this.seconds = seconds;
        this.microseconds = microseconds;
    }

    public static Time valueOf(int totalMicroseconds) {
        if (totalMicroseconds < 0) throw new IllegalArgumentException();

        int minutes = totalMicroseconds / 6000;
        int seconds = (totalMicroseconds - minutes * 6000) / 100;
        int microseconds = totalMicroseconds - minutes * 6000 - seconds * 100;

        return new Time(minutes, seconds, microseconds);
    }

    public static Time valueOf(String s) {
        if (s == null) throw new IllegalArgumentException();

        int minute;
        int second;
        int microseconds;

        int firstColon = s.indexOf(':');
        int secondColon = s.indexOf('.', firstColon + 1);
        int len = s.length();

        if (firstColon < 0 || secondColon < 0 || secondColon > len - 1) {
            throw new IllegalArgumentException();
        }

        minute = Integer.parseInt(s, 0, firstColon, 10);
        second = Integer.parseInt(s, firstColon + 1, secondColon, 10);
        microseconds = Integer.parseInt(s, secondColon + 1, len, 10);

        return new Time(minute, second, microseconds);
    }

    @JsonIgnore
    public int getTotalMicroseconds() {
        return microseconds + seconds * 100 + minutes * 6000;
    }

    public float getTotalSeconds() {
        return minutes * 60 + seconds + (float) microseconds / 100;
    }

    public String getStringFormat() {
        return String.format("%s:%02d.%02d", minutes, seconds, microseconds);
    }

    @Override
    public String toString() {
        return String.format("%s:%02d.%02d", minutes, seconds, microseconds);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Time time = (Time) o;

        if (minutes != time.minutes) return false;
        if (seconds != time.seconds) return false;
        return microseconds == time.microseconds;
    }

    @Override
    public int hashCode() {
        int result = minutes;
        result = 31 * result + seconds;
        result = 31 * result + microseconds;
        return result;
    }

    @Override
    public int compareTo(Time o) {
        return Integer.compare(getTotalMicroseconds(), o.getTotalMicroseconds());
    }
}
