package com.peterstovka.universe.bricksbreaking.game;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ScoreTest {

    @Test
    public void should_add_score() {
        Score score = new Score();

        assertEquals(0, score.getScore());

        score.record(10);

        assertEquals(150, score.getScore());

        score.record(25);

        assertEquals(525, score.getScore());

        score.recordRound();

        assertEquals(25525, score.getScore());
    }
}
