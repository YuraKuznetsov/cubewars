package org.geekhub.yurii.model.cube;

import java.util.Arrays;

public class CubeSide {

    private final int dimension;
    private final CubeElement[][] elementsMatrix;

    public CubeSide(int dimension) {
        this.dimension = dimension;
        this.elementsMatrix = new CubeElement[dimension][dimension];
    }

    public void setSideColor(Color color) {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                elementsMatrix[i][j] = new CubeElement(color);
            }
        }
    }

    public void rotateClockwise() {
        int x = (int) Math.floor(dimension / 2.0);
        int y = dimension - 1;

        for (int i = 0; i < x; i++) {
            for (int j = i; j < y - i; j++) {
                CubeElement k = elementsMatrix[i][j];
                elementsMatrix[i][j] = elementsMatrix[y - j][i];
                elementsMatrix[y - j][i] = elementsMatrix[y - i][y - j];
                elementsMatrix[y - i][y - j] = elementsMatrix[j][y - i];
                elementsMatrix[j][y - i] = k;
            }
        }
    }

    public CubeElement[] getRow(int rowIndex) {
        return elementsMatrix[rowIndex];
    }

    public void setRow(int rowIndex, CubeElement[] row) {
        elementsMatrix[rowIndex] = row;
    }

    public CubeElement[] getCol(int colIndex) {
        CubeElement[] col = new CubeElement[elementsMatrix.length];

        for (int i = 0; i < elementsMatrix.length; i++) {
            col[i] = elementsMatrix[i][colIndex];
        }

        return col;
    }

    public void setCol(int colIndex, CubeElement[] col) {
        for (int i = 0; i < elementsMatrix.length; i++) {
            elementsMatrix[i][colIndex] = col[i];
        }
    }

    public int getDimension() {
        return dimension;
    }

    public CubeElement[][] getElementsMatrix() {
        return elementsMatrix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CubeSide side = (CubeSide) o;

        return Arrays.deepEquals(elementsMatrix, side.elementsMatrix);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(elementsMatrix);
    }

    @Override
    public String toString() {
        return "CubeSide{" +
                "dimension=" + dimension +
                ", elementsMatrix=" + Arrays.toString(elementsMatrix) +
                '}';
    }
}
