package zarsystem.controller;

/**
 * Created by joao on 04/09/16.
 *  Controlador da tela de splash
 */

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import zarsystem.model.database.Database;

public class SplashController extends Controller {

    @FXML
    private Label lblLoading;
    @FXML
    private ImageView imgView;

    private Database database = new Database();

    @FXML
    public void initialize() {

        imgView.setOpacity(0.0);
		Timeline splashFade = new Timeline(
				new KeyFrame(Duration.ZERO,          evt -> imgView.setOpacity(0.0)),
				new KeyFrame(Duration.seconds(0.200),  evt -> imgView.setOpacity(0.1)),
				new KeyFrame(Duration.seconds(0.250),  evt -> imgView.setOpacity(0.35)),
				new KeyFrame(Duration.seconds(0.350), evt -> imgView.setOpacity(0.55)),
				new KeyFrame(Duration.seconds(0.400),  evt -> imgView.setOpacity(0.75)),
				new KeyFrame(Duration.seconds(0.500),  evt -> imgView.setOpacity(1))
				);
		splashFade.play();

        Timeline labels = new Timeline(
                new KeyFrame(Duration.ZERO, evt -> lblLoading
                        .setText("")),
                new KeyFrame(Duration.seconds(1), evt -> lblLoading
                        .setText("Carregando...")),
                new KeyFrame(Duration.seconds(1.5), evt -> lblLoading
                        .setText("Carregando estilos...")),
                new KeyFrame(Duration.seconds(2.5), evt -> lblLoading
                        .setText("Carregando estrutura de cena...")),
                new KeyFrame(Duration.seconds(3), evt -> lblLoading
                        .setText("Carregando arquivos...")),
                new KeyFrame(Duration.seconds(3.5), evt -> lblLoading
                        .setText("Conectando ao banco de dados...")),
                new KeyFrame(Duration.seconds(3.6), evt -> lblLoading
                        .setText("")),

                new KeyFrame(Duration.seconds(3.6), evt -> connectDatabase()),

                new KeyFrame(Duration.seconds(5), evt -> lblLoading
                        .setText("Carregando imagens...")), new KeyFrame(
                Duration.seconds(5),
                evt -> lblLoading.setText("Quase lá...")),
                new KeyFrame(Duration.seconds(6), evt -> lblLoading
                        .setText("Abrindo..."))
        );
        labels.play();

    }
}