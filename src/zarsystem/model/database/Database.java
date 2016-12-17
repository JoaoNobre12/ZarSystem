package zarsystem.model.database;

/**
 * Classe de concexão com db
 * Created by joao on 04/09/16.
 */

import zarsystem.controller.popup.PopUpChangeDbController;
import zarsystem.model.dao.Dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
      String[] configs = CreateDatabase.readDbConfig();

    public  String[] getConfigs() {
        return configs;
    }

    public  void setConfigs(String[] configs) {
        this.configs = configs;
    }


    /**
     * Conecta ao db
     * @return Conncetion ou null se algo der errado
     * */
    public  Connection createConnection() throws Exception{

        String host = configs[0];
        String user = configs[1];
        String pass = configs[2];
        String port = configs[3];

        Dao.popUpChangeDbController.setHost(host);
        Dao.popUpChangeDbController.setUser(user);
        Dao.popUpChangeDbController.setPass(pass);
        Dao.popUpChangeDbController.setPort(port);

        String url = "jdbc:mysql://" + host + ":" + port + "/fit?autoReconnect=true&useSSL=false";

        System.out.println(url);

        Connection cnn;

            Class.forName("com.mysql.jdbc.Driver");

            cnn = DriverManager.getConnection(url, user, pass);


            try {
                Dao.popUpChangeDbController.lblErrorConnection.setText("Conectado com sucesso!");
            } catch (NullPointerException e) {
                System.out.println("Login sem alterar database");
            }

            return cnn;

    }

}