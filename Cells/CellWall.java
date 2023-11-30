package Cells;


public class CellWall extends Cell {
    public CellWall(int row, int column) {
        super(row, column);
        isMovable = false;
    }
}
