package zarsystem.controller.popup;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import zarsystem.controller.Controller;
import zarsystem.model.database.CreateDatabase;
import zarsystem.model.database.Database;
import zarsystem.model.dao.Dao;
import zarsystem.view.blur.Blur;

import java.io.IOException;

/**
 * Created by joao on 04/11/2016.
 *
 */
public class PopUpChangeDbController extends Controller{

    public TextField txtRootPort;
    public PasswordField txtRootPassword;
    public TextField txtRootUserName;
    public TextField txtRootHost;
    public Button btnRootTestConnc;

    public Label lblErrorConnection; // usada em outras classes https://youtu.be/6OAxxtCqIyk?t=2257
    public Button btnRootResetDb;

    private String host;
    private String user;
    private String pass;
    private String port;

    Database database = new Database();

    public void setHost(String host) {
        this.host = host;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }
    public void setPort(String port) {
        this.port = port;
    }


    @FXML
    public void initialize(){

        try {
            String[] configs = CreateDatabase.readDbConfig();
            setHost(configs != null ? configs[0] : "host");
            setUser(configs != null ? configs[1] : "user");
            setPass(configs != null ? configs[2] : "pass");
            setPort(configs != null ? configs[3] : "port");
        } catch (Exception e) {
            e.printStackTrace();
        }

        txtRootUserName.setText(user);Blur.logLabel(lblErrorConnection, "Erro ao conectar.");
        txtRootHost.setText(host);
        txtRootPassword.setText(pass);
        txtRootPort.setText(port);
    }

    public void salvarAlteracao(ActionEvent actionEvent) {
        try {
            CreateDatabase.writeDbConfig(txtRootHost.getText(), txtRootUserName.getText(), txtRootPassword.getText(), txtRootPort.getText());
            closePopUp(actionEvent);
        } catch (IOException e) {
            Blur.logLabel(lblErrorConnection, "Erro ao salvar Alterações.");
            e.printStackTrace();
        }
    }

    public void testConnection(ActionEvent actionEvent) {
        try {
            CreateDatabase.writeDbConfig(txtRootHost.getText(), txtRootUserName.getText(), txtRootPassword.getText(), txtRootPort.getText());

            setHost(txtRootHost.getText());
            setUser(txtRootUserName.getText());
            setPass(txtRootPassword.getText());
            setPort(txtRootPort.getText());

            database.setConfigs(CreateDatabase.readDbConfig());
            Blur.logLabel(lblErrorConnection, "Conexão feita com sucesso!");
            try {
                Dao.connection = database.createConnection();
            } catch (Exception e) {
                Blur.logLabel(lblErrorConnection, "Erro ao conectar.");
                e.printStackTrace();
            }

        } catch (IOException e) {
            Blur.logLabel(lblErrorConnection, "Erro ao salvar dados.");
            e.printStackTrace();
        }
    }


    public void resetDb() {
        try {
            CreateDatabase.resetDbConfig();

                String[] configs = CreateDatabase.readDbConfig();
                setHost(configs != null ? configs[0] : "host");
                setUser(configs != null ? configs[1] : "user");
                setPass(configs != null ? configs[2] : "pass");
                setPort(configs != null ? configs[3] : "port");

            txtRootUserName.setText(user);
            txtRootHost.setText(host);
            txtRootPassword.setText(pass);
            txtRootPort.setText(port);
            Blur.logLabel(lblErrorConnection, "Alterado para Banco de dados Padrão.");
        } catch (IOException e) {
            e.printStackTrace();
            Blur.logLabel(lblErrorConnection, "Erro ao resetar Banco de dados.");
        }
    }
}
