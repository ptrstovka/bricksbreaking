package sk.tuke.gamestudio.game.bricksbreaking.stovka.game;

public class Score {

    private int score;

    public void record(int removedTiles) {
        if (removedTiles > 1) {
            score += (removedTiles * 15);
        }
    }

    public int getScore() {
        return score;
    }

    public void recordRound() {
        score += 25000;
    }

}
