package Game;

import java.io.Serializable;

public class Score implements Serializable {
    String userName;
    int score;

    public Score(String userName, int score) {
        this.userName = userName;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return userName + " " + score;
    }
}
