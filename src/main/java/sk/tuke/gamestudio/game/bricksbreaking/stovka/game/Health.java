package sk.tuke.gamestudio.game.bricksbreaking.stovka.game;

public class Health {

    private int health = 5;

    public void record(int removedTiles) {
        if (removedTiles == 1) {
            health--;
        }
    }

    public boolean isAlive() {
        return health > 0;
    }

    public int getHealth() {
        return health;
    }
}
