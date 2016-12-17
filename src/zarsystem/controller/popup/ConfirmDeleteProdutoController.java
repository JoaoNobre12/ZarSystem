package zarsystem.controller.popup;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import zarsystem.controller.Controller;
import zarsystem.controller.MenuController;
import zarsystem.model.dao.ProdutoDao;
import zarsystem.model.domain.Produto;
import zarsystem.view.blur.Blur;

import java.sql.SQLException;

/**
 * Controlador do PopUp de exclusão de produtos
 * Created by joaov on 30/11/2016.
 */
public class ConfirmDeleteProdutoController extends Controller{
    @FXML private Label lblConfirmNomeProduto;

    private Produto produto;
    private ProdutoDao produtoDao;
    private TableView<Produto> tableProdutos;
    private MenuController menuController;

    public ConfirmDeleteProdutoController(Produto produto, ProdutoDao produtoDao, TableView<Produto> tableProdutos, MenuController menuController) {
        this.produto = produto;
        this.produtoDao = produtoDao;
        this.tableProdutos = tableProdutos;
        this.menuController = menuController;
    }

    @FXML
    public void initialize(){
        lblConfirmNomeProduto.setText(produto.getNome());
    }


    public void deleteProduto(ActionEvent actionEvent) {
        try {
            produtoDao.deleteProduto(produto.getCodProduto());
        } catch (SQLException e) {
            Blur.logLabel(menuController.lblUniversalLogs, "Erro ao excluir produto");
            e.printStackTrace();
        }

        menuController.loadTableCstProdutos();

        closePopUp(actionEvent);
    }

}
