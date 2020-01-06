package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Executor {
    private Connection connection;

    public Executor(Connection connection) {
        this.connection = connection;
    }

    public int executeUpdate(String updateQuery) {
        int countOfUpdated = 0;
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            countOfUpdated = statement.executeUpdate(updateQuery);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
        return countOfUpdated;
    }

    public <T> T executeQuery(String query, ResultHandler<T> resultHandler) {
        T result = null;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            result = resultHandler.handle(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
