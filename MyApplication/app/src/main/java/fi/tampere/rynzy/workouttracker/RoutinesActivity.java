package fi.tampere.rynzy.workouttracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import fi.tampere.rynzy.myapplication.R;

public class RoutinesActivity extends AppCompatActivity {

    private ListView list;
    private String moveList[] = {};
    private Spinner[] spinners;
    private boolean createRoutine;
    private Button createRoutineButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routines);
        spinners = new Spinner[5];
        spinners[0] = (Spinner) findViewById(R.id.spinnerfirst);
        spinners[1] = (Spinner) findViewById(R.id.spinnersecond);
        spinners[2] = (Spinner) findViewById(R.id.spinnerthird);
        spinners[3] = (Spinner) findViewById(R.id.spinnerfourth);
        spinners[4] = (Spinner) findViewById(R.id.spinnerfifth);
        createRoutineButton = findViewById(R.id.createRoutineButton);
        readFile();
        initList();
        createRoutine = false;
    }

    public void readFile() {

        File mydir = this.getDir("workouttracker", Context.MODE_PRIVATE);
        File movesDir = new File(mydir, "moves");
        File[] myFiles = movesDir.listFiles();

        int ch;
        StringBuffer fileContent = new StringBuffer("");

        for (File fill : myFiles) {
            try (FileInputStream out = new FileInputStream(fill)) {
                try {
                    while ((ch = out.read()) != -1) {
                        fileContent.append((char) ch);
                    }
                    fileContent.append("\n");
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }


        if (myFiles.length > 0) {
            String lines[] = fileContent.toString().split("\\r?\\n");
            String seperated[] = new String[lines.length];

            for (int i = 0; i < lines.length; i++) {
                seperated[i] = lines[i].split(":")[1];
            }
            moveList = seperated;

        }


    }

    public void initList() {
        list = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_spinner, R.id.textView, moveList);

        for (Spinner spin : spinners) {
            spin.setAdapter(arrayAdapter);
        }
        list.setOnItemClickListener((parent, view, position, id) -> {


        });

    }

    public void createARoutine(View v) {
        if (createRoutine) {
            createRoutine = false;
            RelativeLayout lay = findViewById(R.id.createLayout);
            lay.setVisibility(RelativeLayout.GONE);
            createRoutineButton.setText("Create a new routine");
            EditText te = findViewById(R.id.newRoutineName);
            te.setText("");

        } else {
            createRoutine = true;
            createRoutineButton.setText("Cancel");
            RelativeLayout lay = findViewById(R.id.createLayout);
            lay.setVisibility(RelativeLayout.VISIBLE);
        }

    }

    public void addTheRoutine(View v) {

    }


}
