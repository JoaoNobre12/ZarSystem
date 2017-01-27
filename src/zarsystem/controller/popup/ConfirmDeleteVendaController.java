package zarsystem.controller.popup;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import zarsystem.controller.Controller;
import zarsystem.controller.MenuController;
import zarsystem.view.blur.Blur;

import java.sql.SQLException;

/**
 * Controlador do popup de exclusão de vendas
 * Created by joaov on 05/12/2016.
 */
public class ConfirmDeleteVendaController extends Controller{

    private MenuController menuController;

    public ConfirmDeleteVendaController(MenuController menuController) {
        this.menuController = menuController;
    }

    @FXML
    public void initialize(){
        System.out.println("Excluir Histórico");
    }

    @FXML
    public void excluirHistorico(ActionEvent actionEvent){
        try {
            menuController.produtoDao.dropHistorico();
            menuController.loadTableCstProdutos();
            Blur.logLabel(menuController.lblUniversalLogs, "Histórico reiniciado.");
            closePopUp(actionEvent);
        } catch (SQLException e) {
            Blur.logLabel(menuController.lblUniversalLogs, "Erro ao apagar histórico");
            e.printStackTrace();
        }
    }
}
