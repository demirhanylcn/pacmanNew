package Characters;


import Game.Animated;
import Game.GameModel;
import Inputs.Inputs;

public class Pacman extends GameCharacter {
    public Pacman(int row, int column, int speed) {
        super(row, column, speed);
        animation.put(Inputs.UP, new String[]{ "src/resources/PacmanUp1.png","src/resources/PacmanUp2.png" ,"src/resources/PacmanUp3.png" });
        animation.put(Inputs.DOWN, new String[]{ "src/resources/PacmanDown1.png","src/resources/PacmanDown2.png" ,"src/resources/PacmanDown3.png" });
        animation.put(Inputs.RIGHT, new String[]{ "src/resources/PacmanRight1.png","src/resources/PacmanRight2.png" ,"src/resources/PacmanRight3.png" });
        animation.put(Inputs.LEFT, new String[]{ "src/resources/PacmanLeft1.png","src/resources/PacmanLeft2.png" ,"src/resources/PacmanLeft3.png" });
        animated = new Animated(animation.get(inputs), 0.25);
    }

    @Override
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
        if(newColumn==-1){
            this.column = model.getBoard().length-1;
            return;
        }
        if(newColumn==model.getBoard().length){
            this.column = 0;
            return;
        }
        if (model.getBoard()[newRow][newColumn].isMovable()) {
            setColumn(newColumn);
            setRow(newRow);
        }
    }


}
