package uk.gov.dwp.maze;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.gov.dwp.maze.data.Coord;
import uk.gov.dwp.maze.enums.Cell;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MazeTest {

    private static final String VALID_MAZE_LINE = " XSFX ";
    private Maze underTest;

    @Test
    @DisplayName("maze can parse all the field types into positions")
    void mazeParsesEveryFieldType() {

        // given
        String validMazeLine = "XSFX ";

        // when
        underTest = new Maze(Collections.singletonList(validMazeLine));

        // then
        assertThat(underTest.getCells().keySet().size()).isEqualTo(5);

        assertThat(underTest.getCells().get(new Coord(1, 1))).isEqualTo(Cell.WALL);
        assertThat(underTest.getCells().get(new Coord(2, 1))).isEqualTo(Cell.START);
        assertThat(underTest.getCells().get(new Coord(3, 1))).isEqualTo(Cell.FINISH);
        assertThat(underTest.getCells().get(new Coord(4, 1))).isEqualTo(Cell.WALL);
        assertThat(underTest.getCells().get(new Coord(5, 1))).isEqualTo(Cell.SPACE);

    }

    @Test
    @DisplayName("maze parser throws IllegalArgumentException when it encounters unknown field")
    void mazeParserThrowsAnExceptionWhenUnknownFieldIsFound() {

        // given
        String invalidMazeLine = "XSCFX ";

        // when
        Throwable throwable = assertThrows(IllegalArgumentException.class,
                () -> underTest = new Maze(Collections.singletonList(invalidMazeLine)));

        // then
        assertThat(throwable.getMessage()).isEqualTo("Unrecognized field: 'C'");

    }

    @Test
    @DisplayName("maze parser throws IllegalStateException when there are more than 1 start")
    void mazeParserThrowsAnExceptionWhenThereAreMoreThanOneStart() {
        // given
        String invalidMazeLine = "XSSFX ";

        // when
        Throwable throwable = assertThrows(IllegalStateException.class,
                () -> underTest = new Maze(Collections.singletonList(invalidMazeLine)));
        // then
        assertThat(throwable.getMessage()).isEqualTo("Not valid maze, only 1 startpoint is allowed, now there is/are: 2");

    }

    @Test
    @DisplayName("maze parser throws IllegalStateException when there are more than 1 finish")
    void mazeParserThrowsAnExceptionWhenThereAreMoreThanOneFinish() {
        // given
        String invalidMazeLine = "XSFFX ";

        // when
        Throwable throwable = assertThrows(IllegalStateException.class,
                () -> underTest = new Maze(Collections.singletonList(invalidMazeLine)));
        // then
        assertThat(throwable.getMessage()).isEqualTo("Not valid maze, only 1 finishpoint is allowed, now there is/are: 2");

    }

    @Test
    @DisplayName("getEmptySpaces should return how many empty spaces are there on the map")
    void getEmptySpacesCountCorrectNumberOfEmptySpaces() {
        // given
        underTest = new Maze(Collections.singletonList(VALID_MAZE_LINE));

        // when
        long emptySpacesCount = underTest.getEmptySpacesCount();

        // then
        assertThat(emptySpacesCount).isEqualTo(2);
    }

    @Test
    @DisplayName("getwallCount should return how many walls are there on the map")
    void getWallCountReturnsCorrectNumberOfWalls() {
        // given
        underTest = new Maze(Collections.singletonList(VALID_MAZE_LINE));

        // when
        long wallCount = underTest.getWallCount();

        // then
        assertThat(wallCount).isEqualTo(2);
    }

    @Test
    @DisplayName("getPoint returns correct cell type of any given coordinate")
    void getPointReturnsCorrectCell() {
        // given
        underTest = new Maze(Collections.singletonList(VALID_MAZE_LINE));

        // when
        Cell cell = underTest.getPoint(new Coord(1, 1));

        // then
        assertThat(cell).isEqualTo(Cell.SPACE);

    }

    @Test
    @DisplayName("getPoint throws exception when you ask for an invalid coordinate")
    void getPointThrowsExceptionWhenCellIsNotFound() {
        // given
        Coord invalidCoordinate = new Coord(2, 11);
        underTest = new Maze(Collections.singletonList("XSFX "));

        // when
        Throwable throwable = assertThrows(IllegalStateException.class,
                () -> underTest.getPoint(invalidCoordinate));


        // then
        assertThat(throwable.getMessage()).isEqualTo("Key not found: Coord{x=2, y=11}");


    }

}
