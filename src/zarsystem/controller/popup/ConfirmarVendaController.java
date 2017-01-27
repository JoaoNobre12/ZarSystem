package zarsystem.controller.popup;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import zarsystem.controller.Controller;
import zarsystem.controller.MenuController;
import zarsystem.model.domain.Produto;
import zarsystem.model.domain.Venda;
import zarsystem.view.blur.Blur;

import java.sql.SQLException;
import java.util.List;

/**
 * Controlador do popup de confirmação de venda
 * Created by joaov on 30/11/2016.
 */
public class ConfirmarVendaController extends Controller{

    @FXML private Label lblQtdProdutos;

    private MenuController menuController;
    private List<Produto> produtoList;

    public ConfirmarVendaController(MenuController menuController, List<Produto> produtoList) {
        this.menuController = menuController;
        this.produtoList = produtoList;
    }

    @FXML
    public void initialize(){
        System.out.println("Quantidade de produtos vendidos: " + produtoList.size());
        lblQtdProdutos.setText(produtoList.size() > 1 ? "Deseja confirmar a venda de " +  produtoList.size() + " produtos?" :
                "Deseja confirmar a venda de " +  produtoList.size() + " produto?");
    }

    public void confirmarVenda(ActionEvent actionEvent) {
        for (Produto p : produtoList) {
            try {

                Venda venda = new Venda(p);

                menuController.produtoDao.insertHistorico(venda);

                menuController.produtoDao.updateVendas(p);
            } catch (SQLException e) {
                Blur.logLabel(menuController.lblUniversalLogs, "Erro ao consultar produtos no histórico.");
                e.printStackTrace();
            }
        }

        menuController.terminarVenda();
        closePopUp(actionEvent);
        Blur.logLabel(menuController.lblUniversalLogs, "Venda realizada com sucesso!");
    }
}
