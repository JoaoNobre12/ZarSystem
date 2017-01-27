package zarsystem.controller;

/**
 * Controlador da tela de Login
 * Created by joao on 04/09/16.
 */

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import zarsystem.controller.popup.PopUpChangePassController;
import zarsystem.controller.popup.PopUpRootLoginController;
import zarsystem.model.dao.Dao;
import zarsystem.model.domain.Login;
import zarsystem.view.blur.Blur;

import java.io.IOException;

public class LoginController extends Controller {

    Login login = new Login();

    @FXML
    private Button Fechar;
    @FXML
    private Button Minimize;
    @FXML
    private Button btnAtend;
    @FXML
    private Button btnRoot;
    @FXML
    private Button btnEntrar;
    @FXML
    private Button btnZar;
    @FXML
    private Button btnDora;
    @FXML
    private Hyperlink hplEsqueciSenha;
    @FXML
    private TextField txtLogin;
    @FXML
    private PasswordField txtPass;
    @FXML
    private Label lblLoginInvalido;

    @FXML
    private AnchorPane loginFrame;

    boolean zarBorder = false;
    boolean doraBorder = false;

    @FXML
    public void initialize() {


        super.dragWindow(loginFrame);

        txtLogin.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if(event.getCode().equals(KeyCode.ENTER)){
                try {
                    verifyLogin(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        txtPass.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if(event.getCode().equals(KeyCode.ENTER)){
                try {
                    verifyLogin(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        connectDatabase();
    }

    private void btnClicked(Button btn) {

        if (btn.idProperty().getValue().equals(btnZar.idProperty().getValue())
                && !zarBorder) {
            System.out.println("zar");

            btn.setStyle(btn.getStyle() + "-fx-background-color: transparent;"
                    + "-fx-border-color: rgba(255, 255, 255, 0.5);"
                    + "-fx-border-width: 1;" + "-fx-border-radius: 5;");

            btnDora.setStyle(btnDora.getStyle() + "-fx-border-width: 0;");



        }
        else if (btn.idProperty().getValue()
                .equals(btnDora.idProperty().getValue())
                && doraBorder == false) {
            System.out.println("dora");

            btn.setStyle(btn.getStyle() + "-fx-background-color: transparent;"
                    + "-fx-border-color: rgba(255, 255, 255, 0.5);"
                    + "-fx-border-width: 1;" + "-fx-border-radius: 5;");

            btnZar.setStyle(btnZar.getStyle() + "-fx-border-width: 0;");
        }
    }

    @FXML
    public void zarClicked() {
        user.setTipo("dono");

        btnClicked(btnZar);
        txtLogin.setText("Zar");
        txtPass.requestFocus();
    }

    @FXML
    public void doraClicked() {
        user.setTipo("dono");
        btnClicked(btnDora);
        txtLogin.setText("Dora");
        txtPass.requestFocus();
    }

    @FXML
    public void rootClicked(MouseEvent event) throws IOException {
        Parent parent = ((Node) event.getSource()).getParent().getParent().getParent();

        Blur.blurParent(parent);

        PopUpRootLoginController popUpRootLoginController = new PopUpRootLoginController();

        Stage dbStage = new Stage();

        dbStage.initModality(Modality.APPLICATION_MODAL);
        dbStage.initStyle(StageStyle.TRANSPARENT);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/zarsystem/view/popup/PopUpRootLogin.fxml"));

        loader.setController(popUpRootLoginController);

        Scene scene = new Scene(loader.load());
        scene.setFill(null);
        dbStage.setScene(scene);
        dbStage.show();

        dbStage.setOnHiding(evt -> Blur.unblurParent(parent));
    }

    @FXML
    public void atendClicked(Event event) throws IOException {
        user.setTipo("atendente");

        txtLogin.setText("atendente");
        txtPass.requestFocus();
    }

    @FXML
    public void verifyLogin(Event event) throws IOException {

        if (login.authenticate(txtLogin.getText(), txtPass.getText())) {
            System.out.println("Login feito com sucesso");
            callSplash(event);
        } else{
            System.out.println("Login falhou");
            Blur.logLabel(lblLoginInvalido,"Login ou senha inválido.");
        }
    }

    public void updatePass(ActionEvent event) {
        System.out.println("Alterar senha");
        Parent parent = ((Node) event.getSource()).getParent();

        try {
            Blur.blurParent(parent);

            PopUpChangePassController popUpChangePassController = new PopUpChangePassController();

            Stage dbStage = new Stage();

            dbStage.initModality(Modality.APPLICATION_MODAL);
            dbStage.initStyle(StageStyle.TRANSPARENT);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/zarsystem/view/popup/PopUpChangePass.fxml"));

            loader.setController(popUpChangePassController);

            Scene scene = new Scene(loader.load());
            scene.setFill(null);
            dbStage.setScene(scene);
            dbStage.show();

            dbStage.setOnHiding(evt -> Blur.unblurParent(parent));
        } catch (IOException e) {
            Blur.logLabel(lblLoginInvalido, "Erro ao carregar popup");
            Blur.unblurParent(parent);
            e.printStackTrace();
        }
    }
}