package uk.gov.dwp.maze;

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
    void moveExplorer() {

        // given
        underTest = new Explorer(Facing.NORTH, Arrays.asList("     ", " XSFX "));

        // when
        underTest.moveExplorerForward();

        // then
        assertThat(underTest.getPosition()).isEqualTo(new Coord(3, 1));

    }

    @Test
    void getCellInFrontShouldTellWhatCellIsInFrontOfExplorer() {

        // given
        underTest = new Explorer(Facing.NORTH, Arrays.asList("     ", " XSFX "));

        // when
        Cell cell = underTest.getCellInFront();

        // then
        assertThat(cell).isEqualTo(Cell.SPACE);

    }

    @Test
    void getPossibleMovesReturnsAllCoordinatesThatAreValidToMoveTo() {

        // given
        underTest = new Explorer(Facing.NORTH, Arrays.asList("     ", " XSFX ", "      "));

        // when
        List<Coord> possibleMoves = underTest.getPossibleMoves();

        // then
        assertThat(possibleMoves).containsExactlyInAnyOrder(new Coord(4, 2),
                new Coord(3, 1),
                new Coord(3, 3));

    }


    @Test
    void getTrailShouldContainAllMoves() {

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
    void getTrailShouldContainAllMovesThatActuallyHappened() {

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

    private class CellTuple {
        CellTuple(final Facing before, final Facing after) {
            this.before = before;
            this.after = after;
        }

        Facing before;
        Facing after;
    }


}
