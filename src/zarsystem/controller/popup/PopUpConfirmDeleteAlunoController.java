package zarsystem.controller.popup;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import zarsystem.controller.Controller;
import zarsystem.controller.MenuController;
import zarsystem.model.dao.AlunoDAO;
import zarsystem.model.domain.Aluno;

/**Controlador do pupup de confirmação de exclusão de aluno
 * Created by joao on 17/09/16.
 */
public class PopUpConfirmDeleteAlunoController extends Controller{
    @FXML
    private Label lblConfirmNomeAluno;

    private AlunoDAO alunoDAO;
    private TableView<Aluno> tableAluno;
    private MenuController menuController;

    public PopUpConfirmDeleteAlunoController(Aluno aluno, AlunoDAO alunoDAO, TableView<Aluno> tableAluno, MenuController menuController) {
        super.currentAluno = aluno;
        this.alunoDAO = alunoDAO;
        this.tableAluno = tableAluno;
        this.menuController = menuController;
    }

    @FXML
    public void initialize(){
        lblConfirmNomeAluno.setText(currentAluno.getNome());
    }

    @FXML
    private void deleteAluno(ActionEvent event) {

        System.out.println((alunoDAO.deleteAlunoDB(currentAluno.getCodMatricula())) ? "Aluno Apagado" : "Erro ao apagar");

        try {
            menuController.loadTablePagPendentes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        tableAluno.setItems(FXCollections.observableArrayList(alunoDAO.consultDb()));

        closePopUp(event);

        menuController.lblClientesCadastrados.setText(String.valueOf(alunoDAO.getNumAlunosCadastrados()));
    }
}
