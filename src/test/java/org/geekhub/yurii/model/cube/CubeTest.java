package org.geekhub.yurii.model.cube;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CubeTest {

    Cube cube = new Cube(Discipline.CUBE_3X3);

    @BeforeEach
    void init() {
        cube.setDefaultSidesColors();
    }

    @Test
    void testMoveTopClockwise() {
        cube.moveTopClockwise(1);

        CubeElement[] expectedFrontRow = new CubeElement[] {
                new CubeElement(Color.RED),
                new CubeElement(Color.RED),
                new CubeElement(Color.RED)
        };
        assertArrayEquals(expectedFrontRow, cube.getFrontSide().getRow(0));

        CubeElement[] expectedBackRow = new CubeElement[] {
                new CubeElement(Color.ORANGE),
                new CubeElement(Color.ORANGE),
                new CubeElement(Color.ORANGE)
        };
        assertArrayEquals(expectedBackRow, cube.getBackSide().getRow(0));

        CubeElement[] expectedLeftRow = new CubeElement[] {
                new CubeElement(Color.GREEN),
                new CubeElement(Color.GREEN),
                new CubeElement(Color.GREEN)
        };
        assertArrayEquals(expectedLeftRow, cube.getLeftSide().getRow(0));

        CubeElement[] expectedRightRow = new CubeElement[] {
                new CubeElement(Color.BLUE),
                new CubeElement(Color.BLUE),
                new CubeElement(Color.BLUE)
        };
        assertArrayEquals(expectedRightRow, cube.getRightSide().getRow(0));
    }

    @Test
    void testMoveTopAnticlockwise() {
        Cube cube1 = new Cube(Discipline.CUBE_3X3);
        cube1.setDefaultSidesColors();

        Cube cube2 = new Cube(Discipline.CUBE_3X3);
        cube2.setDefaultSidesColors();

        cube1.moveTopClockwise(1);
        cube1.moveTopClockwise(1);
        cube1.moveTopClockwise(1);

        cube2.moveTopAnticlockwise(1);

        assertEquals(cube1, cube2);
    }

    @Test
    void testMoveTopTwice() {
        Cube cube1 = new Cube(Discipline.CUBE_3X3);
        cube1.setDefaultSidesColors();

        Cube cube2 = new Cube(Discipline.CUBE_3X3);
        cube2.setDefaultSidesColors();

        cube1.moveTopClockwise(1);
        cube1.moveTopClockwise(1);

        cube2.moveTopTwice(1);

        assertEquals(cube1, cube2);
    }

    @Test
    void testMoveFrontClockwise() {
        cube.moveFrontClockwise(1);

        CubeElement[] expectedTopRow = new CubeElement[] {
                new CubeElement(Color.ORANGE),
                new CubeElement(Color.ORANGE),
                new CubeElement(Color.ORANGE)
        };
        assertArrayEquals(expectedTopRow, cube.getTopSide().getRow(2));

        CubeElement[] expectedRightCol = new CubeElement[] {
                new CubeElement(Color.WHITE),
                new CubeElement(Color.WHITE),
                new CubeElement(Color.WHITE)
        };
        assertArrayEquals(expectedRightCol, cube.getRightSide().getCol(0));

        CubeElement[] expectedBottomRow = new CubeElement[] {
                new CubeElement(Color.RED),
                new CubeElement(Color.RED),
                new CubeElement(Color.RED)
        };
        assertArrayEquals(expectedBottomRow, cube.getBottomSide().getRow(0));

        CubeElement[] expectedLeftCol = new CubeElement[] {
                new CubeElement(Color.YELLOW),
                new CubeElement(Color.YELLOW),
                new CubeElement(Color.YELLOW)
        };
        assertArrayEquals(expectedLeftCol, cube.getLeftSide().getCol(2));
    }

    @Test
    void testMoveFrontAnticlockwise() {
        Cube cube1 = new Cube(Discipline.CUBE_3X3);
        cube1.setDefaultSidesColors();

        Cube cube2 = new Cube(Discipline.CUBE_3X3);
        cube2.setDefaultSidesColors();

        cube1.moveFrontClockwise(1);
        cube1.moveFrontClockwise(1);
        cube1.moveFrontClockwise(1);

        cube2.moveFrontAnticlockwise(1);

        assertEquals(cube1, cube2);
    }

    @Test
    void testMoveFrontTwice() {
        Cube cube1 = new Cube(Discipline.CUBE_3X3);
        cube1.setDefaultSidesColors();

        Cube cube2 = new Cube(Discipline.CUBE_3X3);
        cube2.setDefaultSidesColors();

        cube1.moveFrontClockwise(1);
        cube1.moveFrontClockwise(1);

        cube2.moveFrontTwice(1);

        assertEquals(cube1, cube2);
    }

    @Test
    void testMoveBottomClockwise() {
        cube.moveBottomClockwise(1);

        CubeElement[] expectedFrontRow = new CubeElement[] {
                new CubeElement(Color.ORANGE),
                new CubeElement(Color.ORANGE),
                new CubeElement(Color.ORANGE)
        };
        assertArrayEquals(expectedFrontRow, cube.getFrontSide().getRow(2));

        CubeElement[] expectedBackRow = new CubeElement[] {
                new CubeElement(Color.RED),
                new CubeElement(Color.RED),
                new CubeElement(Color.RED)
        };
        assertArrayEquals(expectedBackRow, cube.getBackSide().getRow(2));

        CubeElement[] expectedLeftRow = new CubeElement[] {
                new CubeElement(Color.BLUE),
                new CubeElement(Color.BLUE),
                new CubeElement(Color.BLUE)
        };
        assertArrayEquals(expectedLeftRow, cube.getLeftSide().getRow(2));

        CubeElement[] expectedRightRow = new CubeElement[] {
                new CubeElement(Color.GREEN),
                new CubeElement(Color.GREEN),
                new CubeElement(Color.GREEN)
        };
        assertArrayEquals(expectedRightRow, cube.getRightSide().getRow(2));
    }

    @Test
    void testMoveBottomAnticlockwise() {
        Cube cube1 = new Cube(Discipline.CUBE_3X3);
        cube1.setDefaultSidesColors();

        Cube cube2 = new Cube(Discipline.CUBE_3X3);
        cube2.setDefaultSidesColors();

        cube1.moveBottomClockwise(1);
        cube1.moveBottomClockwise(1);
        cube1.moveBottomClockwise(1);

        cube2.moveBottomAnticlockwise(1);

        assertEquals(cube1, cube2);
    }

    @Test
    void testMoveBottomTwice() {
        Cube cube1 = new Cube(Discipline.CUBE_3X3);
        cube1.setDefaultSidesColors();

        Cube cube2 = new Cube(Discipline.CUBE_3X3);
        cube2.setDefaultSidesColors();

        cube1.moveBottomClockwise(1);
        cube1.moveBottomClockwise(1);

        cube2.moveBottomTwice(1);

        assertEquals(cube1, cube2);
    }

    @Test
    void testMoveBackClockwise() {
        cube.moveBackClockwise(1);

        CubeElement[] expectedTopRow = new CubeElement[] {
                new CubeElement(Color.RED),
                new CubeElement(Color.RED),
                new CubeElement(Color.RED)
        };
        assertArrayEquals(expectedTopRow, cube.getTopSide().getRow(0));

        CubeElement[] expectedRightCol = new CubeElement[] {
                new CubeElement(Color.YELLOW),
                new CubeElement(Color.YELLOW),
                new CubeElement(Color.YELLOW)
        };
        assertArrayEquals(expectedRightCol, cube.getRightSide().getCol(2));

        CubeElement[] expectedBottomRow = new CubeElement[] {
                new CubeElement(Color.ORANGE),
                new CubeElement(Color.ORANGE),
                new CubeElement(Color.ORANGE)
        };
        assertArrayEquals(expectedBottomRow, cube.getBottomSide().getRow(2));

        CubeElement[] expectedLeftCol = new CubeElement[] {
                new CubeElement(Color.WHITE),
                new CubeElement(Color.WHITE),
                new CubeElement(Color.WHITE)
        };
        assertArrayEquals(expectedLeftCol, cube.getLeftSide().getCol(0));
    }

    @Test
    void testMoveBackAnticlockwise() {
        Cube cube1 = new Cube(Discipline.CUBE_3X3);
        cube1.setDefaultSidesColors();

        Cube cube2 = new Cube(Discipline.CUBE_3X3);
        cube2.setDefaultSidesColors();

        cube1.moveBackClockwise(1);
        cube1.moveBackClockwise(1);
        cube1.moveBackClockwise(1);

        cube2.moveBackAnticlockwise(1);

        assertEquals(cube1, cube2);
    }

    @Test
    void testMoveBackTwice() {
        Cube cube1 = new Cube(Discipline.CUBE_3X3);
        cube1.setDefaultSidesColors();

        Cube cube2 = new Cube(Discipline.CUBE_3X3);
        cube2.setDefaultSidesColors();

        cube1.moveBackClockwise(1);
        cube1.moveBackClockwise(1);

        cube2.moveBackTwice(1);

        assertEquals(cube1, cube2);
    }

    @Test
    void testMoveLeftClockwise() {
        cube.moveLeftClockwise(1);

        CubeElement[] expectedTopCol = new CubeElement[] {
                new CubeElement(Color.BLUE),
                new CubeElement(Color.BLUE),
                new CubeElement(Color.BLUE)
        };
        assertArrayEquals(expectedTopCol, cube.getTopSide().getCol(0));

        CubeElement[] expectedFrontCol = new CubeElement[] {
                new CubeElement(Color.WHITE),
                new CubeElement(Color.WHITE),
                new CubeElement(Color.WHITE)
        };
        assertArrayEquals(expectedFrontCol, cube.getFrontSide().getCol(0));

        CubeElement[] expectedBottomCol = new CubeElement[] {
                new CubeElement(Color.GREEN),
                new CubeElement(Color.GREEN),
                new CubeElement(Color.GREEN)
        };
        assertArrayEquals(expectedBottomCol, cube.getBottomSide().getCol(0));

        CubeElement[] expectedBackCol = new CubeElement[] {
                new CubeElement(Color.YELLOW),
                new CubeElement(Color.YELLOW),
                new CubeElement(Color.YELLOW)
        };
        assertArrayEquals(expectedBackCol, cube.getBackSide().getCol(2));
    }

    @Test
    void testMoveLeftAnticlockwise() {
        Cube cube1 = new Cube(Discipline.CUBE_3X3);
        cube1.setDefaultSidesColors();

        Cube cube2 = new Cube(Discipline.CUBE_3X3);
        cube2.setDefaultSidesColors();

        cube1.moveLeftClockwise(1);
        cube1.moveLeftClockwise(1);
        cube1.moveLeftClockwise(1);

        cube2.moveLeftAnticlockwise(1);

        assertEquals(cube1, cube2);
    }

    @Test
    void testMoveLeftTwice() {
        Cube cube1 = new Cube(Discipline.CUBE_3X3);
        cube1.setDefaultSidesColors();

        Cube cube2 = new Cube(Discipline.CUBE_3X3);
        cube2.setDefaultSidesColors();

        cube1.moveLeftClockwise(1);
        cube1.moveLeftClockwise(1);

        cube2.moveLeftTwice(1);

        assertEquals(cube1, cube2);
    }

    @Test
    void testMoveRightClockwise() {
        cube.moveRightClockwise(1);

        CubeElement[] expectedTopCol = new CubeElement[] {
                new CubeElement(Color.GREEN),
                new CubeElement(Color.GREEN),
                new CubeElement(Color.GREEN)
        };
        assertArrayEquals(expectedTopCol, cube.getTopSide().getCol(2));

        CubeElement[] expectedFrontCol = new CubeElement[] {
                new CubeElement(Color.YELLOW),
                new CubeElement(Color.YELLOW),
                new CubeElement(Color.YELLOW)
        };
        assertArrayEquals(expectedFrontCol, cube.getFrontSide().getCol(2));

        CubeElement[] expectedBottomCol = new CubeElement[] {
                new CubeElement(Color.BLUE),
                new CubeElement(Color.BLUE),
                new CubeElement(Color.BLUE)
        };
        assertArrayEquals(expectedBottomCol, cube.getBottomSide().getCol(2));

        CubeElement[] expectedBackCol = new CubeElement[] {
                new CubeElement(Color.WHITE),
                new CubeElement(Color.WHITE),
                new CubeElement(Color.WHITE)
        };
        assertArrayEquals(expectedBackCol, cube.getBackSide().getCol(0));
    }

    @Test
    void testMoveRightAnticlockwise() {
        Cube cube1 = new Cube(Discipline.CUBE_3X3);
        cube1.setDefaultSidesColors();

        Cube cube2 = new Cube(Discipline.CUBE_3X3);
        cube2.setDefaultSidesColors();

        cube1.moveRightClockwise(1);
        cube1.moveRightClockwise(1);
        cube1.moveRightClockwise(1);

        cube2.moveRightAnticlockwise(1);

        assertEquals(cube1, cube2);
    }

    @Test
    void testMoveRightTwice() {
        Cube cube1 = new Cube(Discipline.CUBE_3X3);
        cube1.setDefaultSidesColors();

        Cube cube2 = new Cube(Discipline.CUBE_3X3);
        cube2.setDefaultSidesColors();

        cube1.moveRightClockwise(1);
        cube1.moveRightClockwise(1);

        cube2.moveRightTwice(1);

        assertEquals(cube1, cube2);
    }
}