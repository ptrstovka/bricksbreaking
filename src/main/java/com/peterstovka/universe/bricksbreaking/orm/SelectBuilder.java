package com.peterstovka.universe.bricksbreaking.orm;

import com.peterstovka.universe.bricksbreaking.foundation.Strings;
import com.peterstovka.universe.bricksbreaking.foundation.Tripple;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.peterstovka.universe.bricksbreaking.foundation.Strings.f;

public class SelectBuilder {

    private Database database;
    private Class cls;

    private final List<Where> wheres = new ArrayList<>();
    private final List<Sort> sorts = new ArrayList<>();
    private Integer limit = null;

    private List<Tripple<Integer, Object, Class>> params = new ArrayList<>();
    private String select;


    SelectBuilder(Database database, Class cls) {
        this.database = database;
        this.cls = cls;
    }

    public SelectBuilder where(String key, String opeartor, Object value) {
        wheres.add(new Where(key, opeartor, value));
        return this;
    }

    public SelectBuilder sort(String by, String as) {
        sorts.add(new Sort(by, as));
        return this;
    }

    public SelectBuilder limit(int limit) {
        this.limit = limit;

        return this;
    }

    private void prepareStatement() {
        List<String> fields = ReflectionHelpers.getFields(cls);
        String table = ReflectionHelpers.tableName(cls);

        String fieldNames = fields.stream().collect(Collectors.joining(", ")).trim();
        String baseSelect = f("SELECT %s FROM %s", fieldNames, table);


        if (! wheres.isEmpty()) {
            baseSelect = f("%s %s", baseSelect, buildWheres());
        }


        if (! sorts.isEmpty()) {
            baseSelect = f("%s %s", baseSelect, buildSorts());
        }

        if (limit != null) {
            baseSelect = f("%s LIMIT %d", baseSelect, limit);
        }

        this.select = baseSelect;
    }

    private String buildSorts() {
        String sortBy = sorts.stream()
                .map(sort -> Strings.f("%s %s", sort.by, sort.as))
                .collect(Collectors.joining(", "))
                .trim();

        return Strings.f("ORDER BY %s", sortBy);
    }

    private String buildWheres() {

        Where first = wheres.get(0);

        String baseWhere = Strings.f("WHERE %s %s ?", first.key, first.operator);
        params.add(new Tripple<>(0, first.value, first.value.getClass()));

        if (wheres.size() > 1) {
            List<Where> ands = wheres.subList(1, wheres.size());
            List<String> andStrings = new ArrayList<>();
            for (int i = 0; i < ands.size(); i++) {
                Where and = ands.get(i);
                andStrings.add(Strings.f("AND %s %s ?", and.key, and.operator));
                params.add(new Tripple<>(i + 1, and.value, and.value.getClass()));
            }

            String andsCollected = andStrings.stream().collect(Collectors.joining(" ")).trim();
            baseWhere = Strings.f("%s %s", baseWhere, andsCollected);
        }

        return baseWhere;
    }

    public <R> R first(Class<R> cls) {
        Object first = this.first();

        if (first == null) {
            return null;
        }

        return cls.cast(first);
    }


    public Object first() {
        List<Object> result = get();
        if (result.isEmpty()) {
            return null;
        }

        return result.get(0);
    }

    public <R> List<R> get(Class<R> cls) {
        return get().stream()
                .map(cls::cast)
                .collect(Collectors.toList());
    }

    // Debug method.
    @SuppressWarnings("unused")
    public SelectBuilder dump() {
        System.out.println(select);
        params.forEach(it -> {
            System.out.println(it.getFirst());
            System.out.println(it.getSecond());
            System.out.println(it.getThird());
        });

        return this;
    }

    public List<Object> get() {
        prepareStatement();

        PreparedStatement statement = database.prepare(select);

        ReflectionHelpers.bindParameters(statement, params);

        List<Object> results = new ArrayList<>();

        try {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                results.add(buildObject(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(f("Could not execute statement [%s]", select));
        }

        return results;
    }

    private Object buildObject(ResultSet resultSet) {
        Constructor constructor = cls.getConstructors()[0];

        Object[] params = new Object[constructor.getParameterTypes().length];
        Class[] parameterTypes = constructor.getParameterTypes();
        for (int i = 0; i < parameterTypes.length; i++) {
            Class aClass = parameterTypes[i];
            Object val = ReflectionHelpers.getValue(resultSet, i + 1, aClass);
            params[i] = val;
        }

        try {
            return constructor.newInstance(params);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    class Where {

        private String key;
        private String operator;
        private Object value;

        Where(String key, String operator, Object value) {
            this.key = key;
            this.operator = operator;
            this.value = value;
        }
    }

    class Sort {

        private String by;
        private String as;

        Sort(String by, String as) {
            this.by = by;
            this.as = as;
        }
    }

}
