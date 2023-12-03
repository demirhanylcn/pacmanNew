package Game;

import Cells.Cell;
import Characters.*;
import Upgrades.Upgrade;

import java.util.ArrayList;
import java.util.List;

public class GameModel {


    private ArrayList<GameCharacter> playerList = new ArrayList<>();
    public GameTableModel gameBoard;
    private List<Upgrade> upgradeList;
    private boolean isGameActive;
    private int currentScore;
    private int currentHealth;

    public GameModel() {
        initializeGame();
    }


    private final char[][] gameFieldArr = {
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

    public void initializeGame() {
        currentScore = 0;
        currentHealth = 3;
        upgradeList = new ArrayList<>();
        gameBoard = new GameTableModel(gameFieldArr);
        resetCharacters();
        isGameActive = false;
    }

    public List<GameCharacter> getPlayerList() {
        return playerList;
    }

    public Pacman getPacman() {
        return playerList.stream()
                .filter(player -> player instanceof Pacman)
                .map(player -> (Pacman) player)
                .findFirst()
                .orElse(null);
    }

    public Cell[][] getGameBoard() {
        return gameBoard.getCells();
    }




    public boolean getIfGhostTouchedPacman() {
        Pacman pacman = getPacman();

        if (pacman != null) {
            int row = pacman.getRow();
            int column = pacman.getColumn();

            for (GameCharacter character : playerList) {
                if (!(character instanceof Pacman) && character.getColumn() == column && character.getRow() == row) return true;
            }
        }
        return false;

    }


    public boolean isGameActive() {
        return isGameActive;
    }

    public void setGameActive(boolean gameActive) {
        this.isGameActive = gameActive;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public void resetCharacters() {
        playerList.clear();
        upgradeList.clear();
        playerList.add(new Pacman(1, 1, 300));
        playerList.add(new BlueGhost(6, 6, 300));
        playerList.add(new RedGhost(6, 12, 300));
        playerList.add(new PinkGhost(10, 6, 300));
        playerList.add(new OrangeGhost(10, 12, 300));


    }

    public List<Upgrade> getUpgradeList() {
        return upgradeList;
    }


}