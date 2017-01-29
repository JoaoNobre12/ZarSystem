package zarsystem.controller.popup;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import zarsystem.controller.Controller;
import zarsystem.model.dao.LoginDao;
import zarsystem.model.domain.Login;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Controlador do popup de alreração de senha
 * Created by joaov on 11/12/2016.
 */
public class PopUpChangePassController extends Controller{
    private LoginDao loginDao = new LoginDao();
    @FXML private TextField txtUser;
    @FXML private Button btnOkay;
    @FXML private TextField txtKey;
    @FXML private PasswordField passRepeatNewPass;
    @FXML private PasswordField passNewPass;

    @FXML
    public void initialize() {

        txtKey.textProperty().addListener((ov, oldValue, newValue) -> {
            txtKey.setText(newValue.toUpperCase());

            if (newValue.equalsIgnoreCase("QWERTY987123BJ")){
                passNewPass.setDisable(false);
                passRepeatNewPass.setDisable(false);
            }else {
                passNewPass.setDisable(true);
                passRepeatNewPass.setDisable(true);
            }
        });

        passNewPass.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 5)
                btnOkay.disableProperty().bind(passRepeatNewPass.textProperty().isNotEqualTo(passNewPass.textProperty()));
            else
                btnOkay.disableProperty().unbind();
        });
    }

    public void alterarSenha(ActionEvent actionEvent) throws IOException, SQLException{
        Login login = new Login();
        login.setUser(txtUser.getText());
        login.setPass(passRepeatNewPass.getText());

        loginDao.updateLoginUser(login);
        closePopUp(actionEvent);
    }
}
