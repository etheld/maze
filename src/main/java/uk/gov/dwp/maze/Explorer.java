package uk.gov.dwp.maze;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.dwp.maze.data.Coord;
import uk.gov.dwp.maze.enums.Cell;
import uk.gov.dwp.maze.enums.Facing;

import java.util.*;
import java.util.stream.Collectors;

public class Explorer {

    private static final Logger LOG = LoggerFactory.getLogger(Explorer.class);

    private Coord position;
    private Facing facing;
    private Maze maze;
    private List<Coord> trail = new ArrayList<>();

    public Explorer(final Facing facing, Maze maze) {
        this.maze = maze;
        this.facing = facing;
        Optional<Coord> startPosition = maze.getCells().entrySet().stream().
                filter(x -> x.getValue() == Cell.START).
                map(Map.Entry::getKey).
                findAny();

        startPosition.ifPresent(coord -> position = coord);

        // comment
        trail.add(position);

        LOG.debug("Explorer spawned at location: {}", position);
    }

    public void moveExplorerForward() {
        Coord newPosition = calculateInFrontCoord();
        if (maze.isValidPoint(newPosition) && maze.getPoint(newPosition) != Cell.WALL) {
            LOG.debug("Explorer moved to {}", newPosition);
            trail.add(newPosition);
            position = newPosition;
        } else {
            LOG.warn("Explorer tried to move, but it was not possible");
        }
    }


    public void turnLeft() {
        facing = facing.getLeft();
    }

    public void turnRight() {
        facing = facing.getRight();
    }

    public Cell getCellInFront() {
        return maze.getPoint(calculateInFrontCoord());
    }

    public List<Coord> getPossibleMoves() {
        return position.getNeighbours().stream().
                filter(maze::isValidPoint).
                filter(point -> maze.getPoint(point) != Cell.WALL).
                collect(Collectors.toList());
    }

    public String whereHaveYouBeen() {
        return trail.stream().map(i -> String.format("%d,%d", i.getX(), i.getY())).collect(Collectors.joining("\n"));
    }

    @Override
    public String toString() {
        return "Explorer{" +
                "position=" + position +
                ", facing=" + facing +
                '}';
    }

    List<Coord> getTrail() {
        return Collections.unmodifiableList(trail);
    }

    Coord getPosition() {
        return position;
    }

    Facing getFacing() {
        return facing;
    }

    private Coord calculateInFrontCoord() {
        return new Coord(position.getX() + facing.getxOffset(), position.getY() + facing.getyOffset());
    }

}
