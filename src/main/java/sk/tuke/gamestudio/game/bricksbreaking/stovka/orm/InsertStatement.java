package sk.tuke.gamestudio.game.bricksbreaking.stovka.orm;

import sk.tuke.gamestudio.game.bricksbreaking.stovka.foundation.Tripple;

import java.lang.reflect.Field;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static sk.tuke.gamestudio.game.bricksbreaking.stovka.foundation.Lists.times;
import static sk.tuke.gamestudio.game.bricksbreaking.stovka.foundation.Strings.f;

public class InsertStatement extends ObjectStatement {

    private String statement;
    private List<Tripple<Integer, Object, Class>> parameters;

    InsertStatement(Object object) {
        super(object);
    }

    @Override
    public void prepare() {
        String clsName = object.getClass().getSimpleName();

        String tableName = clsName.toLowerCase();

        List<String> fields = new ArrayList<>();

        parameters = new ArrayList<>();
        Field[] declaredFields = object.getClass().getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            String name = field.getName();

            Object value = ReflectionHelpers.getValue(field, object);
            Class returnType = ReflectionHelpers.getReturnType(field, object);

            if ("table".equals(name)) {
                if (value instanceof String) {
                    tableName = (String) value;
                }
            }

            parameters.add(new Tripple<>(i, value, returnType));
            fields.add(field.getName());
        }

        String databaseFields = fields.stream().map(field -> field.toLowerCase().trim())
                .collect(Collectors.joining(", "))
                .trim();

        String valuesFields = times("?", fields.size())
                .stream()
                .collect(Collectors.joining(", "))
                .trim();

        statement = f("INSERT INTO %s (%s) VALUES (%s)", tableName, databaseFields, valuesFields);
    }

    public String getStatement() {
        if (statement == null) {
            throw new DatabaseException("The statement is not prepared.");
        }
        return statement;
    }

    public List<Tripple<Integer, Object, Class>> getParameters() {
        if (parameters == null) {
            throw new DateTimeException("The statement is not prepared.");
        }

        return parameters;
    }

    // Debug method.
    @SuppressWarnings("unused")
    public InsertStatement dump() {
        System.out.println(statement);
        parameters.forEach(it -> {
            System.out.println(it.getFirst());
            System.out.println(it.getSecond());
            System.out.println(it.getThird());
        });

        return this;
    }
}
