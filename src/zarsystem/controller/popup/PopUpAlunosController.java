package zarsystem.controller.popup;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import zarsystem.controller.Controller;
import zarsystem.controller.MenuController;
import zarsystem.model.Helpers;
import zarsystem.model.dao.AlunoDAO;
import zarsystem.model.dao.AvFisicaDAO;
import zarsystem.model.domain.Aluno;
import zarsystem.model.domain.AvaliacaoFisica;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by joao on 07/09/16.
 * Controller PopUpAlunos
 */
public class PopUpAlunosController extends Controller {

    public Label lblImc;
    @FXML private Label lblIdade;
    @FXML private Label lblPeso;
    @FXML private Label lblAltura;
    @FXML private Label lblTorax;
    @FXML private Label lblPescoco;
    @FXML private Label lblCintura;
    @FXML private Label lblAbdomen;
    @FXML private Label lblBracoDireito;
    @FXML private Label lblQuadril;
    @FXML private Label lblCoxaDireita;
    @FXML private Label lblPanturrilhaDireita;
    @FXML private Label lblBracoEsquerdo;
    @FXML private Label lblAntebracoDireito;
    @FXML private Label lblAntebracoEsquerdo;
    @FXML private Label lblCoxaEsquerda;
    @FXML private Label lblPanturrilhaEsquerda;
    @FXML private Label lblFlexao;
    @FXML private Label lblAbdominal;
    @FXML private Label lblMetBasal;
    @FXML private Label lblGordura;
    @FXML private Label lblMetTreino;
    @FXML private Label lblAvFisicaStatus;
    @FXML private Label lblNumeroMatricula;
    @FXML private Label lblPlano;
    @FXML private Label lblValorPlano;
    @FXML private Label lblDataDeNascimento;
    @FXML private Label lblRG;
    @FXML private Label lblCPF;
    @FXML private Label lblNomeCompleto;

    private MenuController menuController;

    AvFisicaDAO avFisicaDAO = new AvFisicaDAO();

    public PopUpAlunosController(Aluno currentAluno, MenuController menuController) {
        super.currentAluno = currentAluno;
        this.menuController = menuController;
    }

    AlunoDAO alunoDAO = new AlunoDAO(this.menuController);

    @FXML
    public void initialize(){
        inits();

        NumberFormat formatter = new DecimalFormat("#0.00");

        String valorMonetario = formatter.format(super.valorPlano(currentAluno.getPlano()));

        lblValorPlano.setText("R$ "+ valorMonetario);
    }

    /**
     * Seta labels e inicializa componentes.
     * */
    private void inits(){
            //pegar a avaliação fisica
            AvaliacaoFisica avaliacaoFisica = avFisicaDAO.consultAvFisica(currentAluno.getCodMatricula());

            //logs
            System.out.println("\nDetalhes do Aluno");
            System.err.println("\nCodMatricula: " + currentAluno.getCodMatricula());
            System.err.println("Nome: " + currentAluno.getNome());
            System.out.println("\nCódigo da avFisica: "+avaliacaoFisica.getCodAvFisica());
            System.out.println("Peso " + avaliacaoFisica.getPeso());
            System.out.println("Altura " + avaliacaoFisica.getAltura());


        if(avaliacaoFisica.getCodAvFisica() == 0 && avaliacaoFisica.getPeso() == 0){
            lblAvFisicaStatus.setText("Avaliação Física não cadastrada");
        }
        else {
            lblAvFisicaStatus.setText("");
            lblImc.setText(Helpers.calcImc(avaliacaoFisica.getPeso(), avaliacaoFisica.getAltura()));
        }

        //Aluno
        lblNomeCompleto.setText(currentAluno.getNome());
        lblPlano.setText(currentAluno.getPlano());
        lblNumeroMatricula.setText(String.valueOf(currentAluno.getCodMatricula()));
        lblDataDeNascimento.setText(currentAluno.getDtNasc());
        lblRG.setText(currentAluno.getRg());
        lblCPF.setText(currentAluno.getCpf());

        //Avaliação física do aluno
        lblIdade.setText(String.valueOf(alunoDAO.colsultIdadeAluno(currentAluno.getCodMatricula())) + " anos");
        lblPeso.setText(String.valueOf(avaliacaoFisica.getPeso()));
        lblAltura.setText(String.valueOf(avaliacaoFisica.getAltura()));
        lblTorax.setText(String.valueOf(avaliacaoFisica.getTorax()));
        lblPescoco.setText(String.valueOf(avaliacaoFisica.getPescoco()));
        lblCintura.setText(String.valueOf(avaliacaoFisica.getCintura()));
        lblAbdomen.setText(String.valueOf(avaliacaoFisica.getAbdomen()));
        lblBracoDireito.setText("Dr: " + String.valueOf(avaliacaoFisica.getBracoDireito()));
        lblBracoEsquerdo.setText("Er: " + String.valueOf(avaliacaoFisica.getBracoEsquerdo()));
        lblAntebracoDireito.setText("Dc: " + String.valueOf(avaliacaoFisica.getAntebracoDireito()));
        lblAntebracoEsquerdo.setText("Ec: " + String.valueOf(avaliacaoFisica.getAntebracoEsquerdo()));
        lblQuadril.setText(String.valueOf(avaliacaoFisica.getBracoEsquerdo()));
        lblCoxaDireita.setText(String.valueOf(avaliacaoFisica.getCoxaDireita()));
        lblCoxaEsquerda.setText(String.valueOf(avaliacaoFisica.getCoxaEsquerda()));
        lblPanturrilhaDireita.setText(String.valueOf(avaliacaoFisica.getPanturrilhaDireita()));
        lblPanturrilhaEsquerda.setText(String.valueOf(avaliacaoFisica.getPanturrilhaEsquerda()));
        lblFlexao.setText(String.valueOf(avaliacaoFisica.getFlexao()));
        lblAbdominal.setText(String.valueOf(avaliacaoFisica.getAbdominal()));
        lblMetBasal.setText(String.valueOf(avaliacaoFisica.getMetabolismoBasal()));
        lblGordura.setText(String.valueOf(avaliacaoFisica.getGordura()));
        lblMetTreino.setText(String.valueOf(avaliacaoFisica.getMetabolismoTreino()));
    }
}
