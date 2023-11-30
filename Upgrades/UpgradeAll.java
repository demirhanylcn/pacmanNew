package Upgrades;


import Characters.Pacman;
import Game.GameModel;

public class UpgradeAll extends Upgrade {
    public UpgradeAll(){
        duration = 5000;
        image = "src/resources/avacadoFruit.png";
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