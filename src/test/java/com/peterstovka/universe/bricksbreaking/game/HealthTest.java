package com.peterstovka.universe.bricksbreaking.game;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HealthTest {

    @Test
    public void should_not_remove_health() {
        Health health = new Health();

        assertEquals(5, health.getHealth());

        assertTrue(health.isAlive());

        health.record(10);

        assertEquals(5, health.getHealth());

        assertTrue(health.isAlive());
    }

    @Test
    public void should_remove_health() {
        Health health = new Health();

        health.record(1);

        assertEquals(4, health.getHealth());
    }

    @Test
    public void should_not_be_alive() {
        Health health = new Health();

        assertTrue(health.isAlive());
        health.record(1);
        assertTrue(health.isAlive());
        health.record(1);
        assertTrue(health.isAlive());
        health.record(1);
        assertTrue(health.isAlive());
        health.record(1);
        assertTrue(health.isAlive());
        health.record(1);
        assertFalse(health.isAlive());
    }
}
