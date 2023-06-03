package org.geekhub.yurii.model.cube;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CubeSideTest {

    @Test
    public void testSetSideColor() {
        CubeSide side = new CubeSide(2);
        side.setSideColor(Color.GREEN);

        CubeElement[][] expectedMatrix = new CubeElement[][] {
                { new CubeElement(Color.GREEN), new CubeElement(Color.GREEN) },
                { new CubeElement(Color.GREEN), new CubeElement(Color.GREEN) }
        };

        CubeElement[][] actualMatrix = side.getElementsMatrix();

        assertTrue(Arrays.deepEquals(expectedMatrix, actualMatrix));
    }

    @Test
    public void testSetRow() {
        CubeElement[] newRow = new CubeElement[] {
                new CubeElement(Color.RED),
                new CubeElement(Color.ORANGE),
                new CubeElement(Color.BLUE)
        };

        CubeElement[][] expectedMatrix = new CubeElement[][] {
                { new CubeElement(Color.RED), new CubeElement(Color.ORANGE), new CubeElement(Color.BLUE) },
                { new CubeElement(Color.RED), new CubeElement(Color.RED), new CubeElement(Color.RED) },
                { new CubeElement(Color.RED), new CubeElement(Color.RED), new CubeElement(Color.RED) }
        };

        CubeSide side = new CubeSide(3);
        side.setSideColor(Color.RED);
        side.setRow(0, newRow);

        CubeElement[][] actualMatrix = side.getElementsMatrix();

        assertTrue(Arrays.deepEquals(expectedMatrix, actualMatrix));
    }

    @Test
    public void testGetRow() {
        CubeSide side = new CubeSide(4);
        side.setSideColor(Color.YELLOW);

        CubeElement[] expectedRow = new CubeElement[] {
                new CubeElement(Color.YELLOW),
                new CubeElement(Color.YELLOW),
                new CubeElement(Color.YELLOW),
                new CubeElement(Color.YELLOW)
        };

        CubeElement[] actualRow = side.getRow(3);

        assertTrue(Arrays.deepEquals(expectedRow, actualRow));
    }

    @Test
    public void testSetCol() {
        CubeElement[] newCol = new CubeElement[] {
                new CubeElement(Color.GREEN),
                new CubeElement(Color.ORANGE),
                new CubeElement(Color.BLUE)
        };

        CubeElement[][] expectedMatrix = new CubeElement[][] {
                { new CubeElement(Color.RED), new CubeElement(Color.RED), new CubeElement(Color.GREEN) },
                { new CubeElement(Color.RED), new CubeElement(Color.RED), new CubeElement(Color.ORANGE) },
                { new CubeElement(Color.RED), new CubeElement(Color.RED), new CubeElement(Color.BLUE) }
        };

        CubeSide side = new CubeSide(3);
        side.setSideColor(Color.RED);
        side.setCol(2, newCol);

        CubeElement[][] actualMatrix = side.getElementsMatrix();

        assertTrue(Arrays.deepEquals(expectedMatrix, actualMatrix));
    }

    @Test
    public void testGetCol() {
        CubeSide side = new CubeSide(5);
        side.setSideColor(Color.BLUE);

        CubeElement[] newRow = new CubeElement[] {
                new CubeElement(Color.RED),
                new CubeElement(Color.ORANGE),
                new CubeElement(Color.BLUE),
                new CubeElement(Color.WHITE),
                new CubeElement(Color.ORANGE)
        };

        side.setRow(1, newRow);

        CubeElement[] expectedCol = new CubeElement[] {
                new CubeElement(Color.BLUE),
                new CubeElement(Color.RED),
                new CubeElement(Color.BLUE),
                new CubeElement(Color.BLUE),
                new CubeElement(Color.BLUE)
        };

        CubeElement[] actualCol = side.getCol(0);

        assertTrue(Arrays.deepEquals(expectedCol, actualCol));
    }

    @Test
    public void testRotate() {
        CubeElement[] newCol = new CubeElement[] {
                new CubeElement(Color.GREEN),
                new CubeElement(Color.ORANGE),
                new CubeElement(Color.BLUE),
                new CubeElement(Color.WHITE)
        };

        CubeElement[] newRow = new CubeElement[] {
                new CubeElement(Color.GREEN),
                new CubeElement(Color.ORANGE),
                new CubeElement(Color.BLUE),
                new CubeElement(Color.WHITE)
        };

        CubeElement[][] expectedMatrix = new CubeElement[][] {
                { new CubeElement(Color.WHITE), new CubeElement(Color.WHITE), new CubeElement(Color.WHITE), new CubeElement(Color.WHITE) },
                { new CubeElement(Color.WHITE), new CubeElement(Color.WHITE), new CubeElement(Color.BLUE), new CubeElement(Color.WHITE) },
                { new CubeElement(Color.GREEN), new CubeElement(Color.ORANGE), new CubeElement(Color.ORANGE), new CubeElement(Color.WHITE) },
                { new CubeElement(Color.WHITE), new CubeElement(Color.WHITE), new CubeElement(Color.GREEN), new CubeElement(Color.WHITE) }
        };

        CubeSide side = new CubeSide(4);
        side.setSideColor(Color.WHITE);
        side.setCol(1, newCol);
        side.setRow(2, newRow);

        side.rotateClockwise();
        side.rotateClockwise();
        side.rotateClockwise();

        CubeElement[][] actualMatrix = side.getElementsMatrix();

        assertTrue(Arrays.deepEquals(expectedMatrix, actualMatrix));
    }
}