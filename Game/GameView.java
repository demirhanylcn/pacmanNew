package Game;

import Cells.Cell;
import Inputs.Inputs;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameView extends JFrame {
    private GameController controller;
    private final JTable gameBoardTable;
    private final GameBoardTableModel tableModel;
    private final GameModel model;
    private final JLabel score;
    private final JPanel bottomPanel;

    public GameView(GameModel model) {
        this.model = model;
        this.bottomPanel = new JPanel(new BorderLayout());
        updateBottomPanel();
        setTitle("Pac-Man");
        JPanel upPanel = new JPanel(new FlowLayout());

        score = new JLabel("", SwingConstants.CENTER);
        score.setForeground(Color.WHITE);
        upPanel.add(score);
        upPanel.setBackground(Color.BLACK);
        updateScore();
        tableModel = new GameBoardTableModel(model.gameBoard);



        gameBoardTable = new JTable(tableModel);
        gameBoardTable.setDefaultRenderer(Object.class, new GameTableRenderer(model.getPlayerList()));
        int blockSize = 30;
        configureGameBoardTable(blockSize);

        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPressed(e);
                handleSpecialKeyPressed(e);
            }
        });

        add(upPanel, BorderLayout.NORTH);
        add(gameBoardTable, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        setMinimumSize(new Dimension(600, 600));

        ImageIcon icon = new ImageIcon("src/resources/redGhostLeft.png");
        setIconImage(icon.getImage());

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                handleWindowClosing();
            }
        });
        pack();
        setLocationRelativeTo(null);
    }

    public void repaintBoard() {
        gameBoardTable.repaint();
    }

    public void showGame() {
        setVisible(true);
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }

    public void setCellValue(Cell cell, int row, int column) {
        tableModel.setValueAt(cell, row, column);
    }

    public void updateScore() {
        score.setText("Score : " + model.getCurrentScore());
    }

    public void updateBottomPanel() {
        bottomPanel.removeAll();
        bottomPanel.revalidate();
        bottomPanel.repaint();

        JPanel health = new JPanel(new FlowLayout(FlowLayout.LEADING));
        configurePanel(health);

        JPanel bonuses = new JPanel(new FlowLayout());
        configurePanel(bonuses);

        addHealthIcons(health);
        addBonusIcons(bonuses);
    }

    public static String askName() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.setBackground(Color.ORANGE);

        JLabel label = new JLabel("Enter your nickname:");
        label.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(label);
        panel.add(textField);

        ImageIcon icon = new ImageIcon("src/resources/orangeGhostRight.png");

        int option = JOptionPane.showOptionDialog(
                null,
                panel,
                "Nickname",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                icon,
                null,
                null
        );

        if (option == JOptionPane.OK_OPTION) {
            return textField.getText();
        } else {
            return null;
        }
    }

    private void configureGameBoardTable(int blockSize) {
        gameBoardTable.setRowHeight(blockSize);
        gameBoardTable.setFocusable(false);
        gameBoardTable.setShowGrid(false);
        gameBoardTable.setShowVerticalLines(false);
        gameBoardTable.setShowHorizontalLines(false);
        gameBoardTable.setRowMargin(0);
        gameBoardTable.getColumnModel().setColumnMargin(0);

        for (int i = 0; i < gameBoardTable.getColumnCount(); i++) {
            TableColumn column = gameBoardTable.getColumnModel().getColumn(i);
            column.setPreferredWidth(blockSize);
        }
    }

    private void configurePanel(JPanel panel) {
        panel.setBackground(Color.BLACK);
        bottomPanel.setBackground(Color.BLACK);
    }

    private void handleKeyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> controller.setPacmanDirection(Inputs.UP);
            case KeyEvent.VK_DOWN -> controller.setPacmanDirection(Inputs.DOWN);
            case KeyEvent.VK_LEFT -> controller.setPacmanDirection(Inputs.LEFT);
            case KeyEvent.VK_RIGHT -> controller.setPacmanDirection(Inputs.RIGHT);
        }
    }

    private void handleSpecialKeyPressed(KeyEvent e) {
        int down = KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK;
        if ((e.getModifiersEx() & down) == down && (e.getKeyCode() == KeyEvent.VK_Q))
            controller.stopGame();
    }

    private void handleWindowClosing() {
        controller.stopGame();

    }

    private void addHealthIcons(JPanel health) {
        for (int i = 0; i < model.getCurrentHealth(); i++) {
            health.add(new JLabel(new ImageIcon("src/resources/heart.png")));
        }
        bottomPanel.add(health, BorderLayout.WEST);
    }

    private void addBonusIcons(JPanel bonuses) {
        for (int i = 0; i < model.getUpgradeList().size(); i++) {
            bonuses.add(new JLabel(new ImageIcon(model.getUpgradeList().get(i).getImage())));
        }
        bottomPanel.add(bonuses, BorderLayout.EAST);
    }
}
