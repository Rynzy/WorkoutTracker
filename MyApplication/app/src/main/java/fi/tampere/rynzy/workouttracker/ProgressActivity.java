package fi.tampere.rynzy.workouttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import fi.tampere.rynzy.myapplication.R;

/**
 * ProgressActivity where user can see his weight gain/loss.
 * <p>
 * Account file is loaded and parsed.
 * Populates the tables data with the values.
 *
 * @author Joni Ryynänen
 * @version 1.0
 * @since 2018-04-24
 */
public class ProgressActivity extends AppCompatActivity {

    /**
     * TextView elements for displaying data.
     */
    private TextView start, min, max, current, change;

    /**
     * OnCreate method of the activity. Initial initialization.
     *
     * @param savedInstanceState Saved bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        start = findViewById(R.id.startWeight);
        min = findViewById(R.id.lowestWeight);
        max = findViewById(R.id.highestWeight);
        current = findViewById(R.id.currentWeight);
        change = findViewById(R.id.changeWeight);
        readWeight();
    }

    /**
     * Reads the account file for weight data.
     * <p>
     * Values for the textviews are also set here.
     */
    public void readWeight() {

        File mydir = this.getDir("workouttracker", Context.MODE_PRIVATE);
        File fileWithinMyDir = new File(mydir, "account.txt");
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

            String[] lines = sb.toString().split(":");
            ArrayList<Double> cut = new ArrayList<>();

            double startW = Double.parseDouble(lines[2].split("\n")[0]);
            double minW = Double.parseDouble(lines[2].split("\n")[0]);
            double maxW = Double.parseDouble(lines[2].split("\n")[0]);

            for (int i = 2; i < lines.length; i += 2) {
                String[] another = lines[i].split("\n");
                double w = Double.parseDouble(another[0]);

                if (minW > w) {
                    minW = w;
                }

                if (maxW < w) {
                    maxW = w;
                }

                cut.add(w);

            }

            DecimalFormat df = new DecimalFormat("#.##");
            min.setText("" + df.format(minW));
            max.setText("" + df.format(maxW));
            current.setText("" + df.format(cut.get(cut.size() - 1)));
            start.setText("" + df.format(startW));
            double c = startW - cut.get(cut.size() - 1);

            change.setText("" + df.format(c));

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    /**
     * Asks the user if hes sure that he wants his
     * account to be deleted.
     * <p>
     * If user is sure of the delete, calls for destruction of files.
     *
     * @param v Clicked View element, in this case a button.
     */
    public void deleteAccount(View v) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);

        builder.setTitle("Delete account")
                .setMessage("Do you want to delete this account and all the data associated with it?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    removeAccount();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /**
     * Removes all the moves, routines, weight data of the user.
     * <p>
     * After removing all the files, user will be redirected to the account creation.
     */
    public void removeAccount() {
        File mydir = this.getDir("workouttracker", Context.MODE_PRIVATE);
        File movesDir = new File(mydir, "moves");
        File[] myMoves = movesDir.listFiles();
        File routinesDir = new File(mydir, "routines");
        File[] myRoutines = routinesDir.listFiles();
        File account = new File(mydir, "account.txt");

        for (File move : myMoves) {
            move.delete();
        }

        for (File routine : myRoutines) {
            routine.delete();
        }

        movesDir.delete();
        routinesDir.delete();
        account.delete();

        Toast.makeText(this, "Account removed.",
                Toast.LENGTH_LONG).show();
        Intent myIntent = new Intent(ProgressActivity.this, MainActivity.class);
        ProgressActivity.this.startActivity(myIntent);
    }
}
