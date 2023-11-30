package Game;

import Cells.Cell;
import Cells.CellEmpty;
import Cells.CellPoint;
import Cells.CellUpgrade;
import Characters.GameCharacter;
import Characters.Pacman;
import Inputs.Inputs;
import Upgrades.*;

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
    public void showGame(){
        model.initGame();
        model.setActive(true);
        view.showGame();
        Thread th = new Thread(() -> {
            while (model.isActive()) {
                try {
                    for (GameCharacter gameCharacter: model.getPlayers()) {
                        long currentTime = System.currentTimeMillis();
                        if(currentTime - gameCharacter.getLastMillisecondsUpdate() <= gameCharacter.getSpeed()) continue;
                        gameCharacter.update(model);
                        Cell cell = model.getBoard()[gameCharacter.getRow()][gameCharacter.getColumn()];
                        if(gameCharacter instanceof Pacman && cell  instanceof CellPoint){
                            Cell empty = new CellEmpty(gameCharacter.getRow(),gameCharacter.getColumn());
                            view.setCellValue(empty, gameCharacter.getRow(), gameCharacter.getColumn());
                            model.board.cells[gameCharacter.getRow()][gameCharacter.getColumn()] = empty;
                            addScore(100);
                            view.updateScore();
                        }

                        if(gameCharacter instanceof Pacman && cell instanceof CellUpgrade upgrageCell){
                            Cell empty = new CellEmpty(gameCharacter.getRow(),gameCharacter.getColumn());
                            view.setCellValue(empty, gameCharacter.getRow(), gameCharacter.getColumn());
                            model.board.cells[gameCharacter.getRow()][gameCharacter.getColumn()] = empty;
                            model.getUpgrades().add(upgrageCell.getUpgrade());
                            upgrageCell.getUpgrade().setLastTimeMillis(System.currentTimeMillis());
                            upgrageCell.getUpgrade().start(model);
                            view.updateBottomPanel();
                        }
                    }
                    if(gameEnd()){
                        stopGame();
                    }
                    if(model.getPlayerAroundPacman() && model.getUpgrades().stream().noneMatch(x-> x instanceof UpgradeImmortality || x instanceof UpgradeAll)){
                        model.setHealth(model.getHealth()-1);
                        model.resetPlayer();
                        view.updateBottomPanel();
                    }
                    if(model.getHealth()<=0){
                        stopGame();
                    }
                }
                catch (Exception e){
                    //
                }
            }
        });
        gameThreads.add(th);
        th.start();
        Thread updateTable = new Thread(() -> {
            while (model.isActive()) {
                try {
                    view.repaintBoard();
                    Thread.sleep(1000 / 60);
                } catch (InterruptedException e) {
                    //
                }
            }
        });
        updateTable.start();
        gameThreads.add(updateTable);

        Thread spawnUpgrades = new Thread(() -> {
            Random random = new Random();
            while (model.isActive()) {
                try {
                    Thread.sleep(3000);
                    int n = random.nextInt(100);
                    if(n<=25) continue;
                    int[] upgradesIntArr = new int[]{ 1, 2,3,4,5};
                    int randUpgrade = upgradesIntArr[random.nextInt(upgradesIntArr.length)];
                    Upgrade upgrade;
                    if(randUpgrade == 1){
                        upgrade = new UpgradeSpeed();
                    }else if(randUpgrade == 2){
                        upgrade = new UpgradeImmortality();
                    }
                    else if(randUpgrade == 3){
                        upgrade = new UpgradePointDouble();
                    }
                    else if (randUpgrade == 4) {
                        upgrade = new UpgradePointTriple();
                    } else upgrade = new UpgradeAll();
                    List<GameCharacter> characters = model.getPlayers().stream().filter(player -> !(player instanceof Pacman)).toList();
                    GameCharacter character = characters.get(random.nextInt(characters.size()));
                    CellUpgrade cellUpgrade = new CellUpgrade(character.getRow(), character.getColumn(), upgrade);
                    view.setCellValue(cellUpgrade, character.getRow(), character.getColumn());
                    model.board.cells[character.getRow()][character.getColumn()] = cellUpgrade;
                } catch (Exception e) {
                    //
                }
            }
        });
        spawnUpgrades.start();
        gameThreads.add(spawnUpgrades);


        Thread calculatorUpgrades = new Thread(()->{
            while (model.isActive()) {
                List<Upgrade> toRemove = new ArrayList<>();
                for (Upgrade upgrade : model.getUpgrades()) {
                    long currentTime = System.currentTimeMillis();
                    if (currentTime - upgrade.getLastTimeMillis() <= upgrade.getDuration()) continue;
                    upgrade.reset(model);
                    toRemove.add(upgrade);
                }
                boolean updateBottomPanel = !toRemove.isEmpty();
                model.getUpgrades().removeAll(toRemove);
                if (updateBottomPanel) {
                    view.updateBottomPanel();
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    //
                }

            }
        });
        calculatorUpgrades.start();
        gameThreads.add(calculatorUpgrades);

        for (Cell[] cells: model.board.getCells()) {
            for (Cell cell: cells) {
                view.setCellValue(cell, cell.getRow(), cell.getColumn());
            }
        }
    }
    public void setPacmanDirection(Inputs inputs){
        Pacman pacman = model.getPacman();
        pacman.setDirection(inputs);
    }
    public void stopGame(){
        String name = view.askName();
        scoreController.save(name, model.getScore());
        view.setVisible(false);
        gameThreads.forEach(Thread::interrupt);
        gameThreads.clear();
        model.initGame();
    }
    public void addScore(int score){
        if(model.getUpgrades().stream().anyMatch(upgrade -> upgrade instanceof UpgradePointDouble)){
            model.setScore(model.getScore()+score*2);
        }else if (model.getUpgrades().stream().anyMatch(upgrade -> upgrade instanceof UpgradePointTriple)){
            model.setScore(model.getScore()+score*3);
        } else model.setScore(model.getScore()+score);
    }
    public boolean gameEnd(){
        for(int i = 0; i < model.getBoard().length; i++){
            for(int j = 0; j < model.getBoard()[i].length; j++){
                if(model.getBoard()[i][j] instanceof CellPoint){
                    return false;
                }
            }
        }
        return true;
    }
}
