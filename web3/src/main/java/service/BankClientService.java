package service;

import dao.BankClientDAO;
import model.BankClient;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class BankClientService {


    public BankClientService() {
    }

    public BankClient getClientById(long id) {
        return getBankClientDAO().getClientById(id);
    }

    public BankClient getClientByName(String name) {
        return getBankClientDAO().getClientByName(name);
    }

    public List<BankClient> getAllClient() {
        return getBankClientDAO().getAllBankClient();
    }

    public boolean deleteClient(String name) {
        return getBankClientDAO().deleteClientByName(name);
    }

    public boolean addClient(BankClient client) {
        if (getBankClientDAO().getClientByName(client.getName()) == null) {
            return getBankClientDAO().addClient(client) > 0;
        }
        return false;
    }

    public boolean sendMoneyToClient(BankClient sender, String name, Long value) {
        BankClient receiver = getBankClientDAO().getClientByName(name);
        if (getBankClientDAO().validateClient(sender.getName(), sender.getPassword()) && receiver != null
                                         && getBankClientDAO().isClientHasSum(sender.getName(), value) &&
                                                            !sender.getName().equals(receiver.getName())) {
            getBankClientDAO().updateClientsMoney(sender.getName(), sender.getPassword(), -value);
            getBankClientDAO().updateClientsMoney(receiver.getName(), receiver.getPassword(), value);
            return true;
        }
        return false;
    }

    public void cleanUp() {

        getBankClientDAO().dropTable();
    }

    public void createTable() {
        getBankClientDAO().createTable();
    }

    private static Connection getMysqlConnection() {
       try {
             DriverManager.registerDriver((Driver) Class.forName("com.mysql.cj.jdbc.Driver").newInstance());
            StringBuilder url = new StringBuilder();

            url.
                    append("jdbc:mysql://").        //db type
                    append("localhost:").           //host name
                    append("3306/").                //port
                    append("web3?").                //db name
                    append("user=root&").           //login
                    append("password=0268q7410").  //password
                    append("&serverTimezone=UTC");  //setup server time

            return DriverManager.getConnection(url.toString());
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }

    private static BankClientDAO getBankClientDAO() {
        return new BankClientDAO(getMysqlConnection());
    }
}
