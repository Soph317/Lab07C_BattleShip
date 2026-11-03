public class Cell {
    private final int row;
    private final int col;
    private String status;
    private Ship ship;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.status = "BLANK";
        this.ship = null;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public boolean hasShip() {
        return ship != null;
    }
}