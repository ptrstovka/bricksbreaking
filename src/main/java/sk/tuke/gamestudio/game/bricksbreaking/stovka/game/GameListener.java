package sk.tuke.gamestudio.game.bricksbreaking.stovka.game;

public interface GameListener {

    void onRoundEnded();

    void onTilesRemoved(int count);

}
