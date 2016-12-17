package zarsystem.view.blur;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.util.Duration;

/**
 * Efeito BLur
 * Created by joão on 24/10/2016.
 */
public class Blur {

    /**
     * Borra com transição
     * */
    public static void blurParent(Parent parent){

        Timeline blurTime = new Timeline(
                new KeyFrame(Duration.ZERO, event ->  parent.setEffect(new GaussianBlur(6))),
                new KeyFrame(Duration.seconds(0.100),  event -> parent.setEffect(new GaussianBlur(8))),
                new KeyFrame(Duration.seconds(0.150),  event -> parent.setEffect(new GaussianBlur(9.5))),
                new KeyFrame(Duration.seconds(0.200),  event -> parent.setEffect(new GaussianBlur(10))),
                new KeyFrame(Duration.seconds(0.250),  event -> parent.setEffect(new GaussianBlur(10.7))),
                new KeyFrame(Duration.seconds(0.300),  event -> parent.setEffect(new GaussianBlur(11))),
                new KeyFrame(Duration.seconds(0.350),  event -> parent.setEffect(new GaussianBlur(11.35)))
        );

        blurTime.play();
    }

    /**
     * Borra com o valor especificado
     * */
    public static void blurParent(Parent parent, Double radius){
        parent.setEffect(new GaussianBlur(radius));
    }

    /**
     * Tira o blur
     * */
    public static void unblurParent(Parent parent){
        Timeline blurTime = new Timeline(
                new KeyFrame(Duration.ZERO, event ->  parent.setEffect(new GaussianBlur(10))),
                new KeyFrame(Duration.seconds(0.100),  event -> parent.setEffect(new GaussianBlur(8))),
                new KeyFrame(Duration.seconds(0.150),  event -> parent.setEffect(new GaussianBlur(5))),
                new KeyFrame(Duration.seconds(0.200),  event -> parent.setEffect(new GaussianBlur(2))),
                new KeyFrame(Duration.seconds(0.250),  event -> parent.setEffect(new GaussianBlur(0)))

        );

        blurTime.play();
    }

    public static void logLabel(Label label, String text){
        label.setText(text);

        System.out.println("log de: "+ text);

        Timeline blurTime = new Timeline(
                new KeyFrame(Duration.ZERO, event -> label.setOpacity(1)),
                new KeyFrame(Duration.seconds(4),  event ->label.setOpacity(0.95)),
                new KeyFrame(Duration.seconds(4.15),  event ->label.setOpacity(0.8)),
                new KeyFrame(Duration.seconds(4.2),  event ->label.setOpacity(0.65)),
                new KeyFrame(Duration.seconds(4.25),  event ->label.setOpacity(0.5)),
                new KeyFrame(Duration.seconds(4.3),  event ->label.setOpacity(0.3)),
                new KeyFrame(Duration.seconds(4.35),  event ->label.setOpacity(0.15)),
                new KeyFrame(Duration.seconds(4.4),  event ->label.setOpacity(0.1)),
                new KeyFrame(Duration.seconds(4.55),  event ->label.setOpacity(0)),
                new KeyFrame(Duration.seconds(4.55),  event ->label.setText(""))
        );
        blurTime.play();
    }
}
