package Upgrades;

import Characters.Pacman;
import Game.GameModel;
import Upgrades.Upgrade;

public class UpgradeSpeed extends Upgrade {
    public UpgradeSpeed(){
        duration = 3000;
        image = "src/resources/cherry.png";
    }
    @Override
    public void start(GameModel model) {
        Pacman pacman = model.getPacman();
        pacman.setSpeed(pacman.getSpeed() / 2);
    }

    @Override
    public void reset(GameModel model) {
        Pacman pacman = model.getPacman();
        pacman.setSpeed(pacman.getSpeed() * 2);
    }
}
