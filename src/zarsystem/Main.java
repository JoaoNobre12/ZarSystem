package zarsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import zarsystem.controller.Controller;
import zarsystem.model.Inits;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Pane root = FXMLLoader.load(getClass().getResource("/zarsystem/view/Login.fxml"));

        Scene scene = new Scene(root);
        scene.setFill(null);
        primaryStage.getIcons().add(new Image("/zarsystem/view/img/ico.png"));
        primaryStage.setTitle("ZarSystem");
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();

        Controller.HOST_SERVICES = getHostServices();
    }

    public static void main(String[] args) {
        Inits inits = new Inits();
        inits.start();

        launch(args);

    }

}