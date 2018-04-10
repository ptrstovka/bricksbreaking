package sk.tuke.gamestudio.game.bricksbreaking.stovka.orm;

public abstract class ObjectStatement {

    Object object;

    ObjectStatement(Object object) {
        this.object = object;
    }

    public abstract void prepare();

}
