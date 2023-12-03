package Game;

import javax.swing.*;
import java.awt.*;

public class ScoreView extends JFrame {
    private final ScoreModel model;
    private final JList<String> list;

    public ScoreView(ScoreModel model) {
        this.model = model;
        JScrollPane scroll = new JScrollPane();
        list = new JList<>();
        scroll.setViewportView(list);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setBackground(Color.BLACK);
        list.setForeground(Color.BLUE);
        list.setFont(new Font("Monospaced", Font.PLAIN, 25));
        add(scroll);

        ImageIcon icon = new ImageIcon("src/resources/blueGhostDown.png");
        setIconImage(icon.getImage());
    }

    public JComponent getScoreComponent() {
        return list;
    }

    public void updateScores() {
        DefaultListModel<String> modelList = new DefaultListModel<>();
        for (int i = 0; i < model.getScores().size(); i++) {
            modelList.addElement(model.getScores().get(i).toString());
        }

        SwingUtilities.invokeLater(() -> list.setModel(modelList));
    }

    public void showScore() {
        setSize(600, 600);
        setTitle("Scores");
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
