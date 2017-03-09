package uk.gov.dwp.maze.data;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Coord {

    private final Integer x;
    private final Integer y;

    public Coord(final Integer x, final Integer y) {
        this.x = x;
        this.y = y;
    }

    public List<Coord> getNeighbours() {
        return Arrays.asList(
                new Coord(x - 1, y),
                new Coord(x + 1, y),
                new Coord(x, y + 1),
                new Coord(x, y - 1)
        );
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Coord{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Coord coord = (Coord) o;
        return Objects.equals(x, coord.x) &&
                Objects.equals(y, coord.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}
