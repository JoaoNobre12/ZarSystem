package zarsystem.controller.popup;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import zarsystem.controller.Controller;
import zarsystem.model.dao.Dao;

import java.io.IOException;

/**
 * Controlador da tela de Login do Usuário Root
 * Created by joao on 06/11/16.
 */
public class PopUpRootLoginController extends Controller{
    @FXML
    private  TextField txtRootUserName;
    @FXML
    private PasswordField passRootPassword;

    private String pass = "root";

    @FXML
    public void initialize(){
        txtRootUserName.setDisable(true);
        txtRootUserName.requestFocus();

        /*Entrar ao pressionar enter*/
        passRootPassword.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if(event.getCode().equals(KeyCode.ENTER)){
                try {
                    verifyRoot(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @FXML
    private void verifyRoot(Event event) throws IOException{
        if(passRootPassword.getText().equals(pass)){
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/zarsystem/view/popup/PopUpChangeDb.fxml"));
            loader.setController(Dao.popUpChangeDbController);
            Scene scene = new Scene(loader.load());

            scene.setFill(null);
            stage.setScene(scene);
        }
        else {
            System.out.println("Root login inválido.");
        }
    }
}
