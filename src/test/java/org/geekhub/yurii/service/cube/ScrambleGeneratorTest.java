package org.geekhub.yurii.service.cube;

import org.geekhub.yurii.model.cube.Discipline;
import org.geekhub.yurii.model.cube.move.CubeMove;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScrambleGeneratorTest {

    private final ScrambleGenerator scrambleGenerator = new ScrambleGenerator();

    @Test
    public void testLength() {
        assertEquals(11, scrambleGenerator.generateScramble(Discipline.CUBE_2X2).size());
        assertEquals(25, scrambleGenerator.generateScramble(Discipline.CUBE_3X3).size());
        assertEquals(35, scrambleGenerator.generateScramble(Discipline.CUBE_4X4).size());
        assertEquals(40, scrambleGenerator.generateScramble(Discipline.CUBE_5X5).size());
        assertEquals(45, scrambleGenerator.generateScramble(Discipline.CUBE_6X6).size());
        assertEquals(50, scrambleGenerator.generateScramble(Discipline.CUBE_7X7).size());
    }

    @Test
    public void testUnnecessaryMoves() {
        List<CubeMove> scramble = scrambleGenerator.generateScramble(Discipline.CUBE_7X7);

        for (int i = 0; i < scramble.size(); i++) {
            if (i == scramble.size() - 1) break;

            CubeMove cubeMove = scramble.get(i);
            CubeMove nextMove = scramble.get(i + 1);

            assertTrue(cubeMove.getPlane() != nextMove.getPlane() || cubeMove.getLayer() != nextMove.getLayer());
        }
    }
}