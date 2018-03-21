package com.peterstovka.universe.bricksbreaking.orm;

import com.peterstovka.universe.bricksbreaking.foundation.Strings;
import com.peterstovka.universe.bricksbreaking.foundation.Tripple;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.peterstovka.universe.bricksbreaking.foundation.Strings.f;

public class UpdateStatement extends ObjectStatement {

    private List<String> keys;

    private List<Tripple<Integer, Object, Class>> parameters = new ArrayList<>();
    private String update;

    UpdateStatement(Object object) {
        super(object);
    }

    public void setKeys(List<String> list) {
        this.keys = list;
    }

    public List<Tripple<Integer, Object, Class>> getParameters() {
        return parameters;
    }

    public String getUpdate() {
        return update;
    }

    @Override
    public void prepare() {

        Class cls = object.getClass();

        Field[] declaredFields = Arrays.stream(object.getClass().getDeclaredFields())
                .filter(it -> ! this.keys.contains(it.getName()))
                .toArray(Field[]::new);

        List<String> fieldNames = new ArrayList<>();

        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            fieldNames.add(field.getName().toLowerCase());
            Object value = ReflectionHelpers.getValue(field, object);
            Class returnType = ReflectionHelpers.getReturnType(field, object);
            parameters.add(new Tripple<>(i, value, returnType));
        }

        String table = ReflectionHelpers.tableName(cls);

        String updateValues = fieldNames
                .stream()
                .map(it -> f("%s = ?", it))
                .collect(Collectors.joining(", "))
                .trim();

        String update = f("UPDATE %s SET %s", table, updateValues);

        List<String> wheres = new ArrayList<>(this.keys);

        String where = buildWheres(wheres);

        this.update = f("%s %s", update, where);
    }

    private Field getField(String field) {
        return Arrays.stream(object.getClass().getDeclaredFields())
                .filter(it -> it.getName().toLowerCase().equals(field))
                .findFirst()
                .orElse(null);
    }

    private String buildWheres(List<String> wheres) {

        String first = wheres.get(0);

        int lastIndex = this.parameters.size();
        String baseWhere = Strings.f("WHERE %s = ?", first);
        Field field = getField(first);
        Object value = ReflectionHelpers.getValue(field, object);
        Class returnType = ReflectionHelpers.getReturnType(field, object);
        parameters.add(new Tripple<>(lastIndex, value, returnType));

        if (wheres.size() > 1) {
            List<String> ands = wheres.subList(1, wheres.size());
            List<String> andStrings = new ArrayList<>();
            for (int i = 0; i < ands.size(); i++) {
                String and = ands.get(i);
                andStrings.add(Strings.f("AND %s = ?", and));
                Field f = getField(and);
                Object v = ReflectionHelpers.getValue(f, object);
                Class r = ReflectionHelpers.getReturnType(f, object);
                parameters.add(new Tripple<>(lastIndex + i + 1, v, r));
            }

            String andsCollected = andStrings.stream().collect(Collectors.joining(" ")).trim();
            baseWhere = Strings.f("%s %s", baseWhere, andsCollected);
        }

        return baseWhere;
    }

}
