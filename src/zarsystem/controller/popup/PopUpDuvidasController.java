package zarsystem.controller.popup;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import zarsystem.controller.Controller;
import zarsystem.controller.MenuController;
import zarsystem.model.domain.ClienteSite;

/**
 * Contralador do popup de de emails
 * Created by joaov on 10/12/2016.
 */
public class PopUpDuvidasController extends Controller{
    @FXML private Label lblNomeCliente;
    @FXML private Label lblEmailCliente;
    @FXML private TextArea txtAreaDuvida;

    MenuController menuController;
    ClienteSite clienteSite;

    public PopUpDuvidasController(MenuController menuController, ClienteSite clienteSite) {
        this.menuController = menuController;
        this.clienteSite = clienteSite;
    }

    @FXML
    public void initialize(){
        lblNomeCliente.setText(clienteSite.getNome());
        lblEmailCliente.setText(clienteSite.getEmail());
        txtAreaDuvida.setText(clienteSite.getDuvida());
    }
}
