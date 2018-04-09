package fi.tampere.rynzy.workouttracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import fi.tampere.rynzy.myapplication.R;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText nameInput, weightInput;
    private boolean nameOk, weightOk;

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

    public void redirect() {
        Intent myIntent = new Intent(CreateAccountActivity.this, MainApp.class);
        CreateAccountActivity.this.startActivity(myIntent);
    }

    public void readFile() {

        File mydir = this.getDir("workouttracker", Context.MODE_PRIVATE);
        File fileWithinMyDir = new File(mydir, "account.txt");
        int ch;
        StringBuffer fileContent = new StringBuffer("");

        try (FileInputStream out = new FileInputStream(fileWithinMyDir)) {

            try {
                while ((ch = out.read()) != -1)
                    fileContent.append((char) ch);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException ex) {

        }

    }

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
