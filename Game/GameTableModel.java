package Game;

import Cells.Cell;
import Cells.CellPoint;
import Cells.CellWall;

public class GameTableModel {
    public Cell[][] cells;

    public GameTableModel(char[][] board) {
        cells = new Cell[board.length][];
        initializeCells(board);
    }

    private void initializeCells(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            cells[i] = new Cell[board[i].length];
            for (int j = 0; j < board[i].length; j++) {
                initializeCell(board[i][j], i, j);
            }
        }
    }

    private void initializeCell(char symbol, int row, int column) {
        switch (symbol) {
            case 's':
                cells[row][column] = new CellPoint(row, column);
                break;
            case 'w':
                cells[row][column] = new CellWall(row, column);
                break;
            default:
                cells[row][column] = null;
                break;
        }
    }

    public Cell[][] getCells() {
        return cells;
    }
}
