package uk.gov.dwp.maze;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import uk.gov.dwp.maze.data.Coord;
import uk.gov.dwp.maze.enums.Cell;
import uk.gov.dwp.maze.enums.Facing;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

class ExplorerTest {

    private Explorer underTest;

    @Test
    @DisplayName("when there is no wall in front of the explorer moveExplorerForward should be able to move explorer to the position in front of the explorer")
    void moveExplorerForwardEnablesExplorerToMoveForward() {

        Maze mazeMock = Mockito.mock(Maze.class);

        Map<Coord, Cell> map = new HashMap<>();
        map.put(new Coord(2,2), Cell.START);
        map.put(new Coord(2,1), Cell.SPACE);

        given(mazeMock.getCells()).willReturn(map);
        given(mazeMock.isValidPoint(new Coord(2,1))).willReturn(true);

        // given
        underTest = new Explorer(Facing.NORTH, mazeMock);

        // when
        underTest.moveExplorerForward();

        // then
        assertThat(underTest.getPosition()).isEqualTo(new Coord(2, 1));

    }

    @Test
    @DisplayName("getCellInfFront return space when there is an empty space in front of it")
    void getCellInFrontShouldShouldReturnSpaceWhenThereIsAnEmptySpaceInFrontOfIt() {
        Maze mazeMock = Mockito.mock(Maze.class);

        Map<Coord, Cell> map = new HashMap<>();
        map.put(new Coord(2,2), Cell.START);

        given(mazeMock.getCells()).willReturn(map);
        given(mazeMock.getPoint(new Coord(2,1))).willReturn(Cell.SPACE);

        // given
        underTest = new Explorer(Facing.NORTH, mazeMock);

        // when
        Cell cell = underTest.getCellInFront();

        // then
        assertThat(cell).isEqualTo(Cell.SPACE);

    }

    @Test
    @DisplayName("getCellInfFront return wall  when there is a wall in front of it")
    void getCellInFrontShouldReturnWallWhenThereIsAWallInFrontOfIt() {
        Maze mazeMock = Mockito.mock(Maze.class);

        Map<Coord, Cell> map = new HashMap<>();
        map.put(new Coord(2,2), Cell.START);

        given(mazeMock.getCells()).willReturn(map);
        given(mazeMock.getPoint(new Coord(2,1))).willReturn(Cell.WALL);

        // given
        underTest = new Explorer(Facing.NORTH, mazeMock);

        // when
        Cell cell = underTest.getCellInFront();

        // then
        assertThat(cell).isEqualTo(Cell.WALL);

    }

    @Test
    @DisplayName("getPossibleMoves should return all coordinates that the explorer can move to")
    void getPossibleMovesReturnsAllCoordinatesThatAreValidToMoveTo() {

        Maze mazeMock = Mockito.mock(Maze.class);

        Map<Coord, Cell> map = new HashMap<>();
        map.put(new Coord(2,2), Cell.START);

        given(mazeMock.getCells()).willReturn(map);
        given(mazeMock.isValidPoint(Mockito.any(Coord.class))).willReturn(true);

        // given
        underTest = new Explorer(Facing.NORTH, mazeMock);

        // when
        List<Coord> possibleMoves = underTest.getPossibleMoves();

        // then
        assertThat(possibleMoves).containsExactlyInAnyOrder(
                new Coord(3, 2),
                new Coord(1, 2),
                new Coord(2, 1),
                new Coord(2, 3)
        );

    }


    @Test
    @DisplayName("moving the explorer should generate a trail log, so it knows where it has been")
    void movingExplorerShouldGenerateTrail() {

        // given
        Maze maze = new Maze(Arrays.asList("     ", " XSFX ", "      "));
        underTest = new Explorer(Facing.NORTH, maze);

        // when
        underTest.moveExplorerForward();
        underTest.turnLeft();
        underTest.moveExplorerForward();
        underTest.moveExplorerForward();

        // then
        assertThat(underTest.getTrail()).containsExactly(
                new Coord(3, 2),
                new Coord(3, 1),
                new Coord(2, 1),
                new Coord(1, 1)
        );

    }

    @Test
    @DisplayName("moving the explorer should generate a trail log only when the move actually happened")
    void movingExplorerShouldGenerateTrailButOnlyOnValidMoves() {

        // given
        Maze maze = new Maze(Arrays.asList("     ", " XSFX ", "      "));
        underTest = new Explorer(Facing.NORTH, maze);

        // when
        underTest.moveExplorerForward();
        underTest.moveExplorerForward();
        underTest.turnLeft();
        underTest.moveExplorerForward();
        underTest.moveExplorerForward();

        // then
        assertThat(underTest.getTrail()).containsExactlyInAnyOrder(
                new Coord(3, 2),
                new Coord(3, 1),
                new Coord(2, 1),
                new Coord(1, 1)
        );

    }


}
