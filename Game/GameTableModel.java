package Game;

import Cells.Cell;
import Cells.CellPoint;
import Cells.CellWall;

public class GameTableModel {
    public Cell[][] cells;
    public GameTableModel(char[][] board){
        cells = new Cell[board.length][];
        for (int i = 0; i < board.length; i++) {
            cells[i] = new Cell[board[i].length];
            for (int j = 0; j < board[i].length; j++) {
                if(board[i][j]=='s'){
                    cells[i][j]=new CellPoint(i, j);
                }
                if(board[i][j]=='w'){
                    cells[i][j]=new CellWall(i, j);
                }
            }
        }
    }
    public Cell[][] getCells() {
        return cells;
    }
}
