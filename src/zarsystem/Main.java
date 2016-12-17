package zarsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import zarsystem.model.Inits;
import zarsystem.model.database.CreateDatabase;
import zarsystem.model.database.Database;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Pane root = FXMLLoader.load(getClass().getResource("/zarsystem/view/Menu.fxml"));

        Scene scene = new Scene(root);
        scene.setFill(null);
        primaryStage.getIcons().add(new Image("/zarsystem/view/img/ico.png"));
        primaryStage.setTitle("ZarSystem");
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Inits inits = new Inits();
        inits.start();

        launch(args);
    }

}