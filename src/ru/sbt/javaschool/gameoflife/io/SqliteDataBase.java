package ru.sbt.javaschool.gameoflife.io;

import ru.sbt.javaschool.gameoflife.GameException;

import java.sql.*;

public class SqliteDataBase implements DataBase {

    private final Connection connection;

    private Statement selectQuery = null;
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
        CloseResourse(selectQuery);

        String sql = "SELECT generation FROM Generations ORDER BY id DESC";
        try {
            selectQuery = connection.createStatement();
            ResultSet selectResult = selectQuery.executeQuery(sql);
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
