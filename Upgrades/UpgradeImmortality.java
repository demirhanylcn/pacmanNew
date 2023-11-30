package Upgrades;

import Game.GameModel;
import Upgrades.Upgrade;

public class UpgradeImmortality extends Upgrade {
    public UpgradeImmortality(){
        duration = 3000;
        image = "src/resources/watermelonFruit.png";
    }
    @Override
    public void start(GameModel model) {

    }

    @Override
    public void reset(GameModel model) {

    }
}
