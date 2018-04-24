package fi.tampere.rynzy.workouttracker;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fi.tampere.rynzy.myapplication.R;

/**
 * WorkoutActivity is a class which rolls the singular selected workout move.
 * <p>
 * Activity loads the chosen workoutmove and fetches all the data of the move.
 *
 * @author Joni Ryynänen
 * @version 1.0
 * @since 2018-04-24
 */
public class WorkoutActivity extends AppCompatActivity {

    /**
     * Timer for keeping time, if users wants to.
     */
    private Timer timer;
    /**
     * TextView elements for displaying data.
     */
    private TextView moveName, timerView, currentMax, current;
    /**
     * Boolean to check if the timer is on.
     */
    private boolean timerOn;

    /**
     * OnCreate method of the activity. Initial initialization.
     *
     * @param savedInstanceState Saved bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        timer = new Timer();
        moveName = findViewById(R.id.moveName);
        timerView = findViewById(R.id.timer);
        currentMax = findViewById(R.id.max);
        current = findViewById(R.id.currentAmount);


        Bundle extras = getIntent().getExtras();
        String data = extras.getString("name");
        moveName.setText(data);
        currentMax.setText(currentMax.getText() + extras.getString("max"));
        timerView.setText(timer.toString());
        timerOn = false;
    }

    /**
     * Starts the timer or stops it.
     *
     * @param view Clicked View element, in this case a button.
     */
    public void startTimer(View view) {
        Button bt = findViewById(view.getId());

        if (!timerOn) {
            timerOn = true;
            timer.startTimer();
            bt.setText("STOP");
            updateUi();

        } else {
            timer.setRunning(false);
            timerOn = false;
            bt.setText("START");
        }

    }

    /**
     * Updates the time to the ui element on a seperate thread.
     */
    public void updateUi() {
        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (timerOn) {
                        runOnUiThread(() -> timerView.setText(timer.toString()));
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();

    }

    /**
     * Method for removing a move.
     *
     * @param v Button elemet that is clicked.
     */
    public void removeMove(View v) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);

        builder.setTitle("Delete workout move")
                .setMessage("Do you want to delete this workout move and all the data associated with it?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    removeFile();
                    finish();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /**
     * Method for deleting a move.
     * <p>
     * Finds the current move and removes the file from the phone.
     */
    public void removeFile() {
        File mydir = this.getDir("workouttracker", Context.MODE_PRIVATE);
        File movesDir = new File(mydir, "moves");
        File fileWithinMyDir = new File(movesDir, moveName.getText() + ".txt");
        fileWithinMyDir.delete();

    }

    /**
     * Adds the current amount of moves completed to the users's progress with a date stamp.
     *
     * @param v ViewElement, a button.
     */
    public void addToProgress(View v) {
        if (checkInfo()) {
            File mydir = this.getDir("workouttracker", Context.MODE_PRIVATE);
            File movesDir = new File(mydir, "moves");
            File fileWithinMyDir = new File(movesDir, moveName.getText() + ".txt");
            StringBuilder sb = new StringBuilder();
            int ch;

            try (FileInputStream out = new FileInputStream(fileWithinMyDir)) {
                try {
                    while ((ch = out.read()) != -1) {
                        sb.append((char) ch);
                    }
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }

            Date today = new Date();
            SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
            String date = DATE_FORMAT.format(today);
            String lines[] = sb.toString().split(":");
            EditText am = findViewById(R.id.currentAmount);

            if (Integer.parseInt(current.getText().toString()) > Integer.parseInt(lines[2])) {
                lines[2] = current.getText().toString();
            }

            currentMax.setText("Current max: " + lines[2]);

            Move tmp = new Move(lines[1], Integer.parseInt(lines[2]), lines[3]);

            tmp.addToProgress(date, am.getText().toString());


            try (FileOutputStream out = new FileOutputStream(fileWithinMyDir)) {
                byte[] converions = tmp.toString().getBytes();
                out.write(converions);
                out.flush();

            } catch (IOException ex) {
            }

            Toast.makeText(this, "Workout saved!",
                    Toast.LENGTH_LONG).show();
            current.setText("");
            timerOn = false;
            Button bt = findViewById(R.id.timerButton);
            bt.setText("START");
            timerView.setText("00:00:00");
            timer = new Timer();
        } else {
            current.setError("You have to set the amount of moves you completed.");
        }
    }

    /**
     * Checks if the user input is correct on the completed amount.
     */
    public boolean checkInfo() {
        if (current.getText().toString().isEmpty()) {
            return false;
        }
        return (Integer.parseInt(current.getText().toString()) >= 0);
    }

}
