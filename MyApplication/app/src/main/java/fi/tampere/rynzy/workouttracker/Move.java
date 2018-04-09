package fi.tampere.rynzy.workouttracker;


import java.util.ArrayList;

public class Move {
    private int id;
    private String name;
    private int max;
    private static int CURRENTAMOUNT = 0;
    private int currentAmount;
    private ArrayList<String> dateAndProgress;

    public Move(String name) {
        this.id = CURRENTAMOUNT;
        CURRENTAMOUNT++;
        this.max = 0;
        this.name = name;
        this.currentAmount = 0;
        dateAndProgress = new ArrayList<>();
    }

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

    public int getMax() {
        return max;
    }

    public String getName() {
        return this.name;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getCurrentAmount() {
        return currentAmount;
    }

    public void addToProgress(String date, String amount) {
        dateAndProgress.add(date + ";" + amount);
    }

    public void setCurrentAmount(int currentAmount) {
        this.currentAmount = currentAmount;
    }

    @Override
    public String toString() {
        return "Move:" + this.name + ":" + this.max + ":" + dateAndProgress;
    }
}
