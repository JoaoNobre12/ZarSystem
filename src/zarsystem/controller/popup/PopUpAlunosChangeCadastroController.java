package zarsystem.controller.popup;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import zarsystem.controller.Controller;
import zarsystem.controller.MenuController;
import zarsystem.model.Helpers;
import zarsystem.model.domain.Aluno;
import zarsystem.view.blur.Blur;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Controlador do PopUp de edição de dados de alunos
 * Created by joao on 13/11/16.
 */
public class PopUpAlunosChangeCadastroController extends Controller{
    @FXML
    private RadioButton radioMasc;
    @FXML
    private RadioButton radioFemn;
    @FXML
    private Label lblErrorLog;
    @FXML
    private Label lblNomeAluno;
    @FXML
    private TextField txtValorMensalidade;
    @FXML
    private DatePicker dtNascCliente;
    @FXML
    private TextField txtNomeCliente;
    @FXML
    private TextField txtCPFcliente;
    @FXML
    private TextField txtRgCliente;

    @FXML
    private ComboBox<String> cbBoxPlanoCliente;
    @FXML private ComboBox<String> cbBoxDiaDePagamento;

    private Aluno currentAluno;
    private MenuController menuController;

    public PopUpAlunosChangeCadastroController(Aluno currentAluno, MenuController menuController) {
        this.menuController = menuController;
        this.currentAluno = currentAluno;
    }

    @FXML
    public void initialize(){
        System.out.println("Editar dados do aluno "+currentAluno.getNome());
        inits();
    }

    @FXML
    public void salvarEdtAluno(ActionEvent event) {
        System.out.println("Salvar edições do aluno");

        try {
            menuController.alunoDAO.updateAluno(getChangedAluno(), currentAluno.getCodMatricula());
            menuController.loadTableAlunos();
            menuController.loadTablePagPendentes();
            closePopUp(event);
            menuController.showDadosAluno(getChangedAluno());
        } catch (SQLException | ParseException e) {
            Blur.logLabel(lblErrorLog, "Erro ao salvar alterações.");
            e.printStackTrace();
        }
    }

    /**
     * Tirar amontoado de código do método initialize.
     * */
    private void inits(){
        //Máscaras
        Helpers.maskDate(dtNascCliente);
        Helpers.maskRg(txtRgCliente);
        Helpers.maskCpf(txtCPFcliente);

        //Setar sexo do aluno no popup
        if(currentAluno.getSexo().equals("Masculino"))
            radioMasc.setSelected(true);
        else
            radioFemn.setSelected(true);

        lblNomeAluno.setText(currentAluno.getNome());

        NumberFormat formatter = new DecimalFormat("#0.00");
        String valorMonetario = formatter.format(super.valorPlano(currentAluno.getPlano()));

        txtValorMensalidade.setText(valorMonetario);

        dtNascCliente.getEditor().setText(currentAluno.getDtNasc());
        txtNomeCliente.setText(currentAluno.getNome());
        txtCPFcliente.setText(currentAluno.getCpf());
        txtRgCliente.setText(currentAluno.getRg());
        cbBoxPlanoCliente.setValue(currentAluno.getPlano());
        cbBoxDiaDePagamento.setValue(String.valueOf(currentAluno.getDiaPagamento()));
        cbBoxPlanoCliente.setItems(menuController.cbListPlano);
        cbBoxDiaDePagamento.setItems(menuController.cbListDiaPagamento);

        //Mudar valor do plano
        cbBoxPlanoCliente.getSelectionModel().selectedItemProperty().addListener(evt -> txtValorMensalidade.setText(String.valueOf(
                super.valorPlano(cbBoxPlanoCliente.getSelectionModel().getSelectedIndex())).replace('.',',')));
    }

    /**
     * preenche domínio Aluno
     * @return Aluno com os dados preenchidos no popup
     * */
    private Aluno getChangedAluno(){
        Aluno aluno = new Aluno();

        aluno.setNome(txtNomeCliente.getText());
        aluno.setPlano(cbBoxPlanoCliente.getValue());
        aluno.setDiaPagamento(Integer.parseInt(cbBoxDiaDePagamento.getValue()));
        aluno.setDtNasc(dtNascCliente.getEditor().getText());
        aluno.setSexo((radioMasc.isSelected()) ? "M" : "F");
        aluno.setRg(txtRgCliente.getText());
        aluno.setCpf(txtCPFcliente.getText());

        return aluno;
    }

}
