package org.geekhub.yurii.model.solve;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeTest {

    @Test
    void testValueOfString() {
        Time time = Time.valueOf("1:22.76");

        assertEquals(1, time.getMinutes());
        assertEquals(22, time.getSeconds());
        assertEquals(76, time.getMicroseconds());
    }

    @Test
    void testValueOfString_whenIncorrectStringFormat() {
        assertThrows(IllegalArgumentException.class, () -> Time.valueOf("1:22:76"));
        assertThrows(IllegalArgumentException.class, () -> Time.valueOf("22:76"));
        assertThrows(IllegalArgumentException.class, () -> Time.valueOf("4:100.76"));
    }

    @Test
    void testValueOfInt() {
        Time time = Time.valueOf(18523);

        assertEquals(3, time.getMinutes());
        assertEquals(5, time.getSeconds());
        assertEquals(23, time.getMicroseconds());
    }

    @Test
    void testValueOfInt_whenIncorrectIntFormat() {
        assertThrows(IllegalArgumentException.class, () -> Time.valueOf(-1));
    }

    @Test
    void testToString() {
        assertEquals("5:08.93", Time.valueOf("5:08.93").toString());
        assertEquals("0:08.47", Time.valueOf(847).toString());
    }

    @Test
    void testGetTotalMicroseconds() {
        assertEquals(31893, Time.valueOf("5:18.93").getTotalMicroseconds());
        assertEquals(847, Time.valueOf(847).getTotalMicroseconds());
    }
}