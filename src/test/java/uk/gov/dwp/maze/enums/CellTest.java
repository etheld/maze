package uk.gov.dwp.maze.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.assertj.core.api.Assertions.assertThat;

class CellTest {

    @Test
    void getEnumShouldReturnWallForCharacterX() {
        // given
        // when
        Cell cell = Cell.getEnum('X');
        // then
        assertThat(cell).isEqualTo(Cell.WALL);
    }

    @Test
    void getEnumShouldReturnSpaceForEmptySpace() {
        // given
        // when
        Cell cell = Cell.getEnum(' ');
        // then
        assertThat(cell).isEqualTo(Cell.SPACE);
    }

    @Test
    void getEnumShouldReturnStartForCharacterS() {
        // given
        // when
        Cell cell = Cell.getEnum('S');
        // then
        assertThat(cell).isEqualTo(Cell.START);
    }

    @Test
    void getEnumShouldReturnFinishForCharacterF() {
        // given
        // when
        Cell cell = Cell.getEnum('F');
        // then
        assertThat(cell).isEqualTo(Cell.FINISH);
    }

    @Test
    void getEnumShouldThrowExceptionForCharacterL() {
        // given
        // when
        Throwable throwable = assertThrows(IllegalArgumentException.class,
                () -> Cell.getEnum('L'));

        // then
        assertThat(throwable.getMessage()).isEqualTo("Unrecognized field: 'L'");
    }

}
