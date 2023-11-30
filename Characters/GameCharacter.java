package Characters;

import Game.Animated;
import Game.GameModel;
import Inputs.Inputs;

import java.util.*;

public abstract class GameCharacter {
    protected long lastMillisecondsUpdate;
    private final Random random;
    protected final Map<Inputs, String[]> animation = new HashMap<>();

    public GameCharacter(int row, int column, int speed) {
        inputs = Inputs.DOWN;
        this.row = row;
        this.speed = speed;
        this.column = column;
        this.random = new Random();
    }

    protected Animated animated;
    protected Inputs inputs;
    protected int row;
    protected int column;
    protected int speed;

    public void update(GameModel model) {
        lastMillisecondsUpdate = System.currentTimeMillis();
        int newRow = getRow();
        int newColumn = getColumn();

        switch (getInputs()) {
            case Inputs.UP -> newRow--;
            case Inputs.DOWN -> newRow++;
            case Inputs.LEFT -> newColumn--;
            case Inputs.RIGHT -> newColumn++;
        }
        if (newColumn == -1) {
            this.column = model.getBoard().length - 1;
            return;
        }
        if (newColumn == model.getBoard().length) {
            this.column = 0;
            return;
        }
        if (model.getBoard()[newRow][newColumn].isMovable()) {
            this.row = newRow;
            this.column = newColumn;
            setDirection(getRandomDirectionOpposite(model));

        } else if (!(model.getBoard()[newRow][newColumn].isMovable())) {
            setDirection(getRandomDirectionOpposite(model));
        }
    }

    private Inputs getRandomDirectionOpposite(GameModel model) {
        List<Inputs> inputsList = new ArrayList<>(Arrays.stream(Inputs.values()).filter(x -> x.opposite() != getInputs()).toList());
        if (!(model.getBoard()[row][column - 1].isMovable())) {
            inputsList.remove(Inputs.LEFT);
        }
        if (!(model.getBoard()[row][column + 1].isMovable())) {
            inputsList.remove(Inputs.RIGHT);
        }
        if (!(model.getBoard()[row - 1][column].isMovable())) {
            inputsList.remove(Inputs.UP);
        }
        if (!(model.getBoard()[row + 1][column].isMovable())) {
            inputsList.remove(Inputs.DOWN);
        }
        if (inputsList.isEmpty()) {
            return inputs.opposite();
        }
        return inputsList.get(random.nextInt(inputsList.size()));
    }

    public Animated getAnimated() {
        return animated;
    }

    public Inputs getInputs() {
        return inputs;
    }

    public void setDirection(Inputs inputs) {
        this.inputs = inputs;
        animated.setFrame(animation.get(inputs));
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

    public int getSpeed() {
        return speed;
    }

    public long getLastMillisecondsUpdate() {
        return lastMillisecondsUpdate;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
