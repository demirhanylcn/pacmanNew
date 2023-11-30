package Upgrades;

import Game.GameModel;

public abstract class Upgrade {
    protected int duration;
    protected long lastTimeMillis;
    protected String image;

    public abstract void start(GameModel model);
    public abstract void reset(GameModel model);
    public int getDuration() {
        return duration;
    }
    public String getImage() {
        return image;
    }
    public long getLastTimeMillis() {
        return lastTimeMillis;
    }

    public void setLastTimeMillis(long lastTimeMillis) {
        this.lastTimeMillis = lastTimeMillis;
    }
}
