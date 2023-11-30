package Game;

import java.io.*;
import java.util.ArrayList;

public class ScoreController {
    private final String fileName = "scores.bin";
    private ScoreModel model;
    private ScoreView view;
    public ScoreController(ScoreModel model, ScoreView view) {
        this.model = model;
        this.view =view;
        load();
    }

    public void showScore(){
        view.showScore();
    }
    private void load() {
        try
        {
            File file = new File(fileName);
            file.createNewFile();
            FileInputStream fis = new FileInputStream(file);
            if(fis.available()<=0){
                fis.close();
                return;
            }
            ObjectInputStream ois = new ObjectInputStream(fis);

            model.setScores( (ArrayList) ois.readObject());
            ois.close();
            fis.close();
        }
        catch (IOException | ClassNotFoundException ioe)
        {
            ioe.printStackTrace();
        }
        view.updateScores();
    }


    public void save(String userName, int score) {
        try
        {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            model.addScore(new Score(userName, score));
            model.sortScores();
            oos.writeObject(model.getScores());
            oos.close();
            fos.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        view.updateScores();
    }
}
