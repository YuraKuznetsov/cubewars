package org.geekhub.yurii.service.cube;

import org.geekhub.yurii.exception.ScrambleFormatException;
import org.geekhub.yurii.model.cube.move.CubeMove;
import org.geekhub.yurii.model.cube.move.MoveDirection;
import org.geekhub.yurii.model.cube.move.MovePlane;

import java.util.*;
import java.util.stream.Collectors;

public class ScrambleConverter {

    private final Map<MoveDirection, String> markingForDirection = Map.of(
            MoveDirection.CLOCKWISE, "",
            MoveDirection.ANTICLOCKWISE, "'",
            MoveDirection.LONG, "2"
    );
    private final Map<MovePlane, String> markingForPlane = Map.of(
            MovePlane.TOP, "U",
            MovePlane.BOTTOM, "D",
            MovePlane.FRONT, "F",
            MovePlane.BACK, "B",
            MovePlane.LEFT, "L",
            MovePlane.RIGHT, "R"
    );

    public String toString(List<CubeMove> scramble) {
        final StringJoiner stringJoiner = new StringJoiner(" ");

        scramble.stream()
                .map(this::moveToString)
                .forEach(stringJoiner::add);

        return stringJoiner.toString();
    }

    private String moveToString(CubeMove move) {
        final String planeMarking = markingForPlane.get(move.getPlane());
        final String directionMarking = markingForDirection.get(move.getDirection());
        int layer = move.getLayer();

        switch (layer) {
            case 1:
                return planeMarking + directionMarking;
            case 2:
                return planeMarking + "w" + directionMarking;
            default:
                return layer + planeMarking + "w" + directionMarking;
        }
    }

    public List<CubeMove> toList(String scrambleAsString) {
        String trimmedScramble = scrambleAsString.trim();

        if (trimmedScramble.isEmpty()) return List.of();

        return Arrays.stream(trimmedScramble.split(" "))
                .map(this::stringToMove)
                .collect(Collectors.toList());
    }

    private CubeMove stringToMove(String moveAsString) {
        char firstLetter = moveAsString.charAt(0);

        if (!moveAsString.contains("w")) {
            return defineFirstLayerMove(moveAsString);
        }

        if (isPlaneLetter(firstLetter)) {
            return defineSecondLayerMove(moveAsString);
        }

        int layer = Character.getNumericValue(firstLetter);
        MovePlane plane = definePlane(moveAsString.charAt(1));

        if (moveAsString.charAt(moveAsString.length() - 1) == 'w') {
            return new CubeMove(plane, MoveDirection.CLOCKWISE, layer);
        }

        MoveDirection direction = defineDirection(moveAsString.charAt(moveAsString.length() - 1));
        return new CubeMove(plane, direction, layer);
    }

    private CubeMove defineFirstLayerMove(String moveAsString) {
        MovePlane plane = definePlane(moveAsString.charAt(0));

        if (moveAsString.length() == 1) {
            return new CubeMove(plane, MoveDirection.CLOCKWISE, 1);
        }

        MoveDirection direction = defineDirection(moveAsString.charAt(1));
        return new CubeMove(plane, direction, 1);
    }

    private CubeMove defineSecondLayerMove(String moveAsString) {
        MovePlane plane = definePlane(moveAsString.charAt(0));

        if (moveAsString.length() == 2) {
            return new CubeMove(plane, MoveDirection.CLOCKWISE, 2);
        }

        MoveDirection direction = defineDirection(moveAsString.charAt(2));
        return new CubeMove(plane, direction, 2);
    }

    private MoveDirection defineDirection(char directionMarking) {
        switch (directionMarking) {
            case '\'': return MoveDirection.ANTICLOCKWISE;
            case '2': return MoveDirection.LONG;
            default: throw new ScrambleFormatException("Can't define direction. Marking: '" + directionMarking + "'");
        }
    }

    private boolean isPlaneLetter(char letter) {
        return List.of('U', 'B', 'D', 'F', 'L', 'R').contains(letter);
    }

    private MovePlane definePlane(char letter) {
        switch (letter) {
            case 'U': return MovePlane.TOP;
            case 'D': return MovePlane.BOTTOM;
            case 'F': return MovePlane.FRONT;
            case 'B': return MovePlane.BACK;
            case 'L': return MovePlane.LEFT;
            case 'R': return MovePlane.RIGHT;
            default: throw new ScrambleFormatException("Can't define plane. Letter: " + letter);
        }
    }
}
