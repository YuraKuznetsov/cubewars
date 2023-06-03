package org.geekhub.yurii.model.cube;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Cube {

    private final Discipline discipline;
    private final CubeSide topSide, bottomSide, frontSide, backSide, leftSide, rightSide;

    public Cube(Discipline discipline) {
        this.discipline = discipline;

        this.topSide = new CubeSide(discipline.getDimension());
        this.bottomSide = new CubeSide(discipline.getDimension());
        this.frontSide = new CubeSide(discipline.getDimension());
        this.backSide = new CubeSide(discipline.getDimension());
        this.leftSide = new CubeSide(discipline.getDimension());
        this.rightSide = new CubeSide(discipline.getDimension());
    }

    public void setDefaultSidesColors() {
        topSide.setSideColor(Color.WHITE);
        bottomSide.setSideColor(Color.YELLOW);
        frontSide.setSideColor(Color.GREEN);
        backSide.setSideColor(Color.BLUE);
        leftSide.setSideColor(Color.ORANGE);
        rightSide.setSideColor(Color.RED);
    }

    public void moveTopClockwise(int layer) {
        if (layer == 1) {
            topSide.rotateClockwise();
        }

        CubeElement[] tempRow = frontSide.getRow(layer - 1);
        frontSide.setRow(layer - 1, rightSide.getRow(layer - 1));
        rightSide.setRow(layer - 1, backSide.getRow(layer - 1));
        backSide.setRow(layer - 1, leftSide.getRow(layer - 1));
        leftSide.setRow(layer - 1, tempRow);
    }

    public void moveTopAnticlockwise(int layer) {
        moveTopClockwise(layer);
        moveTopClockwise(layer);
        moveTopClockwise(layer);
    }

    public void moveTopTwice(int layer) {
        moveTopClockwise(layer);
        moveTopClockwise(layer);
    }

    public void moveBottomClockwise(int layer) {
        if (layer == 1) {
            bottomSide.rotateClockwise();
        }

        int rowIndex = discipline.getDimension() - layer;

        CubeElement[] tempRow = frontSide.getRow(rowIndex);
        frontSide.setRow(rowIndex, leftSide.getRow(rowIndex));
        leftSide.setRow(rowIndex, backSide.getRow(rowIndex));
        backSide.setRow(rowIndex, rightSide.getRow(rowIndex));
        rightSide.setRow(rowIndex, tempRow);
    }

    public void moveBottomAnticlockwise(int layer) {
        moveBottomClockwise(layer);
        moveBottomClockwise(layer);
        moveBottomClockwise(layer);
    }

    public void moveBottomTwice(int layer) {
        moveBottomClockwise(layer);
        moveBottomClockwise(layer);
    }

    public void moveFrontClockwise(int layer) {
        if (layer == 1) {
            frontSide.rotateClockwise();
        }

        int topSideRowIndex = discipline.getDimension() - layer;
        int bottomSideRowIndex = layer - 1;
        int rightSideColIndex = layer - 1;
        int leftSideColIndex = discipline.getDimension() - layer;

        CubeElement[] temp = topSide.getRow(topSideRowIndex);
        topSide.setRow(topSideRowIndex, reverse(leftSide.getCol(leftSideColIndex)));
        leftSide.setCol(leftSideColIndex, bottomSide.getRow(bottomSideRowIndex));
        bottomSide.setRow(bottomSideRowIndex, reverse(rightSide.getCol(rightSideColIndex)));
        rightSide.setCol(rightSideColIndex, temp);
    }

    public void moveFrontAnticlockwise(int layer) {
        moveFrontClockwise(layer);
        moveFrontClockwise(layer);
        moveFrontClockwise(layer);
    }

    public void moveFrontTwice(int layer) {
        moveFrontClockwise(layer);
        moveFrontClockwise(layer);
    }

    public void moveBackClockwise(int layer) {
        if (layer == 1) {
            backSide.rotateClockwise();
        }

        int topSideRowIndex = layer - 1;
        int bottomSideRowIndex = discipline.getDimension() - layer;
        int rightSideColIndex = discipline.getDimension() - layer;
        int leftSideColIndex = layer - 1;

        CubeElement[] temp = topSide.getRow(topSideRowIndex);
        topSide.setRow(topSideRowIndex, rightSide.getCol(rightSideColIndex));
        rightSide.setCol(rightSideColIndex, reverse(bottomSide.getRow(bottomSideRowIndex)));
        bottomSide.setRow(bottomSideRowIndex, leftSide.getCol(leftSideColIndex));
        leftSide.setCol(leftSideColIndex, reverse(temp));
    }

    public void moveBackAnticlockwise(int layer) {
        moveBackClockwise(layer);
        moveBackClockwise(layer);
        moveBackClockwise(layer);
    }

    public void moveBackTwice(int layer) {
        moveBackClockwise(layer);
        moveBackClockwise(layer);
    }

    public void moveLeftClockwise(int layer) {
        if (layer == 1) {
            leftSide.rotateClockwise();
        }

        int frontSideColIndex = layer - 1;
        int bottomSideColIndex = layer - 1;
        int backSideColIndex = discipline.getDimension() - layer;
        int topSideColIndex = layer - 1;

        CubeElement[] temp = frontSide.getCol(frontSideColIndex);
        frontSide.setCol(frontSideColIndex, topSide.getCol(topSideColIndex));
        topSide.setCol(topSideColIndex, reverse(backSide.getCol(backSideColIndex)));
        backSide.setCol(backSideColIndex, reverse(bottomSide.getCol(bottomSideColIndex)));
        bottomSide.setCol(bottomSideColIndex, temp);
    }

    public void moveLeftAnticlockwise(int layer) {
        moveLeftClockwise(layer);
        moveLeftClockwise(layer);
        moveLeftClockwise(layer);
    }

    public void moveLeftTwice(int layer) {
        moveLeftClockwise(layer);
        moveLeftClockwise(layer);
    }

    public void moveRightClockwise(int layer) {
        if (layer == 1) {
            rightSide.rotateClockwise();
        }

        int frontSideColIndex = discipline.getDimension() - layer;
        int bottomSideColIndex = discipline.getDimension() - layer;
        int backSideColIndex = layer - 1;
        int topSideColIndex = discipline.getDimension() - layer;

        CubeElement[] temp = frontSide.getCol(frontSideColIndex);
        frontSide.setCol(frontSideColIndex, bottomSide.getCol(bottomSideColIndex));
        bottomSide.setCol(bottomSideColIndex, reverse(backSide.getCol(backSideColIndex)));
        backSide.setCol(backSideColIndex, reverse(topSide.getCol(topSideColIndex)));
        topSide.setCol(topSideColIndex, temp);
    }

    public void moveRightAnticlockwise(int layer) {
        moveRightClockwise(layer);
        moveRightClockwise(layer);
        moveRightClockwise(layer);
    }

    public void moveRightTwice(int layer) {
        moveRightClockwise(layer);
        moveRightClockwise(layer);
    }

    private CubeElement[] reverse(CubeElement[] array) {
        List<CubeElement> list = Arrays.asList(array);
        Collections.reverse(list);
        return list.toArray(new CubeElement[0]);
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public CubeSide getTopSide() {
        return topSide;
    }

    public CubeSide getBottomSide() {
        return bottomSide;
    }

    public CubeSide getFrontSide() {
        return frontSide;
    }

    public CubeSide getBackSide() {
        return backSide;
    }

    public CubeSide getLeftSide() {
        return leftSide;
    }

    public CubeSide getRightSide() {
        return rightSide;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cube cube = (Cube) o;

        if (discipline != cube.discipline) return false;
        if (!topSide.equals(cube.topSide)) return false;
        if (!bottomSide.equals(cube.bottomSide)) return false;
        if (!frontSide.equals(cube.frontSide)) return false;
        if (!backSide.equals(cube.backSide)) return false;
        if (!leftSide.equals(cube.leftSide)) return false;
        return rightSide.equals(cube.rightSide);
    }

    @Override
    public int hashCode() {
        int result = discipline.hashCode();
        result = 31 * result + topSide.hashCode();
        result = 31 * result + bottomSide.hashCode();
        result = 31 * result + frontSide.hashCode();
        result = 31 * result + backSide.hashCode();
        result = 31 * result + leftSide.hashCode();
        result = 31 * result + rightSide.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Cube{" +
                "discipline=" + discipline +
                ", topSide=" + topSide +
                ", bottomSide=" + bottomSide +
                ", frontSide=" + frontSide +
                ", backSide=" + backSide +
                ", leftSide=" + leftSide +
                ", rightSide=" + rightSide +
                '}';
    }
}
