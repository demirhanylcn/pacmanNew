package Game;

public class MenuController {
    private final MenuView view;

    public MenuController( MenuView view) {
        this.view = view;
    }

    public void showMenu(){
        view.showMenu();
    }
}
