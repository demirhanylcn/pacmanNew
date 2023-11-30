package Characters;

import Game.Animated;
import Inputs.Inputs;

public class PinkGhost extends GameCharacter {
    public PinkGhost(int row, int column, int speed) {
        super(row, column, speed);
        animation.put(Inputs.UP, new String[]{ "src/resources/pinkGhostUp.png"});
        animation.put(Inputs.DOWN, new String[]{ "src/resources/pinkGhostDown.png" });
        animation.put(Inputs.RIGHT, new String[]{ "src/resources/pinkGhostRight.png" });
        animation.put(Inputs.LEFT, new String[]{ "src/resources/pinkGhostLeft.png" });
        animated = new Animated(animation.get(inputs), 0.25);
    }

}
