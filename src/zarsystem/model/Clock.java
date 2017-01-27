package zarsystem.model;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Clock{
	
	 public static void bindToTime(Label lbl) {

         Task task = new Task() {
             @Override
             protected Timeline call() throws Exception {
                 Timeline timeline = new Timeline(
                         new KeyFrame(Duration.seconds(0),
                                 actionEvent -> {
                                     Calendar time = Calendar.getInstance();

                                     String hourString = pad(2, ' ', (time.get(Calendar.HOUR_OF_DAY) < 10) ?
                                             ("0" + time.get(Calendar.HOUR_OF_DAY))
                                             : String.valueOf(time.get(Calendar.HOUR_OF_DAY)) + "");
                                     String minuteString = pad(2, '0', time.get(Calendar.MINUTE) + "");
                                     String secondString = pad(2, '0', time.get(Calendar.SECOND) + "");
                                     Format formatDate	= new SimpleDateFormat("dd/MM/yyyy");
                                     String date = formatDate.format(new Date());

                                     lbl.setText(date + "         " + hourString + ":" + minuteString + ":" + secondString + " ");
                                 }
                         ),
                         new KeyFrame(Duration.seconds(1))
                 );
                 timeline.setCycleCount(Animation.INDEFINITE);
                 timeline.play();

                 return timeline;
             }
         };

         new Thread(task).start();
		  }
	
	private static String pad(int fieldWidth, char padChar, String s) {
	    StringBuilder sb = new StringBuilder();
	    for (int i = s.length(); i < fieldWidth; i++) {
	      sb.append(padChar);
	    }
	    sb.append(s);

	    return sb.toString();
	  }
}
