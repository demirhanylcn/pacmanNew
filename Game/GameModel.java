package Game;

import Cells.Cell;
import Characters.*;
import Upgrades.Upgrade;

import java.util.ArrayList;
import java.util.List;

public class GameModel {

    private ArrayList<GameCharacter> players = new ArrayList<>();
    public GameTableModel board;
    private List<Upgrade> upgrades;
    private boolean active;
    private int score;
    private int health;

    public GameModel() {
        initGame();
    }


    private final char[][] field = {
            {'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w'},
            {'s', 's', 's', 's', 's', 's', 's', 's', 's', 'w', 's', 's', 's', 's', 's', 's', 's', 's', 's'},
            {'w', 's', 'w', 'w', 's', 'w', 'w', 'w', 's', 'w', 's', 'w', 'w', 'w', 's', 'w', 'w', 's', 'w'},
            {'w', 's', 's', 's', 's', 's', 's', 's', 's', 's', 's', 's', 's', 's', 's', 's', 's', 's', 'w'},
            {'w', 's', 'w', 'w', 's', 'w', 's', 'w', 'w', 'w', 'w', 'w', 's', 'w', 's', 'w', 'w', 's', 'w'},
            {'w', 's', 's', 's', 's', 'w', 's', 's', 's', 'w', 's', 's', 's', 'w', 's', 's', 's', 's', 'w'},
            {'w', 'w', 'w', 'w', 's', 'w', 'w', 'w', 's', 'w', 's', 'w', 'w', 'w', 's', 'w', 'w', 'w', 'w'},
            {'w', 'w', 'w', 'w', 's', 'w', 's', 's', 's', 's', 's', 's', 's', 'w', 's', 'w', 'w', 'w', 'w'},
            {'w', 'w', 'w', 'w', 's', 'w', 's', 'w', 'w', 's', 'w', 'w', 's', 'w', 's', 'w', 'w', 'w', 'w'},
            {'s', 's', 's', 's', 's', 's', 's', 's', 's', 's', 's', 's', 's', 's', 's', 's', 's', 's', 's'},
            {'w', 'w', 'w', 'w', 's', 'w', 's', 'w', 'w', 's', 'w', 'w', 's', 'w', 's', 'w', 'w', 'w', 'w'},
            {'w', 'w', 'w', 'w', 's', 'w', 's', 's', 's', 's', 's', 's', 's', 'w', 's', 'w', 'w', 'w', 'w'},
            {'w', 'w', 'w', 'w', 's', 'w', 'w', 'w', 's', 'w', 's', 'w', 'w', 'w', 's', 'w', 'w', 'w', 'w'},
            {'w', 's', 's', 's', 's', 'w', 's', 's', 's', 'w', 's', 's', 's', 'w', 's', 's', 's', 's', 'w'},
            {'w', 's', 'w', 'w', 's', 'w', 's', 'w', 'w', 'w', 'w', 'w', 's', 'w', 's', 'w', 'w', 's', 'w'},
            {'w', 's', 's', 's', 's', 's', 's', 's', 's', 's', 's', 's', 's', 's', 's', 's', 's', 's', 'w'},
            {'w', 's', 'w', 'w', 'w', 'w', 'w', 'w', 's', 'w', 's', 'w', 'w', 'w', 'w', 'w', 'w', 's', 'w'},
            {'s', 's', 's', 's', 's', 's', 's', 's', 's', 'w', 's', 's', 's', 's', 's', 's', 's', 's', 's'},
            {'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w'},
    };

    public void initGame() {
        score = 0;
        health = 3;
        upgrades = new ArrayList<>();

        board = new GameTableModel(field);

        resetPlayer();
        active = false;
    }

    public List<GameCharacter> getPlayers() {
        return players;
    }

    public Pacman getPacman() {
        return players.stream()
                .filter(player -> player instanceof Pacman)
                .map(player -> (Pacman) player)
                .findFirst()
                .orElse(null);
    }

    public Cell[][] getBoard() {
        return board.getCells();
    }

    public boolean getIfGhostTouchedPacman() {
        Pacman pacman = getPacman();

        if (pacman != null) {
            int row = pacman.getRow();
            int column = pacman.getColumn();

            return players.stream()
                    .anyMatch(player -> !(player instanceof Pacman) && player.getRow() == row && player.getColumn() == column);
        }

        return false;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void resetPlayer() {
        players.clear();
        upgrades.clear();
        players.add(new Pacman(1, 1, 300));
        players.add(new BlueGhost(6, 6, 300));
        players.add(new RedGhost(6, 12, 300));
        players.add(new PinkGhost(10, 6, 300));
        players.add(new OrangeGhost(10, 12, 300));


    }

    public List<Upgrade> getUpgrades() {
        return upgrades;
    }


}


