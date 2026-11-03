import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
    }

    @Test
    void resetBoard() {
        assertEquals(10, Board.GRID_SIZE);
        for (int r = 0; r < Board.GRID_SIZE; r++) {
            for (int c = 0; c < Board.GRID_SIZE; c++) {
                assertNotNull(board.getCell(r, c));
                assertEquals(r, board.getCell(r, c).getRow());
                assertEquals(c, board.getCell(r, c).getCol());
            }
        }
    }

    @Test
    void placeShipsRandomly() {
        assertEquals(5, board.getShips().size());

        int totalShipCells = 0;
        for (int r = 0; r < Board.GRID_SIZE; r++) {
            for (int c = 0; c < Board.GRID_SIZE; c++) {
                if (board.getCell(r, c).hasShip()) {
                    totalShipCells++;
                }
            }
        }
        assertEquals(17, totalShipCells);
    }

    @Test
    void fireAt() {
        int hitR = -1, hitC = -1;
        int missR = -1, missC = -1;

        for (int r = 0; r < Board.GRID_SIZE; r++) {
            for (int c = 0; c < Board.GRID_SIZE; c++) {
                if (board.getCell(r, c).hasShip() && hitR == -1) {
                    hitR = r;
                    hitC = c;
                } else if (!board.getCell(r, c).hasShip() && missR == -1) {
                    missR = r;
                    missC = c;
                }
            }
        }

        assertTrue(board.fireAt(hitR, hitC));
        assertEquals("HIT", board.getCell(hitR, hitC).getStatus());

        assertFalse(board.fireAt(missR, missC));
        assertEquals("MISS", board.getCell(missR, missC).getStatus());
    }

    @Test
    void isShipSunkAt() {
        Ship targetShip = board.getShips().get(0);
        assertFalse(targetShip.isSunk());

        for (int r = 0; r < Board.GRID_SIZE; r++) {
            for (int c = 0; c < Board.GRID_SIZE; c++) {
                if (board.getCell(r, c).getShip() == targetShip) {
                    board.fireAt(r, c);
                }
            }
        }

        assertTrue(targetShip.isSunk());
    }

    @Test
    void getCell() {
        Cell cell = board.getCell(0, 0);
        assertNotNull(cell);
        assertEquals(0, cell.getRow());
        assertEquals(0, cell.getCol());
    }

    @Test
    void getShips() {
        assertNotNull(board.getShips());
        assertEquals(5, board.getShips().size());
    }
}