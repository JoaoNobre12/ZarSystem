package zarsystem.controller;

/**
 * Created by joao on 04/09/16.
 * Controlador do view menu principal
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import zarsystem.controller.popup.*;
import zarsystem.model.Helpers;
import zarsystem.model.dao.AlunoDAO;
import zarsystem.model.dao.FuncionarioDAO;
import zarsystem.model.dao.ProdutoDao;
import zarsystem.model.domain.Aluno;
import zarsystem.model.domain.Funcionario;
import zarsystem.model.domain.Produto;
import zarsystem.view.blur.Blur;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MenuController extends Controller {
    public AlunoDAO alunoDAO = new AlunoDAO(this);

    //Aba Home
    public Label lblFuncionariosCadastrados;
    public Label lblProdutosCadastrados;
    public Label lblClientesCadastrados;
    public Button btnPagLimparAluno;


    private Aluno aluno = new Aluno();
    private Helpers helpers = new Helpers();

    //TabPane que contém todos os outros
    @FXML private TabPane tabPanePrincipal;

    @FXML private Tab tabHome;

    //Seu Tab e seus respectivos tabPanes
    @FXML private Tab tabClientes;
    @FXML private TabPane tabPaneClientes;

    @FXML private Tab tabProdutos;
    @FXML private TabPane tabPaneProdutos;

    @FXML private Tab tabFuncionarios;
    @FXML private TabPane tabPaneFuncionarios;

    @FXML private Tab tabFinancas;
    @FXML private Tab tabAjuda;
    @FXML private Tab tabSobre;


    /*
    * Descomentar tipo de usuario
    * deixar connect dadatase apenas na tela de splash
    * 12/11
    * */
    @FXML
    public void initialize(){
        /*arastar*/
        super.dragWindow(frame);

        //só na tela de splash
        connectDatabase();

        /*Log de boas vindas*/
        Blur.logLabel(lblUniversalLogs, "Bem-vindo!");

        /*Tipo de usuário*/
        /*System.out.println("Tipo de usuário: " + user.getTipo());
        if (user.getTipo().equals("atendente")){
            tabFinancas.setDisable(true);
            tabFuncionarios.setDisable(true);
        }*/

        //inicializações referentes ao cadastro e consulta de alunos.
        initsAlunos();

        //Inicializações da aba de funcionários
        initsFuncionario();

        //Inicializações da aba de produtos
        initsProdutos();
    }
    /*-**********************************SERVEM PARA TODOS OS TABS*****************************************************/
     private void callUniversalErrorPopup(Event event){
         try {
             Parent parent = ((Node) event.getSource()).getParent().getParent().getParent().getParent().getParent()
                     .getParent().getParent().getParent();

             Blur.blurParent(parent);

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

             errorStage.setOnHiding(evt -> Blur.unblurParent(parent));
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
    /*-**********************************MENUVIEW*********************************************************************/
    public void openCadastroClientes() {
        System.out.println("Aba Cadastro de Clientes");

        tabPanePrincipal.getSelectionModel().select(1);
        tabPaneClientes.getSelectionModel().select(1);
    }

    public void openCadastroFuncionarios() {
        System.out.println("Aba Cadastro de Funcionários");

        tabPanePrincipal.getSelectionModel().select(3);
        tabPaneFuncionarios.getSelectionModel().select(1);
    }

    public void openCadastroProdutos() {
        System.out.println("Aba Cadastro de Produto");

        tabPanePrincipal.getSelectionModel().select(2);
        tabPaneProdutos.getSelectionModel().select(1);
    }

    public void openConsultaClientes() {
        System.out.println("Aba Consulta de Clientes");

        tabPanePrincipal.getSelectionModel().select(1);
        tabPaneClientes.getSelectionModel().select(0);
    }

    public void openConsultaFuncionarios() {
        System.out.println("Aba Consulta de Funcionários");

        tabPanePrincipal.getSelectionModel().select(3);
        tabPaneFuncionarios.getSelectionModel().select(0);
    }

    /**
     * Abre aba de consulta de produtos
     * */
    public void openConsultaProdutos() {
        System.out.println("Aba Consulta de Produto");

        tabPanePrincipal.getSelectionModel().select(2);
        tabPaneProdutos.getSelectionModel().select(0);
    }

    /**
     * Abre aba de histórico de produtos
     * */
    public void openHistoricoProdutos() {
        System.out.println("Aba Historico de Produto");

        tabPanePrincipal.getSelectionModel().select(2);
        tabPaneProdutos.getSelectionModel().select(2);
    }


    public void openSobre() {
        System.out.println("Aba Sobre");

        tabPanePrincipal.getSelectionModel().select(6);
        Blur.logLabel(lblUniversalLogs, "Desenvolvido com <3");
    }

    public void openAjuda() {
        System.out.println("Aba Ajuda");

        tabPanePrincipal.getSelectionModel().select(5);
    }

    /*-***************************************ALUNOS******************************************************************/
    public ObservableList<String> cbListPlano = FXCollections.observableArrayList(
            "Musculação + Aeróbico", "Sertanejo", "Muay Thai", "Musculação + Sertanejo", "Musculação + Sertanejo + Muay Thai",
            "Musculação + Muay Thai"
    );//combo box plano

    public ObservableList<String> cbListDiaPagamento = FXCollections.observableArrayList(
            "05", "10", "15", "20", "25"
    );//combo box dia do pagamento


    @FXML protected Button btnAlunoCdtAvFisica;
    @FXML protected ComboBox<String> cbBoxPlanoCliente;
    @FXML protected ComboBox<String> cbPlanoAluno;
    @FXML protected Label lblTime;
    @FXML public Label lblUniversalLogs;
    @FXML public TableView<Aluno> tableAluno;
    @FXML private TextField txtRGcliente;
    @FXML private ComboBox<String> cbBoxDiaDePagamento;
    @FXML private TextField txtCPFcliente;
    @FXML private TextField txtNomeCliente;
    @FXML private DatePicker dtNascCliente;
    @FXML private Button btnExcluirAluno;
    @FXML private Button btnAlunoEdtCadastro;
    @FXML private TextField txtRgAluno;
    @FXML private TextField txtDtNascAluno;
    @FXML private TextField txtCpfAluno;
    @FXML private TextField txtNomeAluno;
    @FXML private TextField txtNumMatAluno;
    @FXML private TextField txtValorMensalidade;
    @FXML private TextField txtValorCarteirinha;
    @FXML private TextField txtValorMatricula;
    @FXML private Button btnLimparAluno;
    @FXML private RadioButton radioSexoFeminino;
    @FXML private RadioButton radioSexoMasculino;
    @FXML private CheckBox ckBoxPrimeiroPagamento;

    //Aba pagamento
    public TableView<Aluno> tablePagamentoAlunos;
    @FXML private TextField txtPagNumMatricula;
    @FXML private TextField txtPagNomeAluno;
    @FXML private TextField txtPagValorPlano;
    @FXML private TextField txtPagPlano;

    private Aluno selectedAluno; // há um current aluno na classe parent, refatorar....


    public Aluno getSelectedAluno() {
        return selectedAluno;
    }

    public void setSelectedAluno(Aluno selectedAluno) {
        this.selectedAluno = selectedAluno;
    }

    /**
     * Código que estaria no initialize, inicializações
     * */
    private void initsAlunos(){
        /*Limpar botões*/
        btnLimparAluno.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Helpers.clearTextFields(txtNomeAluno,txtNumMatAluno,txtCpfAluno,txtRgAluno,txtDtNascAluno);

            //iniciar combobox da aba de consulta
            cbPlanoAluno.getSelectionModel().select(-1);
            loadTableAlunos();
        });

        //Limpar dados aba pagamento
        btnPagLimparAluno.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> Helpers.clearTextFields(txtPagNumMatricula,txtPagNomeAluno,txtPagValorPlano,txtPagPlano));

        /*Mascaras*/
        Helpers.maskDate(dtNascCliente);
        Helpers.maskRg(txtRGcliente);
        Helpers.maskCpf(txtCPFcliente);
        Helpers.maskRg(txtRgAluno);
        Helpers.maskCpf(txtCpfAluno);

        /*inicializações*/
        helpers.initializes(lblTime, frame, cbBoxPlanoCliente, cbPlanoAluno,
                cbBoxDiaDePagamento, cbListPlano, cbListDiaPagamento);

        /*REFATORAR*/
        //Mudar valor do plano
        cbBoxPlanoCliente.getSelectionModel().selectedItemProperty().addListener(evt -> {

            txtValorMensalidade.setText(String.valueOf(super.valorPlano(cbBoxPlanoCliente.getSelectionModel().getSelectedIndex())).replace('.',','));
            txtValorCarteirinha.setText("5,00");
            txtValorMatricula.setText("5,00");

            cbBoxDiaDePagamento.getSelectionModel().select(0);
        });

        //Consulta ao banco de dados
        loadTableAlunos();

        /*Desabilitar botões que precisam de seleção*/
        btnExcluirAluno.disableProperty().bind(tableAluno.getSelectionModel().selectedItemProperty().isNull());
        btnAlunoCdtAvFisica.disableProperty().bind(tableAluno.getSelectionModel().selectedItemProperty().isNull());
        btnAlunoEdtCadastro.disableProperty().bind(tableAluno.getSelectionModel().selectedItemProperty().isNull());


        //Consultar via ComboBox
        cbPlanoAluno.addEventHandler(ActionEvent.ACTION, event -> {
            if (cbPlanoAluno.isShowing()){
                List<Aluno> alunos = alunoDAO.searchAluno("plano", cbPlanoAluno.getValue());
                tableAluno.setItems(FXCollections.observableArrayList(alunos));
            }
        });

        /*pegar clicks nas linhas da tabela*/
        tableAluno.setRowFactory(tv -> {

            TableRow<Aluno> row = new TableRow<>();

            row.setOnMouseClicked(evt -> {

                Aluno a = tableAluno.getSelectionModel().getSelectedItem();

                setSelectedAluno(tableAluno.getSelectionModel().getSelectedItem());

                showDadosAluno(a);

                //Abrir detalhes do aluno
                if((evt.getClickCount() == 2) && !(row.isEmpty())){
                    try {
                        Aluno rowData = row.getItem();

                        System.out.println("Código da avaliação física " + rowData.getCodAvFisica());

                        callAlunoDetails(rowData, evt);
                    } catch (IOException e) {
                        Blur.logLabel(lblClientesCadastrados, "Erro ao carregar dados do aluno.");
                        e.printStackTrace();
                    }
                }

            });
            return row;
        }); //Mudar Itens do field consulta

        //pegar clicks nas linhas da tabela pagamento
        tablePagamentoAlunos.setRowFactory(tv -> {

            TableRow<Aluno> row = new TableRow<>();

            row.setOnMouseClicked(evt -> {

                Aluno a = tablePagamentoAlunos.getSelectionModel().getSelectedItem();

                setSelectedAluno(tablePagamentoAlunos.getSelectionModel().getSelectedItem());

                txtPagNumMatricula.setText(String.valueOf(a.getCodMatricula()));
                txtPagNomeAluno.setText(a.getNome());
                txtPagValorPlano.setText(String.valueOf(super.valorPlano(a.getPlano())));
                txtPagPlano.setText(a.getPlano());

                //Abrir detalhes do aluno
                if((evt.getClickCount() == 2) && !(row.isEmpty())){
                    try {
                        Aluno rowData = row.getItem();

                        System.out.println("Código da avaliação física " + rowData.getCodAvFisica());

                        callAlunoDetails(rowData, evt);
                    } catch (IOException e) {
                        Blur.logLabel(lblClientesCadastrados, "Erro ao carregar dados do aluno.");
                        e.printStackTrace();
                    }
                }


            });
                    return row;
                }); //Mudar Itens do field consulta de pagamento


        //mostrar pagamentos pendentes
        try {
            alunoDAO.updatePagamentos();
            loadTablePagPendentes();
            loadTableAlunos();
        } catch (SQLException e) {
            e.printStackTrace();
            Blur.logLabel(lblUniversalLogs, "Erro ao alterar situação de pagamento.");
            System.err.println("Erro ao alterar pagamentos" + e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            System.err.println("Erro ao converter datas na consulta de pagamentos pendentes" + e.getMessage());
        }
    }

    /**
     * Altera os campos de consulta da aba Clientes
     * */
    public void showDadosAluno(Aluno a){
        txtNumMatAluno.setText(String.valueOf(a.getCodMatricula()));
        txtNomeAluno.setText(a.getNome());
        txtCpfAluno.setText(a.getCpf());
        txtRgAluno.setText(a.getRg());
        txtDtNascAluno.setText(a.getDtNasc());
        cbPlanoAluno.setValue(a.getPlano());
    }

    @FXML
    public void registrarPagamentoAluno() {
        try {
            alunoDAO.realizarPagamento(tablePagamentoAlunos.getSelectionModel()
                    .getSelectedItem().getCodMatricula());
            loadTablePagPendentes();
            Helpers.clearTextFields(txtPagNumMatricula,txtPagNomeAluno,txtPagValorPlano,txtPagPlano);
            loadTableAlunos();
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
            Blur.logLabel(lblUniversalLogs, "Erro ao realizar pagamento");
        }
    }

    @FXML
    private void salvarAluno(Event event) {
        if (!(txtNomeCliente.getText().equals(""))
                && !(txtRGcliente.getText().equals("")
                && !(dtNascCliente.getEditor().getText().equals(""))
                &&  (Helpers.verifyDate(dtNascCliente.getEditor().getText())))
                &&  (cbBoxPlanoCliente.getSelectionModel().getSelectedIndex() != -1 )
                &&  (cbBoxDiaDePagamento.getSelectionModel().getSelectedIndex() != -1 )) {

            lblClientesCadastrados.setText(String.valueOf(alunoDAO.getNumAlunosCadastrados()));
            aluno.setPlano(cbBoxPlanoCliente.getSelectionModel().getSelectedItem());
            aluno.setDiaPagamento(Integer.parseInt(cbBoxDiaDePagamento.getValue()));
            aluno.setNome(txtNomeCliente.getText());
            aluno.setRg(txtRGcliente.getText());
            aluno.setCpf(txtCPFcliente.getText());
            aluno.setSexo(radioSexoMasculino.isSelected() ? "M" : "F");
            aluno.setDtNasc(dtNascCliente.getEditor().getText());
            aluno.setPagamento(!(ckBoxPrimeiroPagamento.isSelected()) ? "Pendente" : "Realizado");

            //Inserção no dataBase
            try {
                alunoDAO.insertDB(aluno);

                //seta pagamento como true se ckBox estiver selecionado
                if (ckBoxPrimeiroPagamento.isSelected())
                    alunoDAO.realizarPagamento(aluno.getCodMatricula());

                //numeros de alunos na aba home
                tableAluno.setItems(FXCollections.observableArrayList(alunoDAO.consultDb()));
                lblClientesCadastrados.setText(String.valueOf(alunoDAO.getNumAlunosCadastrados()));

                //limpar campos
                Helpers.clearTextFields(txtNomeCliente,txtCPFcliente,txtRGcliente,
                        txtValorCarteirinha, txtValorMatricula, txtValorMensalidade);
                Helpers.clearDatePicker(dtNascCliente);
                cbBoxPlanoCliente.getSelectionModel().select(-1);
                tabPaneClientes.getSelectionModel().select(0);

                //log
                Blur.logLabel(lblUniversalLogs, "Cliente cadastrado com sucesso!");

                //atualizar pagamentos pendentes
                loadTablePagPendentes();
            } catch (Exception e) {
                //log
                Blur.logLabel(lblUniversalLogs, "Erro ao cadastrar" );
                e.printStackTrace();
            }

        } else {
            //mostrar popup de erro genérico.
           callUniversalErrorPopup(event);
        }
    }

    @FXML
    private void clearFieldsCliente() {
        Helpers.clearTextFields(txtNomeCliente,txtCPFcliente,txtRGcliente,
                txtValorCarteirinha, txtValorMatricula, txtValorMensalidade);
        Helpers.clearDatePicker(dtNascCliente);
        cbBoxPlanoCliente.getSelectionModel().select(-1);
        cbBoxDiaDePagamento.getSelectionModel().select(-1);
    }

    @FXML
    public void cadastrarAvFisica(Event event){

        Parent parent = ((Node) event.getSource()).getParent().getParent().getParent().getParent().getParent()
                .getParent().getParent().getParent();

        Blur.blurParent(parent);

        PopUpCadAvFisicaController cadAvFisicaController = new PopUpCadAvFisicaController(getSelectedAluno(), this);

        Stage popUpCadAvFisica = new Stage();

        popUpCadAvFisica.initModality(Modality.APPLICATION_MODAL);

        popUpCadAvFisica.initStyle(StageStyle.TRANSPARENT);

        Scene scene;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/zarsystem/view/popup/PopUpCadastroAvF.fxml"));

            loader.setController(cadAvFisicaController);

            scene = new Scene(loader.load());
            scene.setFill(null);
            popUpCadAvFisica.setScene(scene);
            popUpCadAvFisica.show();
        } catch (IOException e) {
            Blur.unblurParent(parent);
            Blur.logLabel(lblUniversalLogs, "Erro ao abrir cadastro de av. física.");
            e.printStackTrace();
        }

        popUpCadAvFisica.setOnHiding(evt -> Blur.unblurParent(parent));
    }

    private void callAlunoDetails(Aluno aluno, Event evt) throws IOException{

        //Borrar fundo
        Parent parent = ((Node) evt.getSource()).getParent().getParent().getParent().getParent().getParent()
                .getParent().getParent().getParent().getParent().getParent().getParent(); // tatatatatataravô
        Blur.blurParent(parent);

        //Chamar detalhes
        Stage popUp = new Stage();
        popUp.initModality(Modality.APPLICATION_MODAL);
        popUp.initStyle(StageStyle.TRANSPARENT);

        PopUpAlunosController ppAlunosController = new PopUpAlunosController(aluno, this);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/zarsystem/view/popup/PopUpAlunos.fxml"));
        loader.setController(ppAlunosController);

        Scene scene = new Scene(loader.load());
        scene.setFill(null);
        popUp.setScene(scene);
        popUp.show();

        popUp.setOnHiding(event -> Blur.unblurParent(parent));
    }

    /**Editar dados do aluno*/
    public void updateAluno(Event event){
        Parent parent = ((Node) event.getSource()).getParent().getParent().getParent().getParent().getParent()
                .getParent().getParent().getParent();

        Blur.blurParent(parent);
        Stage popUpEdtAluno = new Stage();

        try {
            PopUpAlunosChangeCadastroController changeCadastroController = new PopUpAlunosChangeCadastroController(getSelectedAluno(), this);

            popUpEdtAluno.initModality(Modality.APPLICATION_MODAL);

            popUpEdtAluno.initStyle(StageStyle.TRANSPARENT);

            FXMLLoader loader;

            loader = new FXMLLoader(getClass().getResource("/zarsystem/view/popup/PopUpChangeCadastroAluno.fxml"));
            loader.setController(changeCadastroController);

            Scene scene = new Scene(loader.load());
            scene.setFill(null);
            popUpEdtAluno.setScene(scene);
            popUpEdtAluno.show();
        } catch (Exception e) {
            Blur.logLabel(lblUniversalLogs, "Erro ao abrir popup.");
            Blur.unblurParent(parent);
            e.printStackTrace();
        }


        popUpEdtAluno.setOnHiding(evt -> Blur.unblurParent(parent));
    }

    @FXML
    protected void excluirItemAluno(Event event){
        Parent parent = ((Node) event.getSource()).getParent().getParent().getParent().getParent().getParent()
                .getParent().getParent().getParent();

        Blur.blurParent(parent);

        Stage popUp = new Stage();
        try {
            popUp.initModality(Modality.APPLICATION_MODAL);
            popUp.initStyle(StageStyle.TRANSPARENT);

            PopUpConfirmDeleteAlunoController confirmDeleteAlunoController = new PopUpConfirmDeleteAlunoController(
                    tableAluno.getSelectionModel().getSelectedItem(), alunoDAO, tableAluno, this);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/zarsystem/view/popup/PopUpConfirmDeleteAluno.fxml"));
            loader.setController(confirmDeleteAlunoController);

            Scene scene = new Scene(loader.load());
            scene.setFill(null);
            popUp.setScene(scene);
            popUp.show();
            popUp.setOnHiding(evt -> Blur.unblurParent(parent));
        } catch (IOException e) {
            Blur.unblurParent(parent);
            e.printStackTrace();
        }
    }

    public void consultAlunos(KeyEvent event){
        TextField field = (TextField) event.getSource();

        String column = ""; //culuna da tabela, nome, rg, etc
        String dados = field.getText() + event.getCharacter();

        System.out.println("Pesquisar por: " + field.getText());

        if(field.getId().equals("txtNumMatAluno")){
            column = "cod_matricula";
        }
        else if(field.getId().equals("txtNomeAluno")){
            column = "nome";
        }
        else if(field.getId().equals("txtCpfAluno")){
            column = "cpf";
        }
        else if(field.getId().equals("txtRgAluno")){
            column = "rg";
        }
        else {
            System.err.println("Erro na consulta, consultaAluno");
        }


        if((".-abcdefghijklmnopqrstuvwyxzABCDEFGHIJKLMNOPQRSTUVWYXZáéíóúÁÉÍÓÚ1234567890 ".contains(event.getCharacter()))){
            //System.out.println("SELECT * FROM alunos WHERE nome = "+field.getText() + event.getCharacter()+" "+field.getId());

            List<Aluno> alunos = alunoDAO.searchAluno(column, dados);

            tableAluno.setItems(FXCollections.observableArrayList(alunos));
        }
        else {
            dados = field.getText();

            System.out.println(event.getCode());
            System.out.println("O texto apagado fica: "+field.getText());

            List<Aluno> alunos = alunoDAO.searchAluno(column, dados);

            tableAluno.setItems(FXCollections.observableArrayList(alunos));
        }
    }

    /**
     * Carrega dados da tabela alunos, também atualiza o número de alunos na aba home
     * */
    public void loadTableAlunos(){
        System.out.print("Carregando tabela de alunos...");
        try {
            /*numeros de alunos*/
            lblClientesCadastrados.setText(String.valueOf(alunoDAO.getNumAlunosCadastrados()));
        /*consulta ao db para preencher a tabela*/
            tableAluno.setItems(FXCollections.observableArrayList(alunoDAO.consultDb()));
            System.out.println(" Ok.");
        } catch (Exception e) {
            e.printStackTrace();
            Blur.logLabel(lblUniversalLogs, "Erro ao consultar banco de dados.");
            System.err.println("Erro ao cadastrar ou consultar banco de dados");
        }
    }

    /**
     * Preence a tabela de pagamentos pendentes
     * */
    public void loadTablePagPendentes() throws ParseException, SQLException{
        System.out.println("Carregando tabela de pagamentos pendentes.");
        tablePagamentoAlunos.setItems(FXCollections.observableArrayList(alunoDAO.consultPagamentosPendentes()));
    }

    /*-********************************************FUNCIONÁRIOS********************************************************/
    public FuncionarioDAO funcionarioDAO = new FuncionarioDAO(this);

    public TextField txtCstFormacaoFunc;
    public TextField txtCadNomeFunc;
    public TextField txtCadCpfFunc;
    public TextField txtCadRgFunc;
    public TextField txtCadSalarioFunc;
    public TextField txtCadObjetivoFunc;
    public TextField txtCadFormacaoFunc;
    public TableView<Funcionario> tvCstFuncionarios;
    public TextField txtCstObjetivoFunc;
    public TextField txtCstNomeFunc;
    public TextField txtCstRgFunc;
    public TextField txtFuncCstCodFunc;
    public TextField txtCstCpfFunc;
    public RadioButton radioFuncMasc;
    public Button btnExluirFuncionario;
    public Button btnLimparFuncionarios;
    public Button btnFuncCstEditar;

    /**
     * Código do initialize
     * */
    private void initsFuncionario(){

        //Máscaras
        Helpers.maskRg(txtCadRgFunc);
        Helpers.maskCpf(txtCadCpfFunc);
        Helpers.maskRg(txtCstRgFunc);
        Helpers.maskCpf(txtCstCpfFunc);

        /*Desabilitar botões que precisam de seleção*/
        btnExluirFuncionario.disableProperty().bind(tvCstFuncionarios.getSelectionModel().selectedItemProperty().isNull());
        btnLimparFuncionarios.disableProperty().bind(tvCstFuncionarios.getSelectionModel().selectedItemProperty().isNull());
        btnFuncCstEditar.disableProperty().bind(tvCstFuncionarios.getSelectionModel().selectedItemProperty().isNull());

        /*pegar clicks nas linhas da tabela*/
        tvCstFuncionarios.setRowFactory(tv -> {

            TableRow<Funcionario> row = new TableRow<>();

            row.setOnMouseClicked(evt -> {

                Funcionario funcionario = tvCstFuncionarios.getSelectionModel().getSelectedItem();

                super.currentFuncionario = funcionario;

                showDadosFuncionario(funcionario);
            });
            return row;
        }); //Mudar Itens do field consulta

        //consultar database
        loadTableFuncionarios();
    }

    /**
     * Altera os campos da aba de consulta
     * */
    public void showDadosFuncionario(Funcionario funcionario){
        txtFuncCstCodFunc.setText(String.valueOf(funcionario.getCodFuncionario()));
        txtCstNomeFunc.setText(funcionario.getNome());
        txtCstCpfFunc.setText(funcionario.getCpf());
        txtCstRgFunc.setText(funcionario.getRg());
        txtCstObjetivoFunc.setText(funcionario.getObjetivo());
        txtCstFormacaoFunc.setText(funcionario.getFormacao());
    }

    /**
     * Carrega dados da tabela alunos, também atualiza o número de alunos na aba home
     * */
    public void loadTableFuncionarios(){
        System.out.print("Carregando tabela de funcionários...");
        try {
            /*numero de funcionários*/
            lblFuncionariosCadastrados.setText(String.valueOf(funcionarioDAO.consultDb().size()));
            //consulta ao db para preencher a tabela de funcionários
            tvCstFuncionarios.setItems(FXCollections.observableArrayList(funcionarioDAO.consultDb()));
            System.out.println(" Ok.");
        } catch (Exception e) {
            e.printStackTrace();
            Blur.logLabel(lblUniversalLogs, "Erro ao consultar funcionários.");
            System.err.println("Erro ao consultar funcionários no banco de dados;");
        }
    }


    /**
     * Salvar cadastro de um novo funcionário
     * */
    public void saveCadFuncionario(ActionEvent event) {
        System.out.println("Salvar novo funcionário");

        Funcionario funcionario = new Funcionario();

        try {
            if (!(txtCadNomeFunc.getText().equals("")) && !(txtCadRgFunc.getText().equals(""))) {

                funcionario.setNome(txtCadNomeFunc.getText());
                funcionario.setObjetivo(txtCadObjetivoFunc.getText());
                funcionario.setFormacao(txtCadFormacaoFunc.getText());
                funcionario.setRg(txtCadRgFunc.getText());
                funcionario.setCpf(txtCadCpfFunc.getText());
                funcionario.setSexo((radioFuncMasc.isSelected()) ? "M" : "F");
                funcionario.setSalario(Double.parseDouble(txtCadSalarioFunc.getText().replace(',','.')));

                funcionarioDAO.insertDb(funcionario);

                Blur.logLabel(lblUniversalLogs, "Funcionário cadastrado com sucesso!");

                Helpers.clearTextFields(txtCadNomeFunc,txtCadFormacaoFunc,txtCadObjetivoFunc,
                        txtCadSalarioFunc,txtCadRgFunc,txtCadCpfFunc);

                //voltar ao tab de consulta
                tabPaneFuncionarios.getSelectionModel().select(0);

                //consultar database com novo funcionário cadastrado
                loadTableFuncionarios();
            } else {
                //mostrar popup de erro genérico.
                callUniversalErrorPopup(event);
            }
        } catch (NumberFormatException e) {
            Blur.logLabel(lblUniversalLogs, "Erro ao validar dados.");
            e.printStackTrace();
        } catch (SQLException e) {
            Blur.logLabel(lblUniversalLogs, "Erro ao cadastrar funcionário");
            e.printStackTrace();
        }
    }

    /**
     * Cancela cadastro de um funcionário e volta a aba de consulta de funcionário
     * */
    public void btnCancelCadFuncionario(ActionEvent event) {
        System.out.println("Cancelar cadastro de funcionário");

        Helpers.clearTextFields(txtCadNomeFunc,txtCadFormacaoFunc,txtCadObjetivoFunc,
                txtCadSalarioFunc,txtCadRgFunc,txtCadCpfFunc);

        //voltar ao tab de consulta
        tabPaneFuncionarios.getSelectionModel().select(0);
    }

    public void limparCstFuncionarios() {
        Helpers.clearTextFields(txtFuncCstCodFunc, txtCstNomeFunc,txtCstFormacaoFunc,txtCstObjetivoFunc,
                txtCstRgFunc,txtCstCpfFunc);
    }

    /**
     * Chama um popUp de confirmação de exclusão de funcionário
     * */
    public void excluirFuncionario(Event event) {
        System.out.println("Excluir funcionário");

        Parent parent = ((Node) event.getSource()).getParent().getParent().getParent().getParent().getParent()
                .getParent().getParent().getParent();

        Blur.blurParent(parent);

        Stage popUp = new Stage();
        try {
            popUp.initModality(Modality.APPLICATION_MODAL);
            popUp.initStyle(StageStyle.TRANSPARENT);

            PopUpConfirmDeleteFuncionarioController confirmDeleteFuncionarioController =
                    new PopUpConfirmDeleteFuncionarioController(super.currentFuncionario,funcionarioDAO,tvCstFuncionarios,this);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/zarsystem/view/popup/PopUpConfirmDeleteFuncionario.fxml"));
            loader.setController(confirmDeleteFuncionarioController);

            Scene scene = new Scene(loader.load());
            scene.setFill(null);
            popUp.setScene(scene);
            popUp.show();
            popUp.setOnHiding(evt -> Blur.unblurParent(parent));
        } catch (IOException e) {
            Blur.unblurParent(parent);
            e.printStackTrace();
        }
    }

    public void updateFuncionario(ActionEvent actionEvent) {
        Parent parent = ((Node) actionEvent.getSource()).getParent().getParent().getParent().getParent().getParent()
                .getParent().getParent().getParent();

        Blur.blurParent(parent);
        Stage popUpEdt = new Stage();

        try {
            ChangeCadastroFuncionariosController changeCadastroController = new ChangeCadastroFuncionariosController(super.currentFuncionario, this);

            popUpEdt.initModality(Modality.APPLICATION_MODAL);

            popUpEdt.initStyle(StageStyle.TRANSPARENT);

            FXMLLoader loader;

            loader = new FXMLLoader(getClass().getResource("/zarsystem/view/popup/PopUpChangeCadastroFuncionarios.fxml"));
            loader.setController(changeCadastroController);

            Scene scene = new Scene(loader.load());
            scene.setFill(null);
            popUpEdt.setScene(scene);
            popUpEdt.show();
        } catch (Exception e) {
            Blur.logLabel(lblUniversalLogs, "Erro ao abrir popup.");
            Blur.unblurParent(parent);
            e.printStackTrace();
        }


        popUpEdt.setOnHiding(evt -> Blur.unblurParent(parent));
    }
    /*=******************************************PRODUTOS**************************************************************/

    public ProdutoDao produtoDao = new ProdutoDao(this);

    @FXML private Button btnCancelarCadastroProdutos;
    @FXML private TextField txtCstDescricaoProdutos;
    @FXML private TextField txtCstCodProdutos;
    @FXML private ComboBox<String> cbCstTipoProdutos;
    @FXML private TextField txtCstQtdProdutos;
    @FXML private TableView<Produto> tvCstProdutos;
    @FXML private TableView<Produto> tvProdutosCarrinho;
    @FXML private TextField txtCarrinhoValorProduto;
    @FXML private TextField txtCarrinhoDescricao;
    @FXML private TextField txtCarrinhoValorTotal;
    @FXML private TextField txtCarrinhoCodProduto;
    @FXML private TextField txtCarrinhoQdt;
    @FXML private TextField txtCarrinhoTipoProduto;
    @FXML private TableView<Produto> tvHistoricoProdutos;
    @FXML private TextField txtCadNomeProdutos;
    @FXML private TextField txtCadQtdProdutos;
    @FXML private TextField txtCadDescriçãoProdutos;
    @FXML private TextField txtCadValorProdutos;
    @FXML private TextField txtCadValorRevendaProdutos;
    @FXML private TextField txtCadLucroProdutos;
    @FXML private ComboBox<String> cbCadTipoProduto;

    //Historico
    public Label lblHistoricoVendasEfetuadas;
    public Label lblHistoricoEstoqueRegistrado;
    public Label lblHistoricoValorGasto;
    public Label lblHistoricoValorArrecadado;
    public Label lblHistoricoLucroFinal;

    //combo box tipo do produto
    public ObservableList<String> cbListTipoProduto = FXCollections.observableArrayList(
            "Vestível", "Consumível"
    );

    //Lista com os produtos adicionados ao carrinho
    public List<Produto> listCarrinho = new ArrayList<>();

    private void initsProdutos(){
        //itens do comboBox de cadastro e consulta de produto
        cbCadTipoProduto.setItems(cbListTipoProduto);
        cbCstTipoProdutos.setItems(cbListTipoProduto);

        //carregar produtos já cadastrados
        loadTableCstProdutos();

        //mostrar a quantidade de produtos cadastrados
        lblProdutosCadastrados.setText(String.valueOf(tvCstProdutos.getItems().size()));


        btnCancelarCadastroProdutos.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            openConsultaProdutos();
            Helpers.clearTextFields(txtCadQtdProdutos, txtCadNomeProdutos, txtCadDescriçãoProdutos, txtCadValorProdutos,
                    txtCadValorRevendaProdutos, txtCadLucroProdutos);
            cbCadTipoProduto.getSelectionModel().select(-1);
        });

        //Calcular lucro previsto
        EventHandler<KeyEvent> eventHandlerLucro = new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                String txtValor = txtCadValorProdutos.getText();
                String txtValorRevenda = txtCadValorRevendaProdutos.getText();
                String txtQtdProdutos = txtCadQtdProdutos.getText();

                //Colocar ultima letra digitada antes de fazer as contas
                TextField textField = (TextField) event.getSource();


                    if (textField.getId().equals("txtCadValorProdutos")){
                        txtValor += event.getCharacter();
                        System.out.println("txtvalor: " + txtValor);
                    }
                    else if (textField.getId().equals("txtCadValorRevendaProdutos")){
                        txtValorRevenda += event.getCharacter();
                        System.out.println("txtvalorrevenda: " + txtValorRevenda);
                    }
                    else {
                        txtQtdProdutos += event.getCharacter();
                        System.out.println("txtqtd: " + txtQtdProdutos);
                    }

                try {
                    txtCadLucroProdutos.setText(String.valueOf(Helpers.lucroEstimado(
                            Double.parseDouble(txtValor),
                            Double.parseDouble(txtValorRevenda),
                            Integer.parseInt(txtQtdProdutos)
                    )));
                } catch (NumberFormatException e) {
                    System.out.println(e.getMessage());
                }
            }
        };
        txtCadValorProdutos.addEventHandler(KeyEvent.KEY_TYPED, eventHandlerLucro);
        txtCadValorRevendaProdutos.addEventHandler(KeyEvent.KEY_TYPED, eventHandlerLucro);
        txtCadQtdProdutos.addEventHandler(KeyEvent.KEY_TYPED, eventHandlerLucro);

        //pegar clicks nas linhas da tabela de produtos
        tvCstProdutos.setRowFactory(tv -> {

            TableRow<Produto> row = new TableRow<>();

            row.setOnMouseClicked(evt -> {

                Produto p = tvCstProdutos.getSelectionModel().getSelectedItem();

                txtCstCodProdutos.setText(String.valueOf(p.getCodProduto()));
                txtCstDescricaoProdutos.setText(p.getDescricao());
                cbCstTipoProdutos.setValue(p.getTipo());
                txtCstQtdProdutos.setText(String.valueOf(p.getQuantidade()));
            });
            return row;
        }); //Mudar Itens do field consulta

        // pegar clicks nas linhas da tabela de carrinho
        tvProdutosCarrinho.setRowFactory(tv -> {

            TableRow<Produto> row = new TableRow<>();

            row.setOnMouseClicked(evt -> {

                Produto p = tvCstProdutos.getSelectionModel().getSelectedItem();

                txtCarrinhoCodProduto.setText(String.valueOf(p.getCodProduto()));
                txtCarrinhoDescricao.setText(p.getDescricao());
                txtCarrinhoTipoProduto.setText(p.getTipo());
                txtCarrinhoValorProduto.setText(String.valueOf(p.getValorRevenda()));
                txtCarrinhoQdt.setText(String.valueOf(p.getQuantidade()));
            });
            return row;
        }); //Mudar Itens do field consulta
    }

    /**
     * Consulta banco de dados e carrega tabela e muda label de produtos cadastrados na aba home
     * */
    public void loadTableCstProdutos(){
        try {
            System.out.print("Carregando tabela de produtos... ");
            tvCstProdutos.setItems(FXCollections.observableArrayList(produtoDao.consultDb()));

            //Carregar a quantidade de funcionários cadastrados pela quantidade de linhas da tabela
            lblProdutosCadastrados.setText(String.valueOf(tvCstProdutos.getItems().size()));

            System.out.println("Ok.");
        } catch (SQLException e) {
            Blur.logLabel(lblUniversalLogs, "Erro ao consultar produtos...");
            System.out.println("Erro");
            e.printStackTrace();
        }
    }

    /**
     * Carrega tabela da aba carrinho
     * */
    public void loadTableCarrinho(){
            System.out.print("Mudando carrinho... ");
            tvProdutosCarrinho.setItems(FXCollections.observableArrayList(listCarrinho));
            System.out.println("Ok.");
    }

    /**
     * Carrega a classe de deomínio de produtos com os valores dos txtFields de cadastro
     * @return o novo produto cadastrado
     * */
    private Produto getNewProduto(){
        Produto produto = new Produto();
        try {
            produto.setNome(txtCadNomeProdutos.getText());
            produto.setDescricao(txtCadDescriçãoProdutos.getText());
            produto.setTipo(cbCadTipoProduto.getValue());
            produto.setValor(Double.parseDouble(txtCadValorProdutos.getText()));
            produto.setQuantidade(Integer.parseInt(txtCadQtdProdutos.getText()));
            produto.setValorRevenda(Double.parseDouble(txtCadValorRevendaProdutos.getText()));

            return produto;
        } catch (NumberFormatException e) {
            Blur.logLabel(lblUniversalLogs, "Formato de número inválido");
            e.printStackTrace();
            return null;
        }
        catch (Exception e){
            Blur.logLabel(lblUniversalLogs, "Erro ao validar cadastro");
            e.printStackTrace();
            return null;
        }
    }

    @FXML
    public void salvarNovoProduto(ActionEvent actionEvent) {
        System.out.println("Salvar produto");

        if(!txtCadNomeProdutos.getText().equals("") && !txtCadValorProdutos.getText().equals("")){
            try {
                produtoDao.insertDB(getNewProduto());
                loadTableCstProdutos();
                openConsultaProdutos();
            } catch (SQLException e) {
                callUniversalErrorPopup(actionEvent);
                Blur.logLabel(lblUniversalLogs, "Erro ao fazer cadastro");
                e.printStackTrace();
            }
        }else {
            callUniversalErrorPopup(actionEvent);
        }
    }

    @FXML
    public void finalizarVenda(ActionEvent actionEvent) {
        System.out.println("Finalizar Venda");

        //limpa o carrinho
        listCarrinho.clear();
        loadTableCarrinho();

        openConsultaProdutos();
    }

    @FXML
    public void limparConsultaProdutos(ActionEvent actionEvent) {
        Helpers.clearTextFields(txtCstCodProdutos, txtCstDescricaoProdutos, txtCstQtdProdutos);
        cbCstTipoProdutos.getSelectionModel().select(-1);
    }

    @FXML
    public void excluirProduto(Event event) {
        Parent parent = ((Node) event.getSource()).getParent().getParent().getParent().getParent().getParent()
                .getParent().getParent().getParent();

        Blur.blurParent(parent);

        Stage popUp = new Stage();
        try {
            popUp.initModality(Modality.APPLICATION_MODAL);
            popUp.initStyle(StageStyle.TRANSPARENT);

            ConfirmDeleteProdutoController confirmDeleteProdutoController = new ConfirmDeleteProdutoController(tvCstProdutos.getSelectionModel().getSelectedItem(), produtoDao,tvCstProdutos, this);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/zarsystem/view/popup/PopUpConfirmDeleteProduto.fxml"));
            loader.setController(confirmDeleteProdutoController);

            Scene scene = new Scene(loader.load());
            scene.setFill(null);
            popUp.setScene(scene);
            popUp.show();
            popUp.setOnHiding(evt -> Blur.unblurParent(parent));
        } catch (IOException e) {
            Blur.unblurParent(parent);
            e.printStackTrace();
        }
    }

    @FXML
    public void adicionarAoCarrinho(ActionEvent actionEvent) {
        System.out.print("Adicionar ao carrinho... ");

        Produto produto = tvCstProdutos.getSelectionModel().getSelectedItem();

        produto.setQuantidade(1);


        if (!listCarrinho.contains(produto)) {
            listCarrinho.add(produto);
        }else {
            for (Produto p: listCarrinho){
                if(p.getNome().equals(produto.getNome())){
                    System.out.println("joao");
                    int oldQtd = p.getQuantidade();
                    p.setQuantidade(oldQtd + 1);
                    listCarrinho.remove(produto);
                    listCarrinho.add(p);
                }
            }
        }

        loadTableCarrinho();
        calcularVenda();
        System.out.println("Ok.");
    }

    @FXML
    public void cancelarCompra(ActionEvent actionEvent) {
        Helpers.clearTextFields(txtCarrinhoValorProduto, txtCarrinhoDescricao, txtCarrinhoValorTotal, txtCarrinhoCodProduto,
                txtCarrinhoQdt, txtCarrinhoTipoProduto);

        openConsultaProdutos();

        //limpa o carrinho
        listCarrinho.clear();
        loadTableCarrinho();
    }

    /**
     * Mudas as quantidades na tabela carrinho e os preços na consulta
     * */
    private void calcularVenda(){
        double valorTotal = 0;
        for(Produto p : tvProdutosCarrinho.getItems()){
            valorTotal += p.getValorRevenda();

        }

        System.out.println("O valor total até agora é: " + valorTotal);

        txtCarrinhoValorTotal.setText(String.valueOf(valorTotal));
    }

    public void limparHistorico(ActionEvent actionEvent) {

    }
}