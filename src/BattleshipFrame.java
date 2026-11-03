import javax.swing.*;
import java.awt.*;

public class BattleshipFrame extends JFrame {
    BattleshipGame game;
    JButton[][] boardButtons;
    boolean[][] visited;

    JLabel missLabel, strikeLabel, totalMissLabel, totalHitLabel, titleLabel;
    JButton playAgainButton, quitButton;
    JPanel gridPanel, statusPanel, buttonPanel, bottomPanel;

    public BattleshipFrame() {
        super("Battleship - Lab 07C");
        game = new BattleshipGame();
        boardButtons = new JButton[Board.GRID_SIZE][Board.GRID_SIZE];
        visited = new boolean[Board.GRID_SIZE][Board.GRID_SIZE];
        createWindow();
    }

    public void createWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        titleLabel = new JLabel("BATTLESHIP", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        add(titleLabel, BorderLayout.NORTH);

        gridPanel = new JPanel(new GridLayout(Board.GRID_SIZE, Board.GRID_SIZE, 2, 2));

        for (int r = 0; r < Board.GRID_SIZE; r++) {
            for (int c = 0; c < Board.GRID_SIZE; c++) {
                final int row = r;
                final int col = c;
                JButton button = new JButton("~");
                button.setForeground(new Color(0, 102, 204));
                button.setPreferredSize(new Dimension(45, 45));
                button.setFont(new Font("Arial", Font.BOLD, 16));
                button.setFocusPainted(false);

                button.addActionListener(e -> cellClicked(row, col));
                boardButtons[r][c] = button;
                gridPanel.add(button);
            }
        }
        add(gridPanel, BorderLayout.CENTER);
        statusPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        statusPanel.setBorder(BorderFactory.createTitledBorder("Status"));


        missLabel = new JLabel("MISS: 0 / 5", SwingConstants.CENTER);
        strikeLabel = new JLabel("STRIKE: 0 / 3", SwingConstants.CENTER);
        totalMissLabel = new JLabel("TOTAL MISS: 0 / 83", SwingConstants.CENTER);
        totalHitLabel = new JLabel("TOTAL HIT: 0 / 17", SwingConstants.CENTER);

        statusPanel.add(missLabel);
        statusPanel.add(strikeLabel);
        statusPanel.add(totalMissLabel);
        statusPanel.add(totalHitLabel);

        buttonPanel = new JPanel(new FlowLayout());
        playAgainButton = new JButton("Play Again");
        quitButton = new JButton("Quit");

        playAgainButton.addActionListener(e -> askToPlayAgain());
        quitButton.addActionListener(e -> askToQuit());

        buttonPanel.add(playAgainButton);
        buttonPanel.add(quitButton);

        bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(statusPanel, BorderLayout.NORTH);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        pack();
        setResizable(false);
        setSize(520, 620);
        setLocationRelativeTo(null);
    }

    public void cellClicked(int row, int col) {
        if (visited[row][col]) {
            return;
        }
        visited[row][col] = true;

        JButton button = boardButtons[row][col];
        boolean hit = game.processShot(row, col);

        if (hit) {
            button.setText("X");
            button.setForeground(Color.RED);
        } else {
            button.setText("M");
            button.setForeground(new Color(210, 160, 0));
        }

        updateDisplays();
        totalHitLabel.paintImmediately(totalHitLabel.getVisibleRect());
        missLabel.paintImmediately(missLabel.getVisibleRect());
        strikeLabel.paintImmediately(strikeLabel.getVisibleRect());
        totalMissLabel.paintImmediately(totalMissLabel.getVisibleRect());

        if (hit && game.getBoard().isShipSunkAt(row, col)) {
            Ship sunkShip = game.getBoard().getCell(row, col).getShip();
            JOptionPane.showMessageDialog(this, "You sunk the " + sunkShip.getName() + "!");
        }

        if (game.isWon()) {
            int choice = JOptionPane.showConfirmDialog(this, "You won! Play again?", "Victory", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                restartBoard();
            } else {
                System.exit(0);
            }
        } else if (game.isLost()) {
            int choice = JOptionPane.showConfirmDialog(this, "You lost! Play again?", "Defeat", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                restartBoard();
            } else {
                System.exit(0);
            }
        }
    }

    public void updateDisplays() {
        missLabel.setText("MISS: " + game.getMissCounter() + " / 5");
        strikeLabel.setText("STRIKE: " + game.getStrikeCounter() + " / 3");
        totalMissLabel.setText("TOTAL MISS: " + game.getTotalMissCounter() + " / 83");
        totalHitLabel.setText("TOTAL HIT: " + game.getTotalHitCounter() + " / 17");
    }

    public void restartBoard() {
        game.resetGame();
        for (int r = 0; r < Board.GRID_SIZE; r++) {
            for (int c = 0; c < Board.GRID_SIZE; c++) {
                visited[r][c] = false;
                boardButtons[r][c].setText("~");
                boardButtons[r][c].setForeground(new Color(0, 102, 204));
            }
        }
        updateDisplays();
    }

    private void askToPlayAgain() {
        int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to start a new game?", "Play Again", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            restartBoard();
        }
    }

    private void askToQuit() {
        int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}