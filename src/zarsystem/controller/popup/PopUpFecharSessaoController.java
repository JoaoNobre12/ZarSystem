package zarsystem.controller.popup;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;
import zarsystem.controller.Controller;

import java.io.IOException;

/**
 * Contralador do popup de fechar sessão
 * Created by joaov on 11/12/2016.
 */
public class PopUpFecharSessaoController extends Controller{

    MenuBar menuBar;

    public PopUpFecharSessaoController(MenuBar menuBar) {
        this.menuBar = menuBar;
    }

    public void returnToLogin(ActionEvent actionEvent) throws IOException{
        Stage stage = (Stage) menuBar.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/zarsystem/view/Login.fxml")));
        scene.setFill(null);
        stage.setScene(scene);
        closePopUp(actionEvent);
    }
}
