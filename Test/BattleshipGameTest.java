import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BattleshipGameTest {
    private BattleshipGame game;

    @BeforeEach
    void setUp() {
        game = new BattleshipGame();
    }

    @Test
    void resetGame() {
        assertEquals(0, game.getMissCounter());
        assertEquals(0, game.getStrikeCounter());
        assertEquals(0, game.getTotalMissCounter());
        assertEquals(0, game.getTotalHitCounter());
        assertNotNull(game.getBoard());
        assertFalse(game.isGameOver());
    }

    @Test
    void processShotHit() {
        Board board = game.getBoard();

        for (int r = 0; r < Board.GRID_SIZE; r++) {
            for (int c = 0; c < Board.GRID_SIZE; c++) {
                if (board.getCell(r, c).hasShip()) {
                    boolean hit = game.processShot(r, c);
                    assertTrue(hit);
                    assertEquals(1, game.getTotalHitCounter());
                    assertEquals(0, game.getMissCounter());
                    return;
                }
            }
        }
    }

    @Test
    void processShotFiveMissesMakeStrike() {
        Board board = game.getBoard();
        int missCount = 0;

        for (int r = 0; r < Board.GRID_SIZE && missCount < 5; r++) {
            for (int c = 0; c < Board.GRID_SIZE && missCount < 5; c++) {
                if (!board.getCell(r, c).hasShip()) {
                    boolean hit = game.processShot(r, c);
                    assertFalse(hit);
                    missCount++;
                }
            }
        }

        assertEquals(0, game.getMissCounter());
        assertEquals(1, game.getStrikeCounter());
        assertEquals(5, game.getTotalMissCounter());
    }

    @Test
    void isWon() {
        assertFalse(game.isWon());
        Board board = game.getBoard();

        for (int r = 0; r < Board.GRID_SIZE; r++) {
            for (int c = 0; c < Board.GRID_SIZE; c++) {
                if (board.getCell(r, c).hasShip()) {
                    game.processShot(r, c);
                }
            }
        }

        assertEquals(17, game.getTotalHitCounter());
        assertTrue(game.isWon());
        assertTrue(game.isGameOver());
    }

    @Test
    void isLost() {
        assertFalse(game.isLost());
        Board board = game.getBoard();
        int missCount = 0;

        for (int r = 0; r < Board.GRID_SIZE && missCount < 15; r++) {
            for (int c = 0; c < Board.GRID_SIZE && missCount < 15; c++) {
                if (!board.getCell(r, c).hasShip()) {
                    game.processShot(r, c);
                    missCount++;
                }
            }
        }

        assertEquals(3, game.getStrikeCounter());
        assertTrue(game.isLost());
        assertTrue(game.isGameOver());
    }
}