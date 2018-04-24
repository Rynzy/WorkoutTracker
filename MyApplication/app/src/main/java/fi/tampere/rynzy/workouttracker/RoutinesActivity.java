package fi.tampere.rynzy.workouttracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import fi.tampere.rynzy.myapplication.R;

public class RoutinesActivity extends AppCompatActivity {

    private ListView list;
    private ListView listRoutines;
    private String moveList[] = {};
    private String routineList[] = {};
    private String completeSet[];
    private Spinner[] spinners;
    private boolean createRoutine;
    private Button createRoutineButton;
    private String[] chosenMoves;
    private RelativeLayout[] layouts;

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

        layouts = new RelativeLayout[5];
        layouts[0] = (RelativeLayout) findViewById(R.id.firstSpinner);
        layouts[1] = (RelativeLayout) findViewById(R.id.secondSpinner);
        layouts[2] = (RelativeLayout) findViewById(R.id.thirdSpinner);
        layouts[3] = (RelativeLayout) findViewById(R.id.fourthSpinner);
        layouts[4] = (RelativeLayout) findViewById(R.id.fifthSpinner);
        chosenMoves = new String[5];
        createRoutineButton = findViewById(R.id.createRoutineButton);
        readFile();
        readFileRoutines();
        initList();
        createRoutine = false;

        if (moveList.length >= 1) {
            initListRoutine();
        }
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
            String tmp[] = new String[moveList.length + 1];
            tmp[0] = "";
            for (int i = 1; i < tmp.length; i++) {
                tmp[i] = moveList[i - 1];
            }

            moveList = tmp;
        }
    }

    public void readFileRoutines() {

        File mydir = this.getDir("workouttracker", Context.MODE_PRIVATE);
        File movesDir = new File(mydir, "routines");
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
            completeSet = lines;
            String seperated[] = new String[lines.length];

            for (int i = 0; i < lines.length; i++) {
                seperated[i] = lines[i].split(":")[0];
            }
            routineList = seperated;
        }
    }

    public void initList() {
        list = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_spinner, R.id.textView, moveList);

        for (Spinner spin : spinners) {
            spin.setAdapter(arrayAdapter);
            spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                    if (layouts[1].getVisibility() == View.GONE && position != 0) {
                        chosenMoves[0] = moveList[position];
                        layouts[1].setVisibility(View.VISIBLE);
                    } else if (layouts[2].getVisibility() == View.GONE && position != 0) {
                        chosenMoves[1] = moveList[position];
                        layouts[2].setVisibility(View.VISIBLE);
                    } else if (layouts[3].getVisibility() == View.GONE && position != 0) {
                        chosenMoves[2] = moveList[position];
                        layouts[3].setVisibility(View.VISIBLE);
                    } else if (layouts[4].getVisibility() == View.GONE && position != 0) {
                        chosenMoves[3] = moveList[position];
                        layouts[4].setVisibility(View.VISIBLE);
                    } else {
                        chosenMoves[4] = moveList[position];
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                }

            });
        }

    }

    public void initListRoutine() {
        listRoutines = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_list, R.id.textView, routineList);

        listRoutines.setAdapter(arrayAdapter);
        listRoutines.setOnItemClickListener((parent, view, position, id) -> {
            Intent i = new Intent(RoutinesActivity.this, SetWorkoutActivity.class);
            String name = completeSet[position];
            System.out.println("TASSAAAAAAAAAAAAAAAAAAAA:" + name.split(":")[0]);
            i.putExtra("routineName", name.split(":")[0]);
            RoutinesActivity.this.startActivity(i);

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
        EditText te = findViewById(R.id.newRoutineName);
        String newName = te.getText().toString();
        boolean proceed = true;

        if (newName.isEmpty() || newName.replaceAll(" ", "").length() <= 0) {
            te.setError("You have to set a name for your routine.");
            proceed = false;
        }
        if (proceed) {
            for (String name : routineList) {
                if (newName.equals(name)) {
                    te.setError("A routine with this name exists already.");
                    proceed = false;
                    break;
                }
            }
        }

        if (proceed) {

            File mydir = this.getDir("workouttracker", Context.MODE_PRIVATE);
            File movesDir = new File(mydir, "routines");
            String routineBuilt = "";

            routineBuilt += newName;

            for (String move : chosenMoves) {
                if (move != null && move.length() >= 1) {
                    routineBuilt += ":" + move;
                }
            }

            System.out.println("NIMIII: " + newName);
            File fileWithinMyDir = new File(movesDir, newName + ".txt");

            try (FileOutputStream out = new FileOutputStream(fileWithinMyDir)) {

                if (!fileWithinMyDir.exists()) {
                    fileWithinMyDir.createNewFile();
                }

                byte[] converions = routineBuilt.getBytes();
                out.write(converions);
                out.flush();

                System.out.println(routineBuilt);

            } catch (IOException ex) {
            }

            createRoutine = false;
            RelativeLayout lay = findViewById(R.id.createLayout);
            lay.setVisibility(RelativeLayout.GONE);
            createRoutineButton.setText("Create a new routine");
            te.setText("");
            readFileRoutines();
            initListRoutine();

            for (int k = 1; k < layouts.length; k++) {
                layouts[k].setVisibility(View.GONE);
            }

            for (String chosens : chosenMoves) {
                chosens = "";
            }

            Toast.makeText(this, "A new routine added!",
                    Toast.LENGTH_LONG).show();
        }
    }
}
