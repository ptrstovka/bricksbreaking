package com.peterstovka.universe.bricksbreaking.game;

public interface GameListener {

    void onRoundEnded();

    void onTilesRemoved(int count);

}
