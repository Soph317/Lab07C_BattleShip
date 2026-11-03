public class BattleshipGame {
    Board board;
    int missCounter;
    int strikeCounter;
    int totalMissCounter;
    int totalHitCounter;

    public BattleshipGame() {
        this.board = new Board();
        resetGame();
    }

    public void resetGame() {
        missCounter = 0;
        strikeCounter = 0;
        totalMissCounter = 0;
        totalHitCounter = 0;
        board.resetBoard();
    }

    public boolean processShot(int row, int col) {
        boolean hit = board.fireAt(row, col);

        if (hit) {
            totalHitCounter++;
            missCounter = 0;
        } else {
            missCounter++;
            totalMissCounter++;
            if (missCounter >= 5) {
                strikeCounter++;
                missCounter = 0;
            }
        }
        return hit;
    }

    public boolean isWon() {
        return totalHitCounter >= 17;
    }

    public boolean isLost() {
        return strikeCounter >= 3;
    }

    public boolean isGameOver() {
        return isWon() || isLost();
    }

    public Board getBoard() {
        return board;
    }

    public int getMissCounter() {
        return missCounter;
    }

    public int getStrikeCounter() {
        return strikeCounter;
    }

    public int getTotalMissCounter() {
        return totalMissCounter;
    }

    public int getTotalHitCounter() {
        return totalHitCounter;
    }
}