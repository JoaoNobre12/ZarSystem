package zarsystem.controller.popup;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import zarsystem.controller.Controller;
import zarsystem.controller.MenuController;
import zarsystem.model.Helpers;
import zarsystem.model.dao.AvFisicaDAO;
import zarsystem.model.domain.Aluno;
import zarsystem.model.domain.AvaliacaoFisica;
import zarsystem.view.blur.Blur;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Controller da tela de cadastro de avaliação física.
 * Created by joao on 14/09/16.
 */
public class PopUpCadAvFisicaController extends Controller{

    @FXML private Label lblLogsAvFisica;
    @FXML private TextField txtPeso;
    @FXML private Label lblImc;
    @FXML private TextField txtAltura;
    @FXML private TextField txtPescoco;
    @FXML private TextField txtTorax;
    @FXML private TextField txtAbdomem;
    @FXML private TextField txtCintura;
    @FXML private TextField txtQuadril;
    @FXML private TextField txtBracoDireito;
    @FXML private TextField txtBracoEsquerdo;
    @FXML private TextField txtAntebracoDireito;
    @FXML private TextField txtAntebracoEsquerdo;
    @FXML private TextField txtDireita;
    @FXML private TextField txtPanturrilhaDireita;
    @FXML private TextField txtCoxaDireita;
    @FXML private TextField txtCoxaEsquerda;
    @FXML private TextField txtPanturrilhaEsquerda;
    @FXML private TextField txtAbdominal;
    @FXML private TextField txtFlexao;
    @FXML private TextField txtGordura;
    @FXML private TextField txtMetBasal;
    @FXML private TextField txtMetTreino;
    @FXML private Label lblNomeCompleto;

    private AvaliacaoFisica avaliacaoFisica = new AvaliacaoFisica();
    private AvFisicaDAO avFisicaDAO = new AvFisicaDAO();
    private MenuController menuController;
    private boolean avaliacaoFisicaCadastrada;
    private Aluno currentAluno;

    public PopUpCadAvFisicaController(Aluno aluno) {
        currentAluno = aluno;

        this.avaliacaoFisicaCadastrada = menuController.alunoDAO.isAvFisicaCadastrada(currentAluno.getCodMatricula());
    }

    public PopUpCadAvFisicaController(Aluno aluno, MenuController menuController) {
        this.currentAluno = aluno;
        this.menuController = menuController;

        //verificar se a avaliação física está cadastrada
        this.avaliacaoFisicaCadastrada = menuController.alunoDAO.isAvFisicaCadastrada(currentAluno.getCodMatricula());
    }

    @FXML
    public void initialize(){

        System.out.println((menuController.alunoDAO.isAvFisicaCadastrada(currentAluno.getCodMatricula())) ?
                "Avaliação física cadastrada." : "Avaliação física não cadastrada...");

        lblNomeCompleto.setText(currentAluno.getNome());

        System.out.println("Avaliação física do aluno " + currentAluno.getNome());

        //Se a avaliação física estiver cadastrada, alterá-la
        if (avaliacaoFisicaCadastrada){
            AvaliacaoFisica consultAvFisica = avFisicaDAO.consultAvFisica(currentAluno.getCodMatricula());

            txtPeso.setText(String.valueOf(consultAvFisica.getPeso()));
            txtAltura.setText(String.valueOf(consultAvFisica.getAltura()));
            txtPescoco.setText(String.valueOf(consultAvFisica.getPescoco()));
            txtTorax.setText(String.valueOf(consultAvFisica.getTorax()));
            txtAbdomem.setText(String.valueOf(consultAvFisica.getAbdomen()));
            txtCintura.setText(String.valueOf(consultAvFisica.getCintura()));
            txtQuadril.setText(String.valueOf(consultAvFisica.getQuadril()));
            txtBracoDireito.setText(String.valueOf(consultAvFisica.getBracoDireito()));
            txtBracoEsquerdo.setText(String.valueOf(consultAvFisica.getBracoEsquerdo()));
            txtAntebracoDireito.setText(String.valueOf(consultAvFisica.getAntebracoDireito()));
            txtAntebracoEsquerdo.setText(String.valueOf(consultAvFisica.getAntebracoEsquerdo()));
            txtCoxaDireita.setText(String.valueOf(consultAvFisica.getCoxaDireita()));
            txtCoxaEsquerda.setText(String.valueOf(consultAvFisica.getCoxaEsquerda()));
            txtPanturrilhaDireita.setText(String.valueOf(consultAvFisica.getPanturrilhaDireita()));
            txtPanturrilhaEsquerda.setText(String.valueOf(consultAvFisica.getPanturrilhaEsquerda()));
            txtAbdominal.setText(String.valueOf(consultAvFisica.getAbdominal()));
            txtFlexao.setText(String.valueOf(consultAvFisica.getFlexao()));
            txtGordura.setText(String.valueOf(consultAvFisica.getGordura()));
            txtMetBasal.setText(String.valueOf(consultAvFisica.getMetabolismoBasal()));
            txtMetTreino.setText(String.valueOf(consultAvFisica.getMetabolismoTreino()));
        }
    }

    @FXML
    private void salvarAluno(ActionEvent event) throws IOException{
        if (avaliacaoFisicaCadastrada){
            updateAvFisica(event);
        }
        else {
            cadastarAvFisica(event);
        }
    }

