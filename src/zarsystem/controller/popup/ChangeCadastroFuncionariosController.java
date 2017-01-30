package zarsystem.controller.popup;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import zarsystem.controller.Controller;
import zarsystem.controller.MenuController;
import zarsystem.model.Helpers;
import zarsystem.model.domain.Funcionario;
import zarsystem.view.blur.Blur;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Controlador do popUp de atualização de dados de funcionários.
 * Created by joao on 25/11/2016.
 */
public class ChangeCadastroFuncionariosController extends Controller {


    @FXML
    private RadioButton radioSexFeminino;
    @FXML
    private Label lblNomeFuncionario;
    @FXML
    private Label lblErrorLog;
    @FXML
    private TextField txtRgFunc;
    @FXML
    private TextField txtCpfFunc;
    @FXML
    private TextField txtNomeFunc;
    @FXML
    private TextField txtFormacaoFunc;
    @FXML
    private RadioButton radioSexMasculino;
    @FXML
    private TextField txtSalarioFunc;
    @FXML
    private TextField txtObjetivoFunc;

    private Funcionario funcionario;
    private MenuController menuController;

    public ChangeCadastroFuncionariosController(Funcionario funcionario, MenuController menuController) {
        this.menuController = menuController;
        this.funcionario = funcionario;
    }


    @FXML
    public void initialize(){
        System.out.println("Editar dados do funcionário " + funcionario.getNome());
        inits();
    }

    @FXML
    public void salvarEdtFuncionario(ActionEvent actionEvent) {
        try {
            menuController.funcionarioDAO.updateFuncionario(getChangedFuncionario(), funcionario.getCodFuncionario());
            menuController.loadTableFuncionarios();
            Blur.logLabel(menuController.lblUniversalLogs, "Dados alterados com sucesso!");
            closePopUp(actionEvent);
            menuController.showDadosFuncionario(getChangedFuncionario());
        } catch (SQLException  e) {
            Blur.logLabel(lblErrorLog, "Erro ao salvar alterações.");
            e.printStackTrace();
        }
    }

    /**
     * Tirar amontoado de código do método initialize.
     * */
    private void inits(){
        //Máscaras
        Helpers.maskRg(txtRgFunc);
        Helpers.maskCpf(txtCpfFunc);

        //Setar sexo do aluno no popup
        if(funcionario.getSexo().equals("Masculino"))
            radioSexMasculino.setSelected(true);
        else
            radioSexFeminino.setSelected(true);

        lblNomeFuncionario.setText(funcionario.getNome());

        NumberFormat formatter = new DecimalFormat("#0.00");
        String valorMonetario = formatter.format(funcionario.getSalario());

        txtSalarioFunc.setText(valorMonetario);

        txtNomeFunc.setText(funcionario.getNome());
        txtRgFunc.setText(funcionario.getRg());
        txtCpfFunc.setText(funcionario.getCpf());
        txtFormacaoFunc.setText(funcionario.getFormacao());
        txtObjetivoFunc.setText(funcionario.getObjetivo());
    }

    /**
     * Setar funcionário alterado
     */
    private Funcionario getChangedFuncionario(){
        Funcionario funcionario = new Funcionario();

        funcionario.setNome(txtNomeFunc.getText());
        funcionario.setRg(txtRgFunc.getText());
        funcionario.setCpf(txtCpfFunc.getText());
        funcionario.setObjetivo(txtObjetivoFunc.getText());
        funcionario.setFormacao(txtFormacaoFunc.getText());
        funcionario.setSalario(Double.parseDouble(txtSalarioFunc.getText().replace(',','.')));
        funcionario.setSexo((radioSexMasculino.isSelected()) ? "M" : "F");

        return funcionario;
    }
}
