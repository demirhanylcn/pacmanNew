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
            GameCharacter gameCharacter = characters.stream().filter(player1 -> player1.getColumn()==column && player1.getRow()==row).findFirst().orElse(null);
            if(gameCharacter!=null){
                double t = (System.nanoTime() - startNanoTime) / 1000000000.0;
                setBackground(Color.BLACK);
                setText("");
                setIcon(new ImageIcon(gameCharacter.getAnimated().getFrame(t)));
                setHorizontalAlignment(JLabel.CENTER);
                return this;
            }


            if (cellValue instanceof CellWall) {
                setBackground(Color.ORANGE);
                setText("");
                setIcon(null);
            } else if (cellValue instanceof CellPoint) {
                setBackground(Color.BLACK);
                setForeground(Color.ORANGE);
                table.setFont(new Font("Serif", Font.BOLD, 15));
                setText("·");
                setIcon(null);
            }else if(cellValue instanceof CellEmpty){
                setBackground(Color.BLACK);
                setText("");
                setIcon(null);
            }else {
                setBackground(Color.BLACK);
                setText("");
                setIcon(new ImageIcon(((CellUpgrade) cellValue).getUpgrade().getImage()));
                setHorizontalAlignment(JLabel.CENTER);
            }
        }
        catch (Exception e){
            //
        }
        return this;
    }
}
