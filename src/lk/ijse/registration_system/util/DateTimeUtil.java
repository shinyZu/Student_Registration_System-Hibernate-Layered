package lk.ijse.registration_system.util;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateTimeUtil {
    public Label lblDay;
    public Label lblDate;
    public Label lblTime;

    public void loadDateAndTime() {
        Date date = new Date();

        //load Day
        DateFormat formatter = new SimpleDateFormat("EEEE", Locale.forLanguageTag("en"));
        lblDay.setText(formatter.format(date));

        // load Date
        SimpleDateFormat f = new SimpleDateFormat("MMMM-dd");
        lblDate.setText(f.format(date));

        // load Time
        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {

            SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("hh:mm:ss a");

            Calendar cal = new GregorianCalendar();
            Date date1 = cal.getTime();
            lblTime.setText(simpleTimeFormat.format(date1));
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }
}
