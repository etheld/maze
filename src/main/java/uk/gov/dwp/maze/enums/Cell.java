package uk.gov.dwp.maze.enums;

import java.util.Arrays;
import java.util.List;

public enum Cell {

    WALL('X'),
    START('S'),
    FINISH('F'),
    SPACE(' ');

    final Character fieldValue;

    Cell(Character c) {
        this.fieldValue = c;
    }


    public static Cell getEnum(char fieldCode) {
        List<Cell> list = Arrays.asList(Cell.values());
        return list.stream().
                filter(m -> m.fieldValue.equals(fieldCode)).
                findAny().
                orElseThrow(() -> new IllegalArgumentException(String.format("Unrecognized field: '%s'", fieldCode)));
    }

}
