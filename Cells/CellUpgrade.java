package Cells;


import Upgrades.Upgrade;

public class CellUpgrade extends Cell {

    private final Upgrade upgrade;
    public CellUpgrade(int row, int column, Upgrade upgrade) {
        super(row, column);
        this.upgrade = upgrade;
        isMovable = true;
    }

    public Upgrade getUpgrade() {
        return upgrade;
    }
}
