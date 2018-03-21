package com.peterstovka.universe.bricksbreaking.foundation;

public class Tripple<A, B, C> {

    private A first;
    private B second;
    private C third;

    public Tripple(A first, B second, C third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }

    public C getThird() {
        return third;
    }

}
