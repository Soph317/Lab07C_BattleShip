import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ShipTest {
    private Ship ship;

    @BeforeEach
    void setUp() {
        ship = new Ship("Destroyer", 2);
    }

    @Test
    void recordHit() {
        ship.recordHit();
        assertEquals(1, ship.getHits());
    }

    @Test
    void isSunk() {
        assertFalse(ship.isSunk());
        ship.recordHit();
        ship.recordHit();
        assertTrue(ship.isSunk());
    }

    @Test
    void getName() {
        assertEquals("Destroyer", ship.getName());
    }

    @Test
    void getLength() {
        assertEquals(2, ship.getLength());
    }

    @Test
    void getHits() {
        assertEquals(0, ship.getHits());
        ship.recordHit();
        assertEquals(1, ship.getHits());
    }
}