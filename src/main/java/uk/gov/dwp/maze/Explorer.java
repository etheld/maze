package uk.gov.dwp.maze;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public Explorer(final Facing facing, List<String> lines) {
        this.maze = new Maze(lines);
        this.facing = facing;
        Optional<Coord> startPosition = maze.getCells().entrySet().stream().filter(x -> x.getValue() == Cell.START).map(Map.Entry::getKey).findAny();
        startPosition.ifPresent(coord -> position = coord);

        LOG.debug("Explorer spawned at location: {}", position);
    }

    @Override
    public String toString() {
        return "Explorer{" +
                "position=" + position +
                ", facing=" + facing +
                '}';
    }


    public void moveExplorerForward() {
        Coord newPosition = calculateDestinationCoord();
        if (maze.isValidPoint(newPosition) && maze.getPoint(newPosition) != Cell.WALL) {
            LOG.debug("Explorer moved");
            trail.add(position);
            position = newPosition;
        } else {
            LOG.debug("Explorer tried to move, but it was not possible");
        }
    }

    private Coord calculateDestinationCoord() {
        return new Coord(position.getX() + facing.getxOffset(), position.getY() + facing.getyOffset());
    }


    public void turnLeft() {
        facing = facing.getLeft();
    }

    public void turnRight() {
        facing = facing.getRight();
    }

    public Coord getPosition() {
        return position;
    }

    public Facing getFacing() {
        return facing;
    }

    public Cell getCellInFront() {
        return maze.getPoint(calculateDestinationCoord());
    }

    public List<Coord> getPossibleMoves() {
        return position.getNeighbours().stream().
                filter(maze::isValidPoint).
                filter(p -> maze.getPoint(p) != Cell.WALL).
                collect(Collectors.toList());
    }

    public List<Coord> getTrail() {
        return Collections.unmodifiableList(trail);
    }
}
