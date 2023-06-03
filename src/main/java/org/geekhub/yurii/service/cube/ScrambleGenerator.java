package org.geekhub.yurii.service.cube;

import org.geekhub.yurii.model.cube.Discipline;
import org.geekhub.yurii.model.cube.move.CubeMove;
import org.geekhub.yurii.model.cube.move.MoveDirection;
import org.geekhub.yurii.model.cube.move.MovePlane;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ScrambleGenerator {

    private final Map<Discipline, Integer> scrambleLengthForDiscipline = Map.of(
            Discipline.CUBE_2X2, 11,
            Discipline.CUBE_3X3, 25,
            Discipline.CUBE_4X4, 35,
            Discipline.CUBE_5X5, 40,
            Discipline.CUBE_6X6, 45,
            Discipline.CUBE_7X7, 50
    );
    private final Random random = new Random();

    public List<CubeMove> generateScramble(Discipline discipline) {
        final List<CubeMove> scramble = new ArrayList<>();

        while (scramble.size() != scrambleLengthForDiscipline.get(discipline)) {
            final CubeMove move = getRandomMove(discipline);

            if (isMoveMakesSense(move, scramble)) {
                scramble.add(move);
            }
        }

        return scramble;
    }

    private CubeMove getRandomMove(Discipline discipline) {
        final MovePlane plane = getRandomPlane();
        final MoveDirection direction = getRandomDirection();
        final int layer = getRandomLayer(discipline);

        return new CubeMove(plane, direction, layer);
    }

    private MovePlane getRandomPlane() {
        final MovePlane[] planes = MovePlane.values();
        return planes[random.nextInt(planes.length)];
    }

    private MoveDirection getRandomDirection() {
        final MoveDirection[] directions = MoveDirection.values();
        return directions[random.nextInt(directions.length)];
    }

    private int getRandomLayer(Discipline discipline) {
        final int layersCount = discipline.getMovingLayersCount();
        return 1 + random.nextInt(layersCount);
    }


    private boolean isMoveMakesSense(CubeMove move, List<CubeMove> moves) {
        if (moves.isEmpty()) return true;

        final CubeMove lastMove = moves.get(moves.size() - 1);

        final boolean isDifferentPlane = lastMove.getPlane() != move.getPlane();
        final boolean isDifferentLayer = lastMove.getLayer() != move.getLayer();

        return isDifferentPlane || isDifferentLayer;
    }
}
