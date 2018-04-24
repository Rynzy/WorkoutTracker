package fi.tampere.rynzy.workouttracker;


import java.util.ArrayList;

/**
 * A class for defining a workout move.
 * This class is mainly used when adding a new workout move to the roster
 * or updating the statistics of an old one.
 *
 * @author Joni Ryynänen
 * @version 1.0
 * @since 2018-04-24
 */
public class Move {
    /**
     * Id of the workout move.
     */
    private int id;
    /**
     * Name of the move.
     */
    private String name;
    /**
     * Max amount of move done.
     */
    private int max;
    /**
     * Current amount of moves ever created.
     */
    private static int CURRENTAMOUNT = 0;
    /**
     * Amount that is currently completed of the move.
     */
    private int currentAmount;
    /**
     * List that contains the date stamps and amount of moves completed on that day.
     */
    private ArrayList<String> dateAndProgress;

    /**
     * Constructor which is used when creating a new move.
     */
    public Move(String name) {
        this.id = CURRENTAMOUNT;
        CURRENTAMOUNT++;
        this.max = 0;
        this.name = name;
        this.currentAmount = 0;
        dateAndProgress = new ArrayList<>();
    }

    /**
     * Constructor which is used when updating a move.
     */
    public Move(String name, int max, String dateAndProgress) {
        this.name = name;
        this.max = max;
        this.dateAndProgress = new ArrayList<>();

        String fixed = dateAndProgress.replace("[", "").replace("]", "")
                .replace(",", "");

        String space[] = fixed.split(" ");

        for (String w : space) {
            String spl[] = w.split(";");
            if (spl.length > 1) {
                addToProgress(spl[0], spl[1]);
            }
        }

    }

    /**
     * Getter for the max move.
     * @return max
     */
    public int getMax() {
        return max;
    }

    /**
     * Getter for the name.
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Setter for max value.
     * @param max value that will be set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Getter for the max move.
     * @return max
     */
    public int getCurrentAmount() {
        return currentAmount;
    }

    /**
     * Method for adding completed amount of moves.
     * @param date date stamp
     * @param amount amount of moves completed
     */
    public void addToProgress(String date, String amount) {
        dateAndProgress.add(date + ";" + amount);
    }

    /**
     * Method for adding completed amount of moves.
     * @param currentAmount Sets the current amount of moves completed
     */
    public void setCurrentAmount(int currentAmount) {
        this.currentAmount = currentAmount;
    }

    /**
     * Method for producing a string presentation of the move.
     * @return max
     */
    @Override
    public String toString() {
        return "Move:" + this.name + ":" + this.max + ":" + dateAndProgress;
    }
}
