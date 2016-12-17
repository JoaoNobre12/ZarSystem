package zarsystem.controller.popup;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import zarsystem.controller.Controller;
import zarsystem.controller.MenuController;
import zarsystem.model.dao.FuncionarioDAO;
import zarsystem.model.domain.Funcionario;

/**Controlador do pupup de confirmação de exclusão de aluno
 * Created by joao on 17/09/16.
 */
public class PopUpConfirmDeleteFuncionarioController extends Controller{

    @FXML
    private Label lblConfirmNomeFuncionario;

    private FuncionarioDAO funcionarioDAO;
    private TableView<Funcionario> tableFuncionarios;
    private MenuController menuController;
    private Funcionario funcionario;

    public PopUpConfirmDeleteFuncionarioController(Funcionario funcionario, FuncionarioDAO funcionarioDAO, TableView<Funcionario> tableFuncionarios, MenuController menuController) {
        this.funcionario = funcionario;
        this.funcionarioDAO = funcionarioDAO;
        this.tableFuncionarios = tableFuncionarios;
        this.menuController = menuController;
    }

    @FXML
    public void initialize(){
        lblConfirmNomeFuncionario.setText(funcionario.getNome());
    }

   @FXML
   private void deleteFuncionario(ActionEvent actionEvent) {
       System.out.println((funcionarioDAO.deleteFuncionario(funcionario.getCodFuncionario())) ? "Funcionário Apagado" : "Erro ao apagar funcionário");
       menuController.loadTableFuncionarios();

       //Carregar a quantidade de funcionários cadastrados pela quantidade de linhas da tabela
       menuController.lblFuncionariosCadastrados.setText(String.valueOf(tableFuncionarios.getItems().size()));

       closePopUp(actionEvent);
   }
}
