package zarsystem.controller;

/** Classe abstrata, pai de todos os outros controllers
 *  Created by joao on 04/09/16.
 */

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import zarsystem.controller.popup.PopUpErroController;
import zarsystem.model.Helpers;
import zarsystem.model.dao.Dao;
import zarsystem.model.database.Database;
import zarsystem.model.domain.Aluno;
import zarsystem.model.domain.Funcionario;
import zarsystem.model.domain.User;
import zarsystem.view.blur.Blur;

import java.io.IOException;

abstract public class Controller {
    Helpers helpers = new Helpers();
    protected static User user = new User();

    private double xOffset = 0;
    private double yOffset = 0;

    public static HostServices HOST_SERVICES;

    private Stage loginStage;

    @FXML
    protected MenuBar mainMenuBar;

    protected Aluno currentAluno;

    protected Funcionario currentFuncionario;

    @FXML protected BorderPane mainMenu;

    @FXML
    public void maximize(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        StackPane stackPane = (StackPane) ((Node) event.getSource()).getParent().getParent().getParent()
                .getParent();

        if (!stage.isMaximized()) {
            mainMenu.setStyle("-fx-padding: 0 0 0 0;" +
                    "-fx-background-radius: 0;");
            stage.setMaximized(true);
            stackPane.setPadding(new Insets(0,0,0,0));

        } else{
            mainMenu.setStyle("-fx-padding: 0 5px 5px 5px;" +
                    "-fx-background-radius: 7.5px;");

            stage.setMaximized(false);
            stackPane.setPadding(new Insets(10,10,10,10));
        }

    }

    @FXML
    protected void minimize(MouseEvent event) { // minimiza
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    /**
     * Mostra o popup de sair, se sim fecha o software
     * */
    @FXML
        protected void close(Event event) throws IOException{

        Parent parent = null;

        //Sair pelo Xzinho
        try {
            parent = ((Node) event.getSource()).getParent().getParent().getParent();
        }
        //Sair pelo MenuBar
        catch (ClassCastException e) {

            parent = mainMenuBar.getParent().getParent();
        }
        //Tela de Login
        catch (Exception e) {
            parent = ((Node) event.getSource()).getParent();
        }finally {
            helpers.initPopUp("PopUpSair.fxml", parent);
        }

    }

    protected void dragWindow(Node frame) {

        //mover tela
        frame.setOnMousePressed(evt -> {
            System.out.println("Mouse Pressed");

            xOffset =  evt.getSceneX();
            yOffset =  evt.getSceneY();

            frame.getScene().setFill(null);
        });

        frame.setOnMouseDragged(evt -> {

            Stage stage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
            stage.setMaximized(false);

            if (frame.getId().equals("mainMenu")){
                frame.setStyle("-fx-padding: 0 5px 5px 5px;" +
                               "-fx-background-radius: 7.5px;");
            }else{
                frame.setStyle("-fx-padding: 0 5px 5px 5px;" +
                               "-fx-background-radius: 7.5px;" +
                               "-fx-background-color: transparent;");
            }

            frame.getScene().setFill(null);
            stage.setX(evt.getScreenX() - xOffset);
            stage.setY(evt.getScreenY() - yOffset);
        });
    }

    public void setLoginStage(Stage loginStage) {
        this.loginStage = loginStage;
    }

    protected void callSplash(Event event) throws IOException {
        //fechar conexão anterior
        closeDatabaseConnection();
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        setLoginStage(primaryStage);

        Pane splash = FXMLLoader.load(getClass().getResource("/zarsystem/view/Splash.fxml"));

        Scene sceneSplash = new Scene(splash);
        sceneSplash.setFill(null);

        Timeline beat = new Timeline(

                new KeyFrame(Duration.ZERO,       	evt -> primaryStage.setScene(sceneSplash)),
                new KeyFrame(Duration.seconds(0.5), evt -> System.out.println("Splash showed")),
                new KeyFrame(Duration.seconds(7),   evt -> System.out.println("Abrindo home"))

        );
        beat.play();

        beat.setOnFinished(event1 -> {
            try {
                callHome(loginStage);
            }catch (IOException e){
                e.printStackTrace();
            }
        });
    }

    protected void callHome(Stage stage) throws IOException{
        Pane home = FXMLLoader.load(getClass().getResource("/zarsystem/view/Menu.fxml"));
        Scene sceneHome = new Scene(home);
        sceneHome.setFill(null);
        stage.setScene(sceneHome);

        stage.toBack();

        PauseTransition pauseHome = new PauseTransition(Duration.seconds(0.4));
        pauseHome.setOnFinished(evt ->{
            stage.setMaximized(true);
            stage.toFront();
        });

        pauseHome.play();
        System.out.println("home chamada");
    }

    /** Retorna valor do plano (Musculação, Sertanejo) com base no item selecionado
     * @param int selectedIndex
     * @return double com o valor do plano
     * */
    public double valorPlano(int selectedIndex){
        double value;
        switch (selectedIndex){
            case 0: value = 50.00; break;
            case 1: value = 30.00; break;
            case 2: value = 30.00; break;
            case 3: value = 65.00; break;
            case 4: value = 75.00; break;
            case 5: value = 65.00; break;
            default: value = 0.0;
        }

        return value;
    }


    /** Retorna valor do plano (Musculação, Sertanejo) com base no item selecionado
     * @param int selectedIndex
     * @return double com o valor do plano
     * */
    public double valorPlano(String plano){
        double value;
        /*"Musculação + Aeróbico", "Sertanejo", "Muay Thai", "Musculação + Sertanejo", "Musculação + Sertanejo + Muay Thai",
                "Musculação + Muay Thai" >>>*/
        switch (plano){
            case "Musculação + Aeróbico": value = 50.00; break;
            case "Sertanejo": value = 30.00; break;
            case "Muay Thai": value = 30.00; break;
            case "Musculação + Sertanejo": value = 65.00; break;
            case "Musculação + Sertanejo + Muay Thai": value = 75.00; break;
            case "Musculação + Muay Thai": value = 65.00; break;
            default: value = 0.0;
        }

        return value;
    }

    /*REEFATOAR*/
    private Database database = new Database();

    protected void connectDatabase(){
        try {
            Dao.connection = database.createConnection();
        } catch (Exception e) {
            System.err.println("Erro ao concectar com o banco de dados...");
            e.printStackTrace();
        }

    }

    protected void closeDatabaseConnection(){
        try {
             database.closeConnection();
        } catch (Exception e) {
            System.err.println("Erro ao fechar conexão com o banco de dados...");
            e.printStackTrace();
        }

    }

    protected void callUniversalErrorPopup(Event event){
        try {
            Parent parent = ((Node) event.getSource()).getParent().getParent().getParent().getParent().getParent()
                    .getParent().getParent().getParent();

            Blur.blurParent(parent);

            PopUpErroController popUpErroController = new PopUpErroController("Erro. Tente novamente");

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

    /**
     * fechar popup
     * */
    @FXML
    protected void closePopUp(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        System.out.println("Fechar popup");
    }

    @FXML
    protected void exit() {
        System.out.println("Bye!");

        Platform.exit();
    }
}