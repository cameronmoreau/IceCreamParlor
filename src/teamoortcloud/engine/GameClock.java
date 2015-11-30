package teamoortcloud.engine;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Cameron on 11/30/15.
 */
public class GameClock {

    public interface GameClockListener {
        void crappyHourBegins();
        void shopClosed();
    }

    final int clockStart = 1;
    final int clockEnd = 11;
    final int intervalTime = 4;

    int currentHour, currentMinute;

    Timer timer;
    GameClockListener listener;

    //Start clock
    public GameClock() {
        currentHour = clockStart;
        currentMinute = 0;

        timer = new Timer();
    }

    public void setListener(GameClockListener listener) {
        this.listener = listener;
    }

    public void start() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                tickClock();

                if(currentHour == clockEnd) {
                    if(listener != null) listener.shopClosed();
                    this.cancel();
                }
            }
        };
        timer.schedule(task, 0, 1000);
    }

    public void stop() {
        timer.cancel();
    }

    private void tickClock() {
        currentMinute += intervalTime;
        if(currentMinute >= 60) {
            currentHour++;
            currentMinute = 0;
        }

        if(currentHour == 10 && currentMinute == 0 && listener != null) listener.crappyHourBegins();
    }

    public String getClockString() {
        return String.format("%d:%02d pm", currentHour, currentMinute);
    }
}
