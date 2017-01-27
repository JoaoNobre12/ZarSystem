package zarsystem.model.dao;

import zarsystem.controller.popup.PopUpChangeDbController;

import java.sql.Connection;

/**
 * Created by joao on 06/11/16.
 * Classe que Gera conxão com database
 */
public abstract class Dao {


    public static Connection connection;
    //protected Connection connection2 = Database.createConnection();

    public static PopUpChangeDbController popUpChangeDbController =
            new PopUpChangeDbController();
    
}
