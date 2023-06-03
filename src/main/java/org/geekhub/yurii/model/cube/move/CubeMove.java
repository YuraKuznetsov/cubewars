package org.geekhub.yurii.model.cube.move;

public class CubeMove {

    private final MovePlane plane;
    private final MoveDirection direction;
    private final int layer;

    public CubeMove(MovePlane plane, MoveDirection direction, int layer) {
        this.plane = plane;
        this.direction = direction;
        this.layer = layer;
    }

    public MovePlane getPlane() {
        return plane;
    }

    public MoveDirection getDirection() {
        return direction;
    }

    public int getLayer() {
        return layer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CubeMove move = (CubeMove) o;

        if (layer != move.layer) return false;
        if (plane != move.plane) return false;
        return direction == move.direction;
    }

    @Override
    public int hashCode() {
        int result = plane.hashCode();
        result = 31 * result + direction.hashCode();
        result = 31 * result + layer;
        return result;
    }
}
