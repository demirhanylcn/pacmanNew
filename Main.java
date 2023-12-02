import Game.*;

public class Main {
    public static void main(String[] args) {


        ScoreModel scoreModel = new ScoreModel();
        ScoreView scoreView = new ScoreView(scoreModel);
        ScoreController scoreController = new ScoreController(scoreModel, scoreView);

        GameModel gameModel = new GameModel();
        GameView gameView = new GameView(gameModel);
        GameController gameController = new GameController(gameView, gameModel, scoreController);
        gameView.setController(gameController);

        MenuView menuView = new MenuView(gameController, scoreController);
        MenuController menuController = new MenuController(menuView);

        menuController.showMenu();






        }
    }
