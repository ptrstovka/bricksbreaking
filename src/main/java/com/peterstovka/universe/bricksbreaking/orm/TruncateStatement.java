package com.peterstovka.universe.bricksbreaking.orm;

import com.peterstovka.universe.bricksbreaking.foundation.Strings;

import java.util.List;
import java.util.stream.Collectors;

public class TruncateStatement {

    private List<String> tables;

    private String query;

    public TruncateStatement(List<String> tables) {
        this.tables = tables;
    }

    public void prepare() {
        String truncatedTables = tables.stream().collect(Collectors.joining(", ")).trim();
        query = Strings.f("TRUNCATE %s", truncatedTables);
    }

    public String getQuery() {
        return query;
    }

}
