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
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import fi.tampere.rynzy.myapplication.R;

/**
 * Activity for creating and choosing a routine.
 *
 * @author Joni Ryynänen
 * @version 1.0
 * @since 2018-04-24
 */
public class RoutinesActivity extends AppCompatActivity {

    /**
     * ListView elements for displaying all the existing routines.
     */
    private ListView listRoutines;
    /**
     * List of move names.
     */
    private String moveList[] = {};
    /**
     * List of routine names.
     */
    private String routineList[] = {};
    /**
     * List of routines and all their data.
     */
    private String completeSet[];
    /**
     * Spinner element array which hold data for move names.
     */
    private Spinner[] spinners;
    /**
     * Boolean value for checking if creating a routine.
     */
    private boolean createRoutine;
    /**
     * Button elements for creating and adding a routine.
     */
    private Button createRoutineButton;
    /**
     * Array list of moves chosen for creation of a routine.
     */
    private String[] chosenMoves;
    /**
     * Array list of layouts that are displayed when creating a new routine.
     */
    private RelativeLayout[] layouts;

    /**
     * OnCreate method of the activity. Initial initialization.
     *
     * @param savedInstanceState Saved bundle
     */
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

        layouts = new RelativeLayout[4];
        layouts[0] = (RelativeLayout) findViewById(R.id.secondSpinner);
        layouts[1] = (RelativeLayout) findViewById(R.id.thirdSpinner);
        layouts[2] = (RelativeLayout) findViewById(R.id.fourthSpinner);
        layouts[3] = (RelativeLayout) findViewById(R.id.fifthSpinner);
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

    /**
     * Reads the moves directory within the phone and loads them all
     * and populates an array with the data received.
     */
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

    /**
     * Reads the routines directory within the phone and loads them all
     * and populates an array with the data received.
     */
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

    /**
     * Initializes the View list element for the spinners.
     */
    public void initList() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_spinner, R.id.textView, moveList);

        for (int k = 0; k < spinners.length; k++) {
            spinners[k].setAdapter(arrayAdapter);
            spinners[k].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {



                    if (layouts[0].getVisibility() == View.GONE && position != 0) {
                        chosenMoves[0] = moveList[position];
                        layouts[0].setVisibility(View.VISIBLE);
                    } else if (layouts[1].getVisibility() == View.GONE && position != 0) {
                        chosenMoves[1] = moveList[position];
                        layouts[1].setVisibility(View.VISIBLE);
                    } else if (layouts[2].getVisibility() == View.GONE && position != 0) {
                        chosenMoves[2] = moveList[position];
                        layouts[2].setVisibility(View.VISIBLE);
                    } else if (layouts[3].getVisibility() == View.GONE && position != 0) {
                        chosenMoves[3] = moveList[position];
                        layouts[3].setVisibility(View.VISIBLE);
                    } else {
                        chosenMoves[4] = moveList[position];
                    }

                    for(int i = 0; i < spinners.length; i++) {
                        if(spinners[i].getSelectedItem().toString().isEmpty() || spinners[i].getSelectedItem().toString() == null) {
                            for(int k = i; k < layouts.length; k++) {

                                layouts[k].setVisibility(View.GONE);
                                chosenMoves[k] = null;
                                spinners[k].setSelection(0);

                            }
                        }
                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                }

            });
        }

    }

    /**
     * Initializes the View list element, method is called whenever a change is detected.
     */
    public void initListRoutine() {
        listRoutines = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_list, R.id.textView, routineList);

        listRoutines.setAdapter(arrayAdapter);
        listRoutines.setOnItemClickListener((parent, view, position, id) -> {
            Intent i = new Intent(RoutinesActivity.this, SetWorkoutActivity.class);
            String name = completeSet[position];
            i.putExtra("routineName", name.split(":")[0]);
            RoutinesActivity.this.startActivity(i);

        });

    }

    /**
     * Method for detecting a create routine button click.
     * <p>
     * Changes presentation of the app according to the state of the button.
     *
     * @param v View Element button
     */
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

    /**
     * Method for adding a new routine.
     * <p>
     * Checks if the routine is legal and then either adds it as a new file or asks the user for
     * fixes.
     *
     * @param v View Element button
     */
    public void addTheRoutine(View v) {
        EditText te = findViewById(R.id.newRoutineName);
        String newName = te.getText().toString();
        boolean proceed = true;

        if (newName.isEmpty() || newName.replaceAll(" ", "").length() <= 0 ) {
            te.setError("You have to set a name for your routine.");
            proceed = false;
        }

        if(newName.contains(":")) {
            te.setError("Routine's name cannot contain illegal characters: ':'");
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

            if(chosenMoves[0] == null) {
                Toast.makeText(this, "Couldn't add an empty routine.",
                        Toast.LENGTH_LONG).show();
                proceed = false;
            }
        }

        if (proceed) {

            File mydir = this.getDir("workouttracker", Context.MODE_PRIVATE);
            File movesDir = new File(mydir, "routines");
            String routineBuilt = "";

            routineBuilt += newName;

            for (String move : chosenMoves) {

                if (move != null && !move.isEmpty()) {
                    routineBuilt += ":" + move;
                    System.out.println("VALITTU: " + move);
                }
            }

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

            for(int i = 0; i < chosenMoves.length; i++) {
                chosenMoves[i] = null;
                spinners[i].setSelection(0);

            }

            Toast.makeText(this, "A new routine added!",
                    Toast.LENGTH_LONG).show();
        }
    }
}
