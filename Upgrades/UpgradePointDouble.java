package Upgrades;

import Game.GameModel;
import Upgrades.Upgrade;

public class UpgradePointDouble extends Upgrade {
    public UpgradePointDouble() {
        duration = 5000;
        image = "src/resources/strawberryFruit.png";
    }

    @Override
    public void start(GameModel model) {

    }

    @Override
    public void reset(GameModel model) {

    }
}
