import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    public static int GRID_SIZE = 10;
    Cell[][] grid;
    List<Ship> ships;
    Random rand;

    public Board() {
        grid = new Cell[GRID_SIZE][GRID_SIZE];
        ships = new ArrayList<>();
        rand = new Random();
        resetBoard();
    }

    public void resetBoard() {
        ships.clear();
        for (int r = 0; r < GRID_SIZE; r++) {
            for (int c = 0; c < GRID_SIZE; c++) {
                grid[r][c] = new Cell(r, c);
            }
        }
        placeShipsRandomly();
    }

    public void placeShipsRandomly() {
        int[] lengths = {5, 4, 3, 3, 2};
        String[] names = {"Carrier", "Battleship", "Cruiser", "Submarine", "Destroyer"};

        for (int i = 0; i < lengths.length; i++) {
            Ship ship = new Ship(names[i], lengths[i]);
            boolean placed = false;

            while (!placed) {
                boolean horizontal = rand.nextBoolean();
                int row = rand.nextInt(GRID_SIZE);
                int col = rand.nextInt(GRID_SIZE);

                if (canPlaceShip(row, col, ship.getLength(), horizontal)) {
                    for (int k = 0; k < ship.getLength(); k++) {
                        int r = horizontal ? row : row + k;
                        int c = horizontal ? col + k : col;
                        grid[r][c].setShip(ship);
                    }
                    ships.add(ship);
                    placed = true;
                }
            }
        }
    }

    private boolean canPlaceShip(int row, int col, int length, boolean horizontal) {
        if (horizontal) {
            if (col + length > GRID_SIZE) return false;
            for (int c = col; c < col + length; c++) {
                if (grid[row][c].hasShip()) return false;
            }
        } else {
            if (row + length > GRID_SIZE) return false;
            for (int r = row; r < row + length; r++) {
                if (grid[r][col].hasShip()) return false;
            }
        }
        return true;
    }

    public boolean fireAt(int row, int col) {
        Cell cell = grid[row][col];
        if (cell.hasShip()) {
            cell.setStatus("HIT");
            cell.getShip().recordHit();
            return true;
        } else {
            cell.setStatus("MISS");
            return false;
        }
    }

    public boolean isShipSunkAt(int row, int col) {
        Cell cell = grid[row][col];
        return cell.hasShip() && cell.getShip().isSunk();
    }

    public Cell getCell(int row, int col) {
        return grid[row][col];
    }

    public List<Ship> getShips() {
        return ships;
    }
}