    /**
     * Comportamento do popUp quando a avaliação física já estiver cadastrada.
     * */
    private void updateAvFisica(ActionEvent event){
        System.out.println("Alterar avaliação fisica");

        avaliacaoFisica = avFisicaDAO.consultAvFisica(currentAluno.getCodMatricula());
        setAvFisica();

        try {
            avFisicaDAO.updateAvaliacaoFisica(currentAluno.getCodMatricula(), avaliacaoFisica);
        } catch (SQLException e) {
            Blur.logLabel(lblLogsAvFisica, "Erro no updade de av. física");
            e.printStackTrace();
        }

        closePopUp(event);
    }

    /**
     * Preenche o domínio avaliação física
     * */
    private void setAvFisica(){
        avaliacaoFisica.setPeso(Double.parseDouble(txtPeso.getText().replace(',','.')));

        avaliacaoFisica.setAltura(Double.parseDouble(txtAltura.getText().replace(',','.')));
        avaliacaoFisica.setPescoco(Double.parseDouble(txtPescoco.getText().replace(',','.')));
        avaliacaoFisica.setTorax(Double.parseDouble(txtTorax.getText().replace(',','.')));
        avaliacaoFisica.setCintura(Double.parseDouble(txtCintura.getText().replace(',','.')));
        avaliacaoFisica.setAbdomen(Double.parseDouble(txtAbdomem.getText().replace(',','.')));
        avaliacaoFisica.setQuadril(Double.parseDouble(txtQuadril.getText().replace(',','.')));
        avaliacaoFisica.setBracoDireito(Double.parseDouble(txtBracoDireito.getText().replace(',','.')));
        avaliacaoFisica.setBracoEsquerdo(Double.parseDouble(txtBracoEsquerdo.getText().replace(',','.')));
        avaliacaoFisica.setAntebracoDireito(Double.parseDouble(txtAntebracoDireito.getText().replace(',','.')));
        avaliacaoFisica.setAntebracoEsquerdo(Double.parseDouble(txtAntebracoEsquerdo.getText().replace(',','.')));
        avaliacaoFisica.setCoxaDireita(Double.parseDouble(txtCoxaDireita.getText().replace(',','.')));
        avaliacaoFisica.setCoxaEsquerda(Double.parseDouble(txtCoxaEsquerda.getText().replace(',','.')));
        avaliacaoFisica.setPanturrilhaDireita(Double.parseDouble(txtPanturrilhaDireita.getText().replace(',','.')));
        avaliacaoFisica.setPanturrilhaEsquerda(Double.parseDouble(txtPanturrilhaEsquerda.getText().replace(',','.')));
        avaliacaoFisica.setGordura(Double.parseDouble(txtGordura.getText().replace(',','.')));
        avaliacaoFisica.setMetabolismoBasal(Double.parseDouble(txtMetBasal.getText().replace(',','.')));
        avaliacaoFisica.setMetabolismoTreino(Double.parseDouble(txtMetTreino.getText().replace(',','.')));
        avaliacaoFisica.setAbdominal(Integer.parseInt(txtAbdominal.getText()));
        avaliacaoFisica.setFlexao(Integer.parseInt(txtFlexao.getText()));
    }


    /**
     * Comportamento do popUp quando a não estiver cadastrada ainda.
     * */
    private void cadastarAvFisica(ActionEvent event){
        boolean cadastroOk = false;

        System.out.println("Salvar avaliação Aluno");

        try {

            setAvFisica();

            avFisicaDAO.insertDB(avaliacaoFisica, currentAluno);

            //setar que a avaliação física foi cadastrada
            menuController.alunoDAO.setAvFisicaCadastrada(currentAluno.getCodMatricula());

            cadastroOk = true;

            Blur.logLabel(menuController.lblUniversalLogs, "Avaliação Física cadastrada!");

        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            try {
                PopUpErroController popUpErroController = new PopUpErroController("Erro ao cadastrar dados. Tente novamente");

                Stage errorStage = new Stage();

                errorStage.initModality(Modality.APPLICATION_MODAL);
                errorStage.initStyle(StageStyle.TRANSPARENT);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/zarsystem/view/popup/PopUpErro.fxml"));
                loader.setController(popUpErroController);

                Scene scene = new Scene(loader.load());
                scene.setFill(null);
                errorStage.setScene(scene);
                errorStage.show();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            cadastroOk = false;
        } finally {
            System.out.println("O aluno pesa: " + avaliacaoFisica.getPeso()+ "Kg");

            if (cadastroOk){
                System.out.println("Avaliação Física cadastrada com sucesso!");
                closePopUp(event);
            }
        }

    }

    public void changeImc(KeyEvent event) {

        if(("0123456789,".contains(event.getCharacter()))){

            Node field = ((Node) event.getSource());

            if (field.getId().equals("txtPeso") && !(txtAltura.getText().equals(""))){
                String peso = txtPeso.getText()  + event.getCharacter();
                String altura = txtAltura.getText();

                System.out.println("Peso: " + peso + event.getCharacter());
                System.out.println("Altura: " + altura);

                //Exibir Imc
                lblImc.setText(Helpers.calcImc(Double.parseDouble(peso.replace(',','.')),
                        Double.parseDouble(altura.replace(',','.'))));
            }
            else if((field.getId().equals("txtAltura") && !(txtPeso.getText().equals("")))){
                String peso = txtPeso.getText();
                String altura = txtAltura.getText() + event.getCharacter();

                System.out.println("Peso: "+ peso);
                System.out.println("Altura: "+ altura + event.getCharacter());

                //Exibir Imc
                lblImc.setText(Helpers.calcImc(Double.parseDouble(peso.replace(',','.')),
                        Double.parseDouble(altura.replace(',','.'))));
            }
        }
    }
}
