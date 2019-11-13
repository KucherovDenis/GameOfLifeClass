package ru.sbt.javaschool.gameoflife.io;

import ru.sbt.javaschool.gameoflife.GameException;
import ru.sbt.javaschool.gameoflife.formatters.Splitter;

import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class DataBaseWriter implements Writer {

    private final PreparedStatement statement;

    public DataBaseWriter(PreparedStatement statement) {
        this.statement = statement;
    }

    @Override
    public void save(String message, Splitter splitter) {
        try {
            byte[] data = message.getBytes("UTF-8");
            statement.setBytes(1, data);
            statement.execute();
        }catch (UnsupportedEncodingException | SQLException e) {
            throw new GameException(IOMessages.MSG_DATABASE_WRITE_ERROR, e);
        }
    }
}
