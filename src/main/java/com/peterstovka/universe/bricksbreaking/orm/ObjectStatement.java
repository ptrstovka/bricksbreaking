package com.peterstovka.universe.bricksbreaking.orm;

public abstract class ObjectStatement {

    Object object;

    ObjectStatement(Object object) {
        this.object = object;
    }

    public abstract void prepare();

}
