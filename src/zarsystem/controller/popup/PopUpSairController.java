package zarsystem.controller.popup;

import javafx.fxml.FXML;
import javafx.scene.control.DialogPane;
import zarsystem.controller.Controller;

/**
 * Created by joao on 09/09/16.
 *
 */
public class PopUpSairController extends Controller {

    @FXML
    private DialogPane rootframe;

    @FXML
    public void initialize(){
        System.out.println("Sair?");
    }

}
