package org.geekhub.yurii.service.cube;

import org.geekhub.yurii.model.cube.Discipline;
import org.geekhub.yurii.model.cube.Cube;
import org.geekhub.yurii.model.cube.move.CubeMove;
import org.geekhub.yurii.model.cube.move.MoveDirection;
import org.geekhub.yurii.model.cube.move.MovePlane;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CubeScramblerTest {

    private final CubeScrambler scrambler = new CubeScrambler();

    @Test
    void testScrambling_whenScrambleIsEmpty() {
        Discipline discipline = Discipline.CUBE_3X3;

        Cube expected = new Cube(discipline);
        expected.setDefaultSidesColors();

        List<CubeMove> scramble = List.of();
        Cube actual = scrambler.scrambleCube(discipline, scramble);

        assertEquals(expected, actual);
    }

    @Test
    public void testScrambling() {
        Discipline discipline = Discipline.CUBE_7X7;

        List<CubeMove> scramble = List.of(
                new CubeMove(MovePlane.RIGHT, MoveDirection.CLOCKWISE, 1),
                new CubeMove(MovePlane.TOP, MoveDirection.ANTICLOCKWISE, 1),
                new CubeMove(MovePlane.FRONT, MoveDirection.LONG, 1),
                new CubeMove(MovePlane.LEFT, MoveDirection.CLOCKWISE, 3),
                new CubeMove(MovePlane.BOTTOM, MoveDirection.LONG, 2),
                new CubeMove(MovePlane.BACK, MoveDirection.ANTICLOCKWISE, 1)
        );

        Cube expected = new Cube(discipline);
        expected.setDefaultSidesColors();
        expected.moveRightClockwise(1);
        expected.moveTopAnticlockwise(1);
        expected.moveFrontTwice(1);
        expected.moveLeftClockwise(3);
        expected.moveBottomTwice(2);
        expected.moveBackAnticlockwise(1);

        Cube actual = scrambler.scrambleCube(discipline, scramble);

        assertEquals(expected, actual);
    }
}