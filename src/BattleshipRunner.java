import javax.swing.SwingUtilities;

public class BattleshipRunner {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BattleshipFrame frame = new BattleshipFrame();
            frame.setVisible(true);
        });
    }
}