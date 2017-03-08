package uk.gov.dwp.maze;


import uk.gov.dwp.maze.enums.Cell;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Maze {


    private Map<Coord, Cell> cells = new HashMap<>();


    public Maze(List<String> lines) {

        buildMaze(lines);
        checkIfMazeIsValid();

    }

    // visible for testing
    Map<Coord, Cell> getCells() {
        return Collections.unmodifiableMap(cells);
    }

    private void checkIfMazeIsValid() {
        int startCellCount = getCells(Cell.START).size();
        int finishCellCount = getCells(Cell.FINISH).size();

        if (startCellCount != 1) {
            throw new IllegalStateException("Not valid maze, only 1 startpoint is allowed, now there is/are: " + startCellCount);
        }
        if (finishCellCount != 1) {
            throw new IllegalStateException("Not valid maze, only 1 finishpoint is allowed, now there is/are: " + finishCellCount);
        }
    }

    private void buildMaze(List<String> lines) {

        int i = 1;
        for (String line : lines) {
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                cells.put(new Coord(j + 1, i), Cell.getEnum(c));
            }
            i++;
        }
    }

    public boolean isValidPoint(Coord c) {
        return cells.containsKey(c);
    }

    public Cell getPoint(Coord c) {
        if (isValidPoint(c)) {
            return cells.get(c);
        } else {
            throw new IllegalStateException("Key not found: " + c);
        }
    }


    public List<Cell> getCells(Cell cellType) {
        return cells.values().stream().filter(x -> x == cellType).collect(Collectors.toList());
    }

    public long countCell(Cell cellType) {
        return cells.values().stream().filter(x -> x == cellType).count();
    }

    public long getEmptySpacesCount() {
        return countCell(Cell.SPACE);
    }

    public long getWallCount() {
        return countCell(Cell.WALL);
    }

}
