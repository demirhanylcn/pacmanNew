package Game;

import java.util.ArrayList;
import java.util.List;

public class ScoreModel {
    private List<Score> scores;
    public ScoreModel(){
        scores = new ArrayList<>();
    }
    public List<Score> getScores() {
        return scores;
    }
    public void addScore(Score score){
        scores.add(score);
    }
    public void sortScores(){
        scores.sort((first, second) ->  second.getScore() - first.getScore());
    }
    public void setScores(ArrayList<Score> scores){
        this.scores = scores;
    }
}
