package Game;

import Cells.Cell;

import javax.swing.table.AbstractTableModel;

public class GameBoardTableModel extends AbstractTableModel {
    private final GameTableModel gameTableModel;

    public GameBoardTableModel(GameTableModel gameTableModel) {
        this.gameTableModel = gameTableModel;
    }

    @Override
    public int getRowCount() {
        return gameTableModel.cells.length;
    }

    @Override
    public int getColumnCount() {
        return gameTableModel.cells[0].length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return gameTableModel.cells[rowIndex][columnIndex];
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        gameTableModel.cells[rowIndex][columnIndex] = (Cell) value;
        fireTableCellUpdated(rowIndex, columnIndex);
    }
}
