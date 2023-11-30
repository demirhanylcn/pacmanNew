package Cells;

public class Cell {
    private int row;
    private int column;
    protected boolean isMovable;

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
    }


    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public boolean isMovable() {
        return isMovable;
    }
}
