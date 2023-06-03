package org.geekhub.yurii.service.cube;

import org.geekhub.yurii.exception.ScrambleFormatException;
import org.geekhub.yurii.model.cube.move.CubeMove;
import org.geekhub.yurii.model.cube.move.MoveDirection;
import org.geekhub.yurii.model.cube.move.MovePlane;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScrambleConverterTest {

    ScrambleConverter scrambleConverter = new ScrambleConverter();

    @Test
    void throwScrambleFormatException_whenIncomprehensibleLayer() {
        String scramble = "-3L2' Rw";

        assertThrows(ScrambleFormatException.class, () -> scrambleConverter.toList(scramble));
    }

    @Test
    void throwScrambleFormatException_whenIncomprehensiblePlane() {
        String scramble = "BD' F2";

        assertThrows(ScrambleFormatException.class, () -> scrambleConverter.toList(scramble));
    }

    @Test
    void throwScrambleFormatException_whenIncomprehensibleDirection() {
        String scramble = "B2 R L*";

        assertThrows(ScrambleFormatException.class, () -> scrambleConverter.toList(scramble));
    }

    @Test
    void throwScrambleFormatException_whenSmallLetters() {
        String scramble = "u' r2";

        assertThrows(ScrambleFormatException.class, () -> scrambleConverter.toList(scramble));
    }

    @Test
    void returnEmptyList_whenEmptyString() {
        String scramble = "";

        assertEquals(List.of(), scrambleConverter.toList(scramble));
    }

    @Test
    void testToList_whenSpacesAround() {
        String scramble = " U' Rw2 ";

        List<CubeMove> expected = new ArrayList<>();
        expected.add(new CubeMove(MovePlane.TOP, MoveDirection.ANTICLOCKWISE, 1));
        expected.add(new CubeMove(MovePlane.RIGHT, MoveDirection.LONG, 2));

        assertEquals(expected, scrambleConverter.toList(scramble));
    }

    @Test
    void testToList() {
        List<CubeMove> expectedScramble = new ArrayList<>();
        expectedScramble.add(new CubeMove(MovePlane.TOP, MoveDirection.LONG, 1));
        expectedScramble.add(new CubeMove(MovePlane.LEFT, MoveDirection.ANTICLOCKWISE, 1));
        expectedScramble.add(new CubeMove(MovePlane.BOTTOM, MoveDirection.ANTICLOCKWISE, 1));
        expectedScramble.add(new CubeMove(MovePlane.LEFT, MoveDirection.LONG, 1));
        expectedScramble.add(new CubeMove(MovePlane.FRONT, MoveDirection.CLOCKWISE, 1));
        expectedScramble.add(new CubeMove(MovePlane.BACK, MoveDirection.CLOCKWISE, 1));
        expectedScramble.add(new CubeMove(MovePlane.FRONT, MoveDirection.LONG, 1));
        expectedScramble.add(new CubeMove(MovePlane.BOTTOM, MoveDirection.CLOCKWISE, 1));
        expectedScramble.add(new CubeMove(MovePlane.RIGHT, MoveDirection.CLOCKWISE, 1));
        expectedScramble.add(new CubeMove(MovePlane.FRONT, MoveDirection.ANTICLOCKWISE, 1));
        expectedScramble.add(new CubeMove(MovePlane.LEFT, MoveDirection.LONG, 2));
        expectedScramble.add(new CubeMove(MovePlane.TOP, MoveDirection.ANTICLOCKWISE, 3));

        List<CubeMove> actualScramble = scrambleConverter.toList("U2 L' D' L2 F B F2 D R F' Lw2 3Uw'");

        assertEquals(expectedScramble, actualScramble);
    }

    @Test
    void testToString() {
        List<CubeMove> scramble = new ArrayList<>();
        scramble.add(new CubeMove(MovePlane.TOP, MoveDirection.ANTICLOCKWISE, 1));
        scramble.add(new CubeMove(MovePlane.FRONT, MoveDirection.CLOCKWISE, 2));
        scramble.add(new CubeMove(MovePlane.TOP, MoveDirection.LONG, 1));
        scramble.add(new CubeMove(MovePlane.RIGHT, MoveDirection.LONG, 3));
        scramble.add(new CubeMove(MovePlane.FRONT, MoveDirection.ANTICLOCKWISE, 3));
        scramble.add(new CubeMove(MovePlane.BOTTOM, MoveDirection.CLOCKWISE, 1));
        scramble.add(new CubeMove(MovePlane.BACK, MoveDirection.ANTICLOCKWISE, 2));
        scramble.add(new CubeMove(MovePlane.BOTTOM, MoveDirection.ANTICLOCKWISE, 2));

        String expectedString = "U' Fw U2 3Rw2 3Fw' D Bw' Dw'";
        String actualString = scrambleConverter.toString(scramble);

        assertEquals(expectedString, actualString);
    }
}