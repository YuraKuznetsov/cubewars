package org.geekhub.yurii.model.cube;

public enum Discipline {

    CUBE_2X2(2, 1),
    CUBE_3X3(3, 1),
    CUBE_4X4(4, 2),
    CUBE_5X5(5, 2),
    CUBE_6X6(6, 3),
    CUBE_7X7(7, 3);

    private final Integer dimension;
    private final Integer movingLayersCount;

    Discipline(Integer dimension, Integer movingLayersCount) {
        this.dimension = dimension;
        this.movingLayersCount = movingLayersCount;
    }

    public Integer getDimension() {
        return dimension;
    }

    public Integer getMovingLayersCount() {
        return movingLayersCount;
    }
}
