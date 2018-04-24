package fi.tampere.rynzy.workouttracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import fi.tampere.rynzy.myapplication.R;

/**
 * CreateAccountActivity is the starting point of the application
 * In this activity some initial files will be set for the application.
 *
 * @author Joni Ryynänen
 * @version 1.0
 * @since 2018-04-24
 */
public class CreateAccountActivity extends AppCompatActivity {

    /**
     * EditText elements for name and weight.
     */
    private EditText nameInput, weightInput;
    /**
     * Boolean values for checking if it is okay to proceed.
     */
    private boolean nameOk, weightOk;


    /**
     * OnCreate method of the activity. Initial initialization.
     *
     * @param savedInstanceState Saved bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);
        nameInput = (EditText) findViewById(R.id.nameInput);
        weightInput = (EditText) findViewById(R.id.weightInput);
        weightOk = true;
        nameOk = true;
        createMoves();
    }

    /**
     * Checks if the users input is correct and acts accordingly.
     *
     * @param view Clicked View element, in this case a button.
     */
    public void checkInfo(View view) {
        String name = nameInput.getText().toString();
        double weight = 0;

        if (name.isEmpty()) {
            nameInput.setError("You have to set a name.");
            nameOk = false;
        }

        try {
            weight = Double.parseDouble(weightInput.getText().toString());
        } catch (NumberFormatException ex) {
            weightOk = false;
            weightInput.setError("Error in weight.");
        }

        if (nameOk && weightOk) {
            createAccount();
        }

    }

    /**
     * Creates the account for the user by taking the given arguments and pushing them
     * into phones internal storage as a text file.
     */
    public void createAccount() {
        File mydir = this.getDir("workouttracker", Context.MODE_PRIVATE);
        File fileWithinMyDir = new File(mydir, "account.txt");
        Date today = new Date();
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
        String date = DATE_FORMAT.format(today);
        String mycontent = "Name:" + nameInput.getText().toString() + "\n" + "Weight:" + weightInput.getText() + "\nDate:" + date;

        try (FileOutputStream out = new FileOutputStream(fileWithinMyDir)) {

            if (!fileWithinMyDir.exists()) {
                fileWithinMyDir.createNewFile();
            }

            byte[] bytesArray = mycontent.getBytes();
            out.write(bytesArray);
            out.flush();

        } catch (IOException ex) {

        }

        redirect();

    }

    /**
     * Redirects the user to another activity.
     */
    public void redirect() {
        Intent myIntent = new Intent(CreateAccountActivity.this, MainApp.class);
        CreateAccountActivity.this.startActivity(myIntent);
    }

    /**
     * Creates some basic workout moves to the application. Moves are saved into
     * a folder that contains each move as a seperate text file.
     */
    public void createMoves() {
        File mydir = this.getDir("workouttracker", Context.MODE_PRIVATE);
        ArrayList<Move> moves = new ArrayList<>();
        moves.add(new Move("Push-Up"));
        moves.add(new Move("Pull-Up"));
        moves.add(new Move("High Plank"));
        moves.add(new Move("Bodyweight Squat"));
        moves.add(new Move("Reverse Lunge"));
        moves.add(new Move("Burpee"));

        File movesDir = new File(mydir, "moves");
        File routinesDir = new File(mydir, "routines");

        if (!movesDir.exists()) {
            movesDir.mkdir();
        }

        if (!routinesDir.exists()) {
            routinesDir.mkdir();
        }

        for (Move move : moves) {
            File fileWithinMyDir = new File(movesDir, move.getName() + ".txt");

            try (FileOutputStream out = new FileOutputStream(fileWithinMyDir)) {

                if (!fileWithinMyDir.exists()) {
                    fileWithinMyDir.createNewFile();
                }

                byte[] converions = move.toString().getBytes();

                out.write(converions);
                out.flush();

            } catch (IOException ex) {

            }
        }

    }
}
