import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CellTest {
    private Cell cell;

    @BeforeEach
    void setUp() {
        cell = new Cell(3, 4);
    }

    @Test
    void getRow() {
        assertEquals(3, cell.getRow());
    }

    @Test
    void getCol() {
        assertEquals(4, cell.getCol());
    }

    @Test
    void getStatus() {
        assertEquals("BLANK", cell.getStatus());
    }

    @Test
    void setStatus() {
        cell.setStatus("HIT");
        assertEquals("HIT", cell.getStatus());
        cell.setStatus("MISS");
        assertEquals("MISS", cell.getStatus());
    }

    @Test
    void getShipAndSetShip() {
        assertNull(cell.getShip());
        Ship cruiser = new Ship("Cruiser", 3);
        cell.setShip(cruiser);
        assertEquals(cruiser, cell.getShip());
    }

    @Test
    void hasShip() {
        assertFalse(cell.hasShip());
        cell.setShip(new Ship("Submarine", 3));
        assertTrue(cell.hasShip());
    }
}