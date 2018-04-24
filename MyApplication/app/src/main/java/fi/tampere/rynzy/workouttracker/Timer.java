package fi.tampere.rynzy.workouttracker;

/**
 * A class which is used to create an Timer object.
 * <p>
 * This is used whenever user is doing an workout move. Timer includes seconds, minutes and hours
 * in HH:MM:SS format.
 *
 * @author Joni Ryynänen
 * @version 1.0
 * @since 2018-04-24
 */
public class Timer {
    /**
     * Minutes of the timer.
     */
    private int minutes;
    /**
     * Seconds of the timer.
     */
    private int seconds;
    /**
     * Hours of the timer.
     */
    private int hours;
    /**
     * Boolean check for if the timer is running.
     */
    private boolean running;

    /**
     * Default constructor for the timer.
     */
    public Timer() {
        this.minutes = 0;
        this.seconds = 0;
        this.hours = 0;
        running = false;
    }

    /**
     * Sets the timer state to running.
     *
     * @param running value
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * Starts the timer within a new thread, updates the timer every second.
     */
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

        if (!running) {


            Thread t = new Thread(runnable);
            t.start();
        }

    }

    /**
     * Increases seconds by one, and hours and minutes if neccessary.
     */
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

    /**
     * Getter for minutes.
     *
     * @return minutes
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     * Setter for minutes.
     *
     * @param minutes value
     */
    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    /**
     * Getter for seconds.
     *
     * @return seconds
     */
    public int getSeconds() {
        return seconds;
    }

    /**
     * Setter for seconds.
     *
     * @param seconds value
     */
    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    /**
     * Getter for minutes.
     *
     * @return minutes
     */
    public int getHours() {
        return hours;
    }

    /**
     * Setter for hours.
     *
     * @param hours value
     */
    public void setHours(int hours) {
        this.hours = hours;
    }

    /**
     * Creates a string presentation of the timer
     * in predefined structure HH:MM:SS.
     *
     * @return string presentation of the timer
     */
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

    /**
     * Getter for timer to see if it is running.
     *
     * @return value wheter the timer is running
     */
    public boolean isRunning() {
        return running;
    }
}
