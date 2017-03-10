package uk.gov.dwp.maze.data;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CoordTest {

    private Coord underTest;

    @Test
    void getNeighbours() {
        // given
        underTest = new Coord(3, 3);

        // when
        List<Coord> neighbours = underTest.getNeighbours();

        // then
        assertThat(neighbours).containsExactly(
                new Coord(2, 3),
                new Coord(4, 3),
                new Coord(3, 4),
                new Coord(3, 2)
        );

    }

}
