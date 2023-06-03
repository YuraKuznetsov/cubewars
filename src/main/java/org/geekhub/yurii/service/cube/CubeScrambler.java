package org.geekhub.yurii.service.cube;

import org.geekhub.yurii.model.cube.Cube;
import org.geekhub.yurii.model.cube.Discipline;
import org.geekhub.yurii.model.cube.move.CubeMove;

import java.util.List;

public class CubeScrambler {

    public Cube scrambleCube(Discipline discipline, List<CubeMove> scramble) {
        final Cube cube = new Cube(discipline);
        cube.setDefaultSidesColors();

        for (CubeMove move : scramble) {
            makeMove(cube, move);
        }

        return cube;
    }

    private void makeMove(Cube cube, CubeMove move) {
        switch (move.getPlane()) {
            case TOP:
                moveTopSide(cube, move);
                break;
            case BOTTOM:
                moveBottomSide(cube, move);
                break;
            case RIGHT:
                moveRightSide(cube, move);
                break;
            case LEFT:
                moveLeftSide(cube, move);
                break;
            case FRONT:
                moveFrontSide(cube, move);
                break;
            case BACK:
                moveBackSide(cube, move);
                break;
        }
    }

    private void moveTopSide(Cube cube, CubeMove move) {
        switch (move.getDirection()) {
            case CLOCKWISE:
                cube.moveTopClockwise(move.getLayer());
                break;
            case ANTICLOCKWISE:
                cube.moveTopAnticlockwise(move.getLayer());
                break;
            case LONG:
                cube.moveTopTwice(move.getLayer());
                break;
        }
    }

    private void moveFrontSide(Cube cube, CubeMove move) {
        switch (move.getDirection()) {
            case CLOCKWISE:
                cube.moveFrontClockwise(move.getLayer());
                break;
            case ANTICLOCKWISE:
                cube.moveFrontAnticlockwise(move.getLayer());
                break;
            case LONG:
                cube.moveFrontTwice(move.getLayer());
                break;
        }
    }

    private void moveBottomSide(Cube cube, CubeMove move) {
        switch (move.getDirection()) {
            case CLOCKWISE:
                cube.moveBottomClockwise(move.getLayer());
                break;
            case ANTICLOCKWISE:
                cube.moveBottomAnticlockwise(move.getLayer());
                break;
            case LONG:
                cube.moveBottomTwice(move.getLayer());
                break;
        }
    }

    private void moveBackSide(Cube cube, CubeMove move) {
        switch (move.getDirection()) {
            case CLOCKWISE:
                cube.moveBackClockwise(move.getLayer());
                break;
            case ANTICLOCKWISE:
                cube.moveBackAnticlockwise(move.getLayer());
                break;
            case LONG:
                cube.moveBackTwice(move.getLayer());
                break;
        }
    }

    private void moveLeftSide(Cube cube, CubeMove move) {
        switch (move.getDirection()) {
            case CLOCKWISE:
                cube.moveLeftClockwise(move.getLayer());
                break;
            case ANTICLOCKWISE:
                cube.moveLeftAnticlockwise(move.getLayer());
                break;
            case LONG:
                cube.moveLeftTwice(move.getLayer());
                break;
        }
    }

    private void moveRightSide(Cube cube, CubeMove move) {
        switch (move.getDirection()) {
            case CLOCKWISE:
                cube.moveRightClockwise(move.getLayer());
                break;
            case ANTICLOCKWISE:
                cube.moveRightAnticlockwise(move.getLayer());
                break;
            case LONG:
                cube.moveRightTwice(move.getLayer());
                break;
        }
    }
}
