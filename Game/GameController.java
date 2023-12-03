package Game;

import Cells.Cell;
import Cells.CellEmpty;
import Cells.CellPoint;
import Cells.CellUpgrade;
import Characters.GameCharacter;
import Characters.Pacman;
import Inputs.Inputs;
import Upgrades.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameController {
    private GameView view;
    private GameModel model;
    private List<Thread> gameThreads = new ArrayList<>();
    private ScoreController scoreController;

    public GameController(GameView view, GameModel model, ScoreController scoreController) {
        this.scoreController = scoreController;
        this.view = view;
        this.model = model;
    }

    public void showGame() {

        model.initializeGame();
        model.setGameActive(true);
        view.showGame();

        Thread gameThread = new Thread(this::game);
        gameThreads.add(gameThread);
        gameThread.start();


        Thread updateTableThread = new Thread(this::updateTable);
        updateTableThread.start();
        gameThreads.add(updateTableThread);


        Thread spawnUpgrades = new Thread(this::spawnUpgrades);
        spawnUpgrades.start();
        gameThreads.add(spawnUpgrades);


        Thread calculatorUpgrades = new Thread(this::calculateUpgrades);
        calculatorUpgrades.start();
        gameThreads.add(calculatorUpgrades);

        for (Cell[] cells : model.gameBoard.getCells()) {
            for (Cell cell : cells) {
                view.setCellValue(cell, cell.getRow(), cell.getColumn());
            }
        }
    }

    private void game() {
        while (model.isGameActive()) {
            try {

                for (GameCharacter gameCharacter : model.getPlayerList()) {
                    long currentTime = System.currentTimeMillis();

                    if (currentTime - gameCharacter.getLastMillisecondsUpdate() <= gameCharacter.getSpeed()) {
                        continue;
                    }

                    gameCharacter.update(model);
                    Cell currentCell = model.getGameBoard()[gameCharacter.getRow()][gameCharacter.getColumn()];

                    if (gameCharacter instanceof Pacman && currentCell instanceof CellPoint) {
                        earnPoint(gameCharacter);
                    }

                    if (gameCharacter instanceof Pacman && currentCell instanceof CellUpgrade) {
                        addUpgrade(gameCharacter, (CellUpgrade) currentCell);
                    }
                }
                handlePlayerHealth();
                handleGameEnd();


            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    private void updateCellAndModel(int row, int column) {
        Cell emptyCell = new CellEmpty(row, column);
        view.setCellValue(emptyCell, row, column);
        model.gameBoard.cells[row][column] = emptyCell;
    }

    private void earnPoint(GameCharacter pacman) {
        updateCellAndModel(pacman.getRow(), pacman.getColumn());
        addScore(100);
        view.updateScore();
    }

    private void addUpgrade(GameCharacter pacman, CellUpgrade upgradeCell) {
        updateCellAndModel(pacman.getRow(), pacman.getColumn());

        model.getUpgradeList().add(upgradeCell.getUpgrade());
        upgradeCell.getUpgrade().setLastTimeMillis(System.currentTimeMillis());
        upgradeCell.getUpgrade().start(model);

        view.updateBottomPanel();
    }

    private void handleGameEnd() {
        if (gameEnd()) {
            stopGame();
        }
    }

    private void handlePlayerHealth() {
        if (model.getIfGhostTouchedPacman() && model.getUpgradeList().stream().noneMatch(x -> x instanceof UpgradeImmortality || x instanceof UpgradeAll)) {
            model.setCurrentHealth(model.getCurrentHealth() - 1);
            model.resetCharacters();
            view.updateBottomPanel();
            view.updateBottomPanel();


        }


        if (model.getCurrentHealth() <= 0) {
            stopGame();
        }
    }

    private void updateTable() {
        while (model.isGameActive()) {
            try {
                view.repaintBoard();
                Thread.sleep(15);
            } catch (InterruptedException e) {
                System.out.println(e);
                Thread.currentThread().interrupt();
            }
        }
    }

    private void calculateUpgrades() {
        while (model.isGameActive()) {
            try {
                List<Upgrade> toRemove = new ArrayList<>();

                for (Upgrade upgrade : model.getUpgradeList()) {
                    long currentTime = System.currentTimeMillis();

                    if (currentTime - upgrade.getLastTimeMillis() <= upgrade.getDuration()) continue;

                    upgrade.reset(model);
                    toRemove.add(upgrade);
                }

                boolean updateBottomPanel = !toRemove.isEmpty();
                model.getUpgradeList().removeAll(toRemove);

                if (updateBottomPanel) {
                    SwingUtilities.invokeLater(() -> view.updateBottomPanel());
                }

                Thread.sleep(100);

            } catch (InterruptedException e) {
                System.out.println(e);
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }


    private void spawnUpgrades() {
        Random randomGenerator = new Random();

        while (model.isGameActive()) {
            try {

                Thread.sleep(100);
                int randomNumber = randomGenerator.nextInt(100);
                if (randomNumber <= 25) {
                    int[] availableUpgrades = new int[]{1, 2, 3, 4, 5};
                    int selectedUpgradeIndex = randomGenerator.nextInt(availableUpgrades.length);
                    int selectedUpgradeId = availableUpgrades[selectedUpgradeIndex];

                    Upgrade selectedUpgrade;

                    switch (selectedUpgradeId) {
                        case 1:
                            selectedUpgrade = new UpgradeSpeed();
                            break;
                        case 2:
                            selectedUpgrade = new UpgradeImmortality();
                            break;
                        case 3:
                            selectedUpgrade = new UpgradePointDouble();
                            break;
                        case 4:
                            selectedUpgrade = new UpgradePointTriple();
                            break;
                        default:
                            selectedUpgrade = new UpgradeAll();
                            break;
                    }

                    List<GameCharacter> characters = model.getPlayerList().stream()
                            .filter(player -> !(player instanceof Pacman))
                            .toList();

                    GameCharacter selectedCharacter = characters.get(randomGenerator.nextInt(characters.size()));

                    CellUpgrade upgradeCell = new CellUpgrade(selectedCharacter.getRow(), selectedCharacter.getColumn(), selectedUpgrade);

                    view.setCellValue(upgradeCell, selectedCharacter.getRow(), selectedCharacter.getColumn());
                    model.gameBoard.cells[selectedCharacter.getRow()][selectedCharacter.getColumn()] = upgradeCell;


                }
            } catch (InterruptedException e) {
                System.out.println(e);
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void setPacmanDirection(Inputs inputs) {
        Pacman pacman = model.getPacman();
        pacman.setDirection(inputs);
    }

    public void stopGame() {
        String name = GameView.askName();
        scoreController.save(name, model.getCurrentScore());
        view.setVisible(false);
        gameThreads.forEach(Thread::interrupt);
        gameThreads.clear();
        model.initializeGame();
        view.updateBottomPanel();
    }

    public void addScore(int score) {
        if (model.getUpgradeList().stream().anyMatch(upgrade -> upgrade instanceof UpgradePointDouble)) {
            model.setCurrentScore(model.getCurrentScore() + score * 2);
        } else if (model.getUpgradeList().stream().anyMatch(upgrade -> upgrade instanceof UpgradePointTriple)) {
            model.setCurrentScore(model.getCurrentScore() + score * 3);
        } else model.setCurrentScore(model.getCurrentScore() + score);
    }

    public boolean gameEnd() {
        for (int i = 0; i < model.getGameBoard().length; i++) {
            for (int j = 0; j < model.getGameBoard()[i].length; j++) {
                if (model.getGameBoard()[i][j] instanceof CellPoint) {
                    return false;
                }
            }
        }
        return true;
    }
}
