package sk.tuke.gamestudio.game.bricksbreaking.stovka.game;

public class Player {

    private static Player instance;

    private Player() {
        // no -op
    }

    public static Player instance() {
        if (instance == null) {
            instance = new Player();
        }

        return instance;
    }

    private String name = "player";

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
