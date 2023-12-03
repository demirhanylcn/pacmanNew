package Game;

import Cells.*;
import Characters.GameCharacter;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;


public class GameTableRenderer extends DefaultTableCellRenderer {
    private List<GameCharacter> characters;
    final long startNanoTime = System.nanoTime();


    public GameTableRenderer(List<GameCharacter> characters) {
        this.characters = characters;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        try {
            Cell cellValue = (Cell) value;
            GameCharacter gameCharacter = findCharacterAt(row, column);
            if (gameCharacter != null) {
                updateCellForCharacter(gameCharacter);
            } else {
                updateCellForCellType(cellValue);
            }
        } catch (Exception e) {
            handleException(e);
        }
        return this;
    }
    private GameCharacter findCharacterAt(int row, int column) {
        return characters.stream()
                .filter(player -> player.getColumn() == column && player.getRow() == row)
                .findFirst()
                .orElse(null);
    }
    private void updateCellForCharacter(GameCharacter gameCharacter) {
        double elapsedTimeSeconds = (System.nanoTime() - startNanoTime) / 1000000000.0;
        setBackground(Color.BLACK);
        setText("");
        setIcon(new ImageIcon(gameCharacter.getAnimated().getFrame(elapsedTimeSeconds)));
        setHorizontalAlignment(JLabel.CENTER);
    }
    private void updateCellForCellType(Cell cellValue) {
        setBackground(Color.BLACK);
        if (cellValue instanceof CellWall) {
            updateCellForWall();
        } else if (cellValue instanceof CellPoint) {
            updateCellForPoint();
        } else if (cellValue instanceof CellEmpty) {
            updateCellForEmpty();
        } else if (cellValue instanceof CellUpgrade) {
            updateCellForUpgrade((CellUpgrade) cellValue);
        }
    }
    private void updateCellForWall() {
        setBackground(Color.ORANGE);
        setText("");
        setIcon(null);
    }

    private void updateCellForPoint() {
        setBackground(Color.BLACK);
        setForeground(Color.ORANGE);
        setFont(new Font("Serif", Font.BOLD, 15));
        setText("·");
        setIcon(null);
    }

    private void updateCellForEmpty() {
        setBackground(Color.BLACK);
        setText("");
        setIcon(null);
    }

    private void updateCellForUpgrade(CellUpgrade cellUpgrade) {
        setBackground(Color.BLACK);
        setText("");
        setIcon(new ImageIcon(cellUpgrade.getUpgrade().getImage()));
        setHorizontalAlignment(JLabel.CENTER);
    }

    private void handleException(Exception e) {
        e.printStackTrace();
    }


}
