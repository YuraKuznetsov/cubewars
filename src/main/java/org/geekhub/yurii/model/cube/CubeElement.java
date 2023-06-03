package org.geekhub.yurii.model.cube;

public class CubeElement {

    private final Color color;

    public CubeElement(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CubeElement element = (CubeElement) o;

        return color == element.color;
    }

    @Override
    public int hashCode() {
        return color.hashCode();
    }

    @Override
    public String toString() {
        return "CubeElement{" +
                "color=" + color +
                '}';
    }
}
