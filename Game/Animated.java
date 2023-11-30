package Game;

public class Animated {
    public String[] frames;

    public double duration;

    public Animated(String[] frames, double duration) {
        this.frames = frames;
        this.duration = duration;
    }

    public String getFrame(double time)
    {
        int index = (int)((time % (frames.length * duration)) / duration);
        return frames[index];
    }

    public void setFrame(String[] frames){
        this.frames = frames;
    }

}