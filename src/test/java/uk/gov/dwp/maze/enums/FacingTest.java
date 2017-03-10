package uk.gov.dwp.maze.enums;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static uk.gov.dwp.maze.enums.Facing.EAST;
import static uk.gov.dwp.maze.enums.Facing.NORTH;
import static uk.gov.dwp.maze.enums.Facing.SOUTH;
import static uk.gov.dwp.maze.enums.Facing.WEST;

class FacingTest {

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
                    String testName = String.format("getLeft from %s should be %s", data.before, data.after);

                    return dynamicTest(testName, () -> {
                        // given

                        // when
                        Facing left = data.before.getLeft();

                        // then
                        assertThat(left).isEqualTo(data.after);
                    });
                });
    }

    @TestFactory
    Stream<DynamicTest> turnRightTests() {
        return turnRightData.stream()
                .map(data -> {
                    String testName = String.format("getRight from %s should be %s", data.before, data.after);

                    return dynamicTest(testName, () -> {
                        // given

                        // when
                        Facing left = data.before.getRight();

                        // then
                        assertThat(left).isEqualTo(data.after);
                    });
                });
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
