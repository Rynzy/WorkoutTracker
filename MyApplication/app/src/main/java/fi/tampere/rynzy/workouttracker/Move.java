package fi.tampere.rynzy.workouttracker;


public class Move {
    private int id;
    private String name;
    private int max;
    private static int CURRENTAMOUNT = 0;
    private int currentAmount;

    public Move(String name) {
        this.id = CURRENTAMOUNT;
        CURRENTAMOUNT++;
        this.max = 0;
        this.name = name;
        this.currentAmount = 0;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(int currentAmount) {
        this.currentAmount = currentAmount;
    }

    @Override
    public String toString() {
        return "Move:" + this.name + ":" + this.max;
    }
}
