package ru.sbt.javaschool.gameoflife.io;

import ru.sbt.javaschool.gameoflife.GameException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataBaseReader implements Loader {

    private final ResultSet resultSet;

    public DataBaseReader(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    @Override
    public List<String> load() {
        List<String> result = null;
        try {
            if (resultSet.next()) {
                result = new ArrayList<>();
                byte[] data = resultSet.getBytes(1);
                String dataStr = new String(data);
                result.add(dataStr);
            }

        } catch (SQLException e) {
            throw new GameException(IOMessages.MSG_DATABASE_READ_ERROR, e);
        }

        return result;
    }
}
