package ru.sbt.javaschool.gameoflife.io;

import ru.sbt.javaschool.gameoflife.GameException;

import java.sql.*;

public class SqliteDataBase implements DataBase {

    private final Connection connection;

    private Statement selectQuery;
    private PreparedStatement insetQuery;

    public SqliteDataBase(String fileName) {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + fileName);
            createDbTable();
            selectQuery = connection.createStatement();
            String sql = "INSERT INTO Generations(generation) VALUES(?)";
            insetQuery = connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new GameException(IOMessages.MSG_DATABASE_CONNECT_ERROR, e);
        }
    }

    private void createDbTable() {
        try (Statement query = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS Generations( \n" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                    "generation BLOB NOT NULL " +
                    ");";
            query.execute(sql);
        } catch (SQLException e) {
            throw new GameException(IOMessages.MSG_DATABASE_ACCESS_ERROR, e);
        }
    }

    @Override
    public void close() {
        GameException gameException = null;
        if (selectQuery != null)
            try {
                selectQuery.close();
            } catch (SQLException e) {
                gameException = new GameException(IOMessages.MSG_DATABASE_CLOSE_ERROR, e);
            }

        if (insetQuery != null)
            try {
                insetQuery.close();
            } catch (SQLException e) {
                if (gameException != null) {
                    gameException.addSuppressed(e);
                } else {
                    gameException = new GameException(IOMessages.MSG_DATABASE_CLOSE_ERROR, e);
                }
            }

        try {
            connection.close();
        } catch (SQLException e) {
            if (gameException != null) {
                gameException.addSuppressed(e);
            } else {
                gameException = new GameException(IOMessages.MSG_DATABASE_CLOSE_ERROR, e);
            }
        }

        if (gameException != null)
            throw gameException;
    }

    @Override
    public void clear() {
        try (Statement query = connection.createStatement()) {
            String sql = "DELETE FROM Generations";
            query.execute(sql);
        } catch (SQLException e) {
            throw new GameException(IOMessages.MSG_DATABASE_ACCESS_ERROR, e);
        }
    }

    @Override
    public Loader getLoader() {
        Loader loader;

        String sql = "SELECT generation FROM Generations ORDER BY id DESC";
        try {
            ResultSet selectResult = selectQuery.executeQuery(sql);
            loader = new DataBaseReader(selectResult);
        } catch (SQLException e) {
            throw new GameException(IOMessages.MSG_DATABASE_ACCESS_ERROR, e);
        }
        return loader;
    }

    @Override
    public Writer getWriter() {
        return new DataBaseWriter(insetQuery);
    }
}
