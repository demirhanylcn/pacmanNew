package Inputs;

public enum Inputs {
    UP,
    RIGHT,
    LEFT,
    DOWN;

    public Inputs opposite() {
        switch (this) {
            case UP -> {
                return DOWN;
            }
            case DOWN -> {
                return UP;
            }
            case LEFT -> {
                return RIGHT;
            }
            case RIGHT -> {
                return LEFT;
            }
        }
        return null;
    }
}
