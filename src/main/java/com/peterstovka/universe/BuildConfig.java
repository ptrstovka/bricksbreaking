package com.peterstovka.universe;

public final class BuildConfig {
    public static final String VERSION = "1.0-SNAPSHOT";
    public static final String NAME = "bricksbreaking";
    public static final String POSTGRE_DB_USER = "root";
    public static final String POSTGRE_DB_PASS = "secret";
    public static final String POSTGRE_DB_URL = "jdbc:postgresql://localhost:15432/root";
    public static final String POSTGRE_TEST_DB_USER = "root";
    public static final String POSTGRE_TEST_DB_PASS = "secret";
    public static final String POSTGRE_TEST_DB_URL = "jdbc:postgresql://localhost:15432/root";

    private BuildConfig() {
    }
}
