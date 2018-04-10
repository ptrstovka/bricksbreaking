package sk.tuke.gamestudio.game.bricksbreaking.stovka.orm;

import com.peterstovka.universe.BuildConfig;

import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;

import static sk.tuke.gamestudio.game.bricksbreaking.stovka.foundation.Lists.collect;
import static sk.tuke.gamestudio.game.bricksbreaking.stovka.foundation.Strings.f;

public class Database {

    private static Database instance = null;
    private static Database testInstance = null;

    private String url;
    private String user;
    private String pass;

    private Database(String url, String user, String pass) {
        this.url = url;
        this.user = user;
        this.pass = pass;
    }

    private Connection connection;

    public static Database instance() {
        if (instance == null) {
            String url = BuildConfig.POSTGRE_DB_URL;
            String user = BuildConfig.POSTGRE_DB_USER;
            String pass = BuildConfig.POSTGRE_DB_PASS;
            instance = new Database(url, user, pass);
        }

        return instance;
    }

    public static Database test() {
        if (testInstance == null) {
            String url = BuildConfig.POSTGRE_TEST_DB_URL;
            String user = BuildConfig.POSTGRE_TEST_DB_USER;
            String pass = BuildConfig.POSTGRE_TEST_DB_PASS;
            testInstance = new Database(url, user, pass);
        }

        return testInstance;
    }

    public void open() {
        if (connection != null) {
            throw new DatabaseException("The database connection is already open.");
        }

        try {
            connection = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(f("We could not connect to the [%s] with user [%s] and password [%s].", url, user, pass));
        }
    }

    public void close() {
        if (connection == null) {
            throw new DatabaseException("The database connection is already closed.");
        }

        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            throw new DatabaseException("We could not close the connection.");
        }
    }

    private Connection getConnection() {
        if (connection == null) {
            throw new DatabaseException("The database connection must be open.");
        }

        return connection;
    }

    public void insert(Object model) {
        InsertStatement insertStatement = new InsertStatement(model);
        insertStatement.prepare();

        PreparedStatement preparedStatement;

        try {
            preparedStatement = getConnection().prepareStatement(insertStatement.getStatement());
        } catch (SQLException e) {
            throw new DatabaseException(f("Insert statement is not valid: [%s]", insertStatement.getStatement()));
        }

        ReflectionHelpers.bindParameters(preparedStatement, insertStatement.getParameters());

        try {
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("The INSERT statement could not be executed.");
        }
    }

    public SelectBuilder select(Class cls) {
        return new SelectBuilder(this, cls);
    }

    public void update(Object object, List<String> primaryKeys) {
        UpdateStatement statement = new UpdateStatement(object);
        statement.setKeys(primaryKeys);
        statement.prepare();

        PreparedStatement preparedStatement = prepare(statement.getUpdate());

        ReflectionHelpers.bindParameters(preparedStatement, statement.getParameters());

        try {
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("The UPDATE statement could not be executed.");
        }
    }

    public void truncate(Class... classes) {
        List<String> tables = collect(classes)
                .stream()
                .map(ReflectionHelpers::tableName)
                .collect(Collectors.toList());

        truncate(tables);
    }

    public void truncate(String... tables) {
        truncate(collect(tables));
    }

    private void truncate(List<String> tables) {
        TruncateStatement statement = new TruncateStatement(tables);
        statement.prepare();

        try {
            Statement stmt = getConnection().createStatement();
            stmt.executeUpdate(statement.getQuery());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("The TRUNCATE statement could not be executed.");
        }
    }

    protected PreparedStatement prepare(String statement) {
        try {
            return getConnection().prepareStatement(statement);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(f("Insert statement is not valid: [%s]", statement));
        }
    }

}
