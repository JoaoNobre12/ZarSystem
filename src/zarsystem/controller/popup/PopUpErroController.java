package zarsystem.controller.popup;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import zarsystem.controller.Controller;

/**
 * Created by joao on 10/09/16.
 * Classe controladora das mensagens de erro.
 */
public class PopUpErroController extends Controller {
    @FXML private Label lblError;

    private String errorMessage1;

    public PopUpErroController(){}

    public PopUpErroController(String msg) {
        this.errorMessage1 = msg;
    }

    @FXML
    public void initialize(){
        lblError.setText(errorMessage1);
    }
}
