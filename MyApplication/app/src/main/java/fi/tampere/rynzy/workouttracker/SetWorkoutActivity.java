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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fi.tampere.rynzy.myapplication.R;

public class SetWorkoutActivity extends AppCompatActivity {

    private Timer timer;
    private TextView moveName, timerView, currentMax, current, routineName, currentView, totalView;
    private boolean timerOn;
    private List<String> listOfMove;
    private int currentMoveIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_workout);
        timer = new Timer();
        moveName = findViewById(R.id.moveName);
        routineName = findViewById(R.id.routineName);
        currentView = findViewById(R.id.currentMove);
        totalView = findViewById(R.id.totalAmount);
        timerView = findViewById(R.id.timer);
        currentMax = findViewById(R.id.max);
        current = findViewById(R.id.currentAmount);

        Bundle extras = getIntent().getExtras();
        String data = extras.getString("routineName");

        routineName.setText(data);

        timerView.setText(timer.toString());
        timerOn = false;
        listOfMove = new ArrayList<>();
        readFile();
        currentMoveIndex = 0;

        if (listOfMove.size() == 0) {
            removeFile();
            Toast.makeText(this, "Routine doesn't have any moves.",
                    Toast.LENGTH_LONG).show();
            finish();

        } else {
            initCurrentMove();
            totalView.setText("/" + listOfMove.size());
        }
    }

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

    public void updateUi() {
        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (timerOn) {
                        Thread.sleep(1000);
                        runOnUiThread(() -> timerView.setText(timer.toString()));
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();

    }

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

            currentMoveIndex++;
            if (currentMoveIndex < listOfMove.size()) {
                Toast.makeText(this, "Workout saved!",
                        Toast.LENGTH_LONG).show();
                current.setText("");
                initCurrentMove();
            } else {
                Toast.makeText(this, "Routine completed!",
                        Toast.LENGTH_LONG).show();
                timerOn = false;
            }

        } else {
            current.setError("You have to set the amount of moves you completed.");
        }


    }

    public boolean checkInfo() {
        if (current.getText().toString().isEmpty()) {
            return false;
        }
        return (Integer.parseInt(current.getText().toString()) > 0);
    }

    public void readFile() {

        File mydir = this.getDir("workouttracker", Context.MODE_PRIVATE);
        File movesDir = new File(mydir, "routines");
        File fileWithinMyDir = new File(movesDir, routineName.getText() + ".txt");

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

        System.out.println(fileContent.toString());

        String[] moveNames = fileContent.toString().split(":");

        for (int i = 1; i < moveNames.length; i++) {

            File checkMove = new File(mydir, "moves");
            File moveFile = new File(checkMove, moveNames[i] + ".txt");
            if (moveFile.exists()) {
                listOfMove.add(moveNames[i]);
            }
        }

        System.out.println(listOfMove);

    }

    public void initCurrentMove() {
        File mydir = this.getDir("workouttracker", Context.MODE_PRIVATE);
        File movesDir = new File(mydir, "moves");
        File fileWithinMyDir = new File(movesDir, listOfMove.get(currentMoveIndex) + ".txt");

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

        String[] conts = fileContent.toString().split(":");
        currentMax.setText("Current max: " + conts[2]);
        moveName.setText(conts[1]);
        currentView.setText("" + (currentMoveIndex + 1));
        timerOn = false;
        Button bt = findViewById(R.id.timerButton);
        bt.setText("START");
        timer = new Timer();


    }

    public void removeMove(View v) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);

        builder.setTitle("Delete routine")
                .setMessage("Do you want to delete this routine?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    removeFile();
                    finish();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void removeFile() {
        File mydir = this.getDir("workouttracker", Context.MODE_PRIVATE);
        File movesDir = new File(mydir, "routines");
        File fileWithinMyDir = new File(movesDir, routineName.getText() + ".txt");
        fileWithinMyDir.delete();

    }
}
