package fi.tampere.rynzy.workouttracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fi.tampere.rynzy.myapplication.R;

/**
 * SettingsActivity which is used in weigh in.
 *
 * @author Joni Ryynänen
 * @version 1.0
 * @since 2018-04-24
 */
public class SettingsActivity extends AppCompatActivity {

    /**
     * EditTExt element for user input of the weight.
     */
    private EditText weightInput;
    /**
     * Given weight as a double.
     */
    private double weight;

    /**
     * OnCreate method of the activity. Initial initialization.
     *
     * @param savedInstanceState Saved bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        weightInput = (EditText) findViewById(R.id.weightInput);
    }

    /**
     * Checks if the users input is correct and adds it to the users progress.
     *
     * @param v Clicked View element, in this case a button.
     */
    public void checkInfo(View v) {

        try {
            weight = Double.parseDouble(weightInput.getText().toString());

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

            } catch (IOException ex) {
                ex.printStackTrace();
            }

            Date today = new Date();
            SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
            String date = DATE_FORMAT.format(today);

            sb.append("\n").append("Weight:").append(weight);
            sb.append("\n").append("Date:").append(date);

            try (FileOutputStream out = new FileOutputStream(fileWithinMyDir)) {
                byte[] converions = sb.toString().getBytes();
                out.write(converions);
                out.flush();

            } catch (IOException ex) {
            }

            Toast.makeText(this, "Weigh in set!",
                    Toast.LENGTH_LONG).show();
            finish();

        } catch (NumberFormatException ex) {
            weightInput.setError("Error in weight.");
        }
    }

}
