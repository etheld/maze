package uk.gov.dwp.maze;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import uk.gov.dwp.maze.data.Coord;
import uk.gov.dwp.maze.enums.Cell;
import uk.gov.dwp.maze.enums.Facing;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static uk.gov.dwp.maze.enums.Facing.EAST;
import static uk.gov.dwp.maze.enums.Facing.NORTH;
import static uk.gov.dwp.maze.enums.Facing.SOUTH;
import static uk.gov.dwp.maze.enums.Facing.WEST;


class ExplorerTest {

    private Explorer underTest;

    private Collection<CellTuple> turnLeftData = Arrays.asList(
            new CellTuple(NORTH, WEST),
            new CellTuple(WEST, SOUTH),
            new CellTuple(SOUTH, EAST),
            new CellTuple(EAST, NORTH));

    private Collection<CellTuple> turnRightData = Arrays.asList(
            new CellTuple(NORTH, EAST),
            new CellTuple(WEST, NORTH),
            new CellTuple(SOUTH, WEST),
            new CellTuple(EAST, SOUTH));

    @TestFactory
    Stream<DynamicTest> turnLeftTests() {
        return turnLeftData.stream()
                .map(data -> {
                    String testName = String.format("turnLeft from %s should be %s", data.before, data.after);

                    return dynamicTest(testName, () -> {
                        underTest = new Explorer(data.before, Arrays.asList("XSFX"));
                        underTest.turnLeft();
                        assertThat(underTest.getFacing()).isEqualTo(data.after);
                    });
                });
    }

    @TestFactory
    Stream<DynamicTest> turnRightTests() {
        return turnRightData.stream()
                .map(data -> {
                    String testName = String.format("turnRight from %s should be %s", data.before, data.after);

                    return dynamicTest(testName, () -> {
                        underTest = new Explorer(data.before, Arrays.asList("XSFX"));
                        underTest.turnRight();
                        assertThat(underTest.getFacing()).isEqualTo(data.after);
                    });
                });
    }

    @Test
    @DisplayName("when there is no wall in front of the explorer moveExplorerForward should be able to move explorer to the position in front of the explorer")
    void moveExplorerForwardEnablesExplorerToMoveForward() {

        // given
        underTest = new Explorer(Facing.NORTH, Arrays.asList("     ", " XSFX "));

        // when
        underTest.moveExplorerForward();

        // then
        assertThat(underTest.getPosition()).isEqualTo(new Coord(3, 1));

    }

    @Test
    @DisplayName("getCellInfFront return space when there is an empty space in front of it")
    void getCellInFrontShouldShouldReturnSpaceWhenThereIsAnEmptySpaceInFrontOfIt() {

        // given
        underTest = new Explorer(Facing.NORTH, Arrays.asList("     ", " XSFX "));

        // when
        Cell cell = underTest.getCellInFront();

        // then
        assertThat(cell).isEqualTo(Cell.SPACE);

    }

    @Test
    @DisplayName("getCellInfFront return wall  when there is a wall in front of it")
    void getCellInFrontShouldReturnWallWhenThereIsAWallInFrontOfIt() {

        // given
        underTest = new Explorer(Facing.NORTH, Arrays.asList("  X  ", " XSFX "));

        // when
        Cell cell = underTest.getCellInFront();

        // then
        assertThat(cell).isEqualTo(Cell.WALL);

    }

    @Test
    @DisplayName("getPossibleMoves should return all coordinates that the explorer can move to")
    void getPossibleMovesReturnsAllCoordinatesThatAreValidToMoveTo() {

        // given
        underTest = new Explorer(Facing.NORTH, Arrays.asList("     ", " XSFX ", "      "));

        // when
        List<Coord> possibleMoves = underTest.getPossibleMoves();

        // then
        assertThat(possibleMoves).containsExactlyInAnyOrder(
                new Coord(4, 2),
                new Coord(3, 1),
                new Coord(3, 3)
        );

    }


    @Test
    @DisplayName("moving the explorer should generate a trail log, so it knows where it has been")
    void movingExplorerShouldGenerateTrail() {

        // given
        underTest = new Explorer(Facing.NORTH, Arrays.asList("     ", " XSFX ", "      "));

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
        underTest = new Explorer(Facing.NORTH, Arrays.asList("     ", " XSFX ", "      "));

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

    // helper class
    private class CellTuple {
        CellTuple(final Facing before, final Facing after) {
            this.before = before;
            this.after = after;
        }

        Facing before;
        Facing after;
    }


}
