package fi.tampere.rynzy.workouttracker;


import android.widget.TextView;

public class Timer {
    private int minutes;
    private int seconds;
    private int hours;
    private boolean running;

    public Timer() {
        this.minutes = 0;
        this.seconds = 0;
        this.hours = 0;
        running = false;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void startTimer() {

        Runnable runnable = () -> {
            running = true;

            while (running) {
                try {
                    Thread.sleep(1000);
                    increase();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        };

        if(!running) {


        Thread t = new Thread(runnable);
        t.start();
        }

    }

    public void increase() {
        this.seconds++;

        if (getSeconds() == 60) {
            this.minutes++;
            setSeconds(0);
        }

        if (getMinutes() == 60) {
            this.hours++;
            setMinutes(0);
        }
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    @Override
    public String toString() {
        String hours = getHours() + "";
        String minutes = getMinutes() + "";
        String seconds = getSeconds() + "";

        if (hours.length() == 1) {
            hours = "0" + getHours();
        }
        if (minutes.length() == 1) {
            minutes = "0" + getMinutes();
        }
        if (seconds.length() == 1) {
            seconds = "0" + getSeconds();
        }
        return hours + ":" + minutes + ":" + seconds;
    }

    public boolean isRunning() {
        return running;
    }
}
