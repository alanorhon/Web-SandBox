package dao;

import model.BankClient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BankClientDAO {

    private Executor executor;
    private Connection connection;

    public BankClientDAO(Connection connection) {
        this.connection = connection;
        executor = new Executor(connection);
    }

    public List<BankClient> getAllBankClient() {
        return executor.executeQuery("SELECT * FROM bank_client", resultSet -> {
            List<BankClient> clients = new ArrayList<>();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                long money = resultSet.getLong("money");
                clients.add(new BankClient(id, name, password, money));
            }
            return clients;
        });
    }

    public boolean validateClient(String name, String password) {
        boolean result = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement
                ("SELECT * FROM bank_client WHERE name=? AND password=?")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            result = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void updateClientsMoney(String name, String password, Long transactValue) {
        try (PreparedStatement preparedStatement = connection.prepareStatement
                ("UPDATE bank_client SET money = money+? " +
                        "WHERE name=? AND password=?")) {
            connection.setAutoCommit(false);
            preparedStatement.setLong(1, transactValue);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, password);
            preparedStatement.execute();
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
    }


    public BankClient getClientById(long id) {
        return executor.executeQuery("SELECT * FROM bank_client WHERE id=" + id, resultSet -> {
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                long money = resultSet.getLong("money");
                return new BankClient(id, name, password, money);
            }
            return null;
        });
    }

    public boolean isClientHasSum(String name, Long expectedSum) {
        return executor.executeQuery("SELECT money FROM bank_client WHERE name='" + name + "'", resultSet -> {
            resultSet.next();
            return resultSet.getLong("money") >= expectedSum;
        });
    }

    public long getClientIdByName(String name) {
        return executor.executeQuery("SELECT id FROM bank_client WHERE name ='" + name + "'", resultSet -> {
            resultSet.next();
            return resultSet.getLong("id");
        });
    }

    public BankClient getClientByName(String name) {
        return executor.executeQuery("SELECT * FROM bank_client WHERE name ='" + name + "'", resultSet -> {
            if (resultSet.next()) {
                long id = resultSet.getLong("id");
                String password = resultSet.getString("password");
                long money = resultSet.getLong("money");
                return new BankClient(id, name, password, money);
            }
            return null;
        });
    }

    public int addClient(BankClient client) {
        int result = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO bank_client(name, password, money) VALUES(?,?,?)")) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getPassword());
            preparedStatement.setLong(3, client.getMoney());
            result = preparedStatement.executeUpdate();
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
        return result;
    }

    public void createTable() {
        executor.executeUpdate("CREATE TABLE IF NOT EXISTS bank_client (id BIGINT AUTO_INCREMENT, name VARCHAR(256)," +
                                                            " password VARCHAR(256), money BIGINT, PRIMARY KEY (id))");
    }

    public void dropTable() {
        executor.executeUpdate("DROP TABLE IF EXISTS bank_client");
    }

    public boolean deleteClientByName(String name) {
        return executor.executeUpdate("DELETE FROM bank_client WHERE name='" + name + "'") > 0;
    }
}
