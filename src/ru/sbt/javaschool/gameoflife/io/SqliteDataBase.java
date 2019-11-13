package ru.sbt.javaschool.gameoflife.io;

import ru.sbt.javaschool.gameoflife.GameException;

import java.io.Closeable;
import java.sql.*;

public class SqliteDataBase implements DataBase {

    private final Connection connection;

    private ResultSet selectResult = null;
    private PreparedStatement insetQuery = null;

    public SqliteDataBase(String fileName) {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + fileName);
        } catch (SQLException e) {
            throw new GameException(IOMessages.MSG_DATABASE_CONNECT_ERROR, e);
        }
    }

    @Override
    public void close() {
        GameException gameException;
        try {
            if (selectResult != null) selectResult.close();
            if (insetQuery != null) insetQuery.close();
            connection.close();
        } catch (SQLException e) {
            throw new GameException(IOMessages.MSG_DATABASE_CLOSE_ERROR, e);
        }
    }

    @Override
    public void clear() {
        String sql = " DROP TABLE IF EXISTS Generations;";
        try (Statement query = connection.createStatement()) {
            query.execute(sql);
            sql = "CREATE TABLE Generations( \n" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                    "generation BLOB NOT NULL " +
                    ");";
            query.execute(sql);
        } catch (SQLException e) {
            throw new GameException(IOMessages.MSG_DATABASE_ACCESS_ERROR, e);
        }
    }

    @Override
    public Loader getLoader() {
        Loader loader = null;
        CloseResourse(selectResult);

        String sql = "SELECT generation FROM Generations ORDER BY id DESC";
        try (Statement statement = connection.createStatement();) {
            selectResult = statement.executeQuery(sql);
            loader = new DataBaseReader(selectResult);
        } catch (SQLException e) {
            throw new GameException(IOMessages.MSG_DATABASE_ACCESS_ERROR, e);
        }
        return loader;
    }

    private void CloseResourse(AutoCloseable closeable) {
        if (closeable != null)
            try {
                closeable.close();
            } catch (Exception e) {
                throw new GameException(IOMessages.MSG_DATABASE_ACCESS_ERROR, e);
            }
    }

    @Override
    public Writer getWriter() {
        Writer writer = null;

        CloseResourse(insetQuery);

        String sql = "INSERT INTO Generations(generation) VALUES(?)";
        try {
            insetQuery = connection.prepareStatement(sql);
            writer = new DataBaseWriter(insetQuery);
        } catch (SQLException e) {
            throw new GameException(IOMessages.MSG_DATABASE_ACCESS_ERROR, e);
        }
        return writer;
    }
}
