package com.peterstovka.universe.bricksbreaking.orm;

import com.peterstovka.universe.bricksbreaking.foundation.Strings;
import com.peterstovka.universe.bricksbreaking.foundation.Tripple;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.peterstovka.universe.bricksbreaking.foundation.Strings.f;

public class ReflectionHelpers {

    public static List<String> getFields(Class cls) {
        List<String> fields = new ArrayList<>();
        for (Field field : cls.getDeclaredFields()) {
            fields.add(field.getName().toLowerCase());
        }
        return fields;
    }

    public static String tableName(Class cls) {
        return  cls.getSimpleName().toLowerCase();
    }

    public static void bindParameters(PreparedStatement statement, List<Tripple<Integer, Object, Class>> params) {
        for (Tripple<Integer, Object, Class> tripple: params) {
            Integer index = tripple.getFirst();
            Object val = tripple.getSecond();
            Class cls = tripple.getThird();

            String setter = createPreparedStatementSetter(cls);

            if (val instanceof Date) {
                val = new java.sql.Date(((Date) val).getTime());
                cls = java.sql.Date.class;
            }

            if (val instanceof Integer) {
                cls = int.class;
            }

            try {
                Method method = statement.getClass().getMethod(setter, int.class, cls);
                method.setAccessible(true);
                method.invoke(statement, index + 1, val);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                throw new DatabaseException(Strings.f("The [%s] data type is not supported.", cls.getName()));
            }
        }
    }

    private static String createPreparedStatementSetter(Class cls) {
        String name = cls.getSimpleName();
        if ("Integer".equals(name)) {
            name = "Int";
        }

        name = Strings.firstUpperCase(name);

        return Strings.f("set%s", name);
    }

    private static String createResultSetGetter(Class cls) {
        String name = cls.getSimpleName();

        if ("Integer".equals(name)) {
            name = "Int";
        }

        if ("Date".equals(name)) {
            name = "Timestamp";
        }

        name = Strings.firstUpperCase(name);

        return Strings.f("get%s", name);
    }

    public static Object getValue(ResultSet set, int index, Class cls) {
        try {
            Method method = set.getClass().getMethod(createResultSetGetter(cls), int.class);
            return method.invoke(set, index);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object getValue(Field field, Object object) {
        String name = field.getName();
        String getter = f("get%s", Strings.firstUpperCase(name));
        try {
            Method method = object.getClass().getMethod(getter);
            return method.invoke(object);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignore) {
            return null;
        }
    }

    public static Class getReturnType(Field field, Object object) {
        String name = field.getName();
        String getter = f("get%s", Strings.firstUpperCase(name));
        try {
            return object.getClass().getMethod(getter).getReturnType();
        } catch (NoSuchMethodException ignore) {
            return null;
        }
    }

}
