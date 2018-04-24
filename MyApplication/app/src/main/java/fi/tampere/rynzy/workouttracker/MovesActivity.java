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
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import fi.tampere.rynzy.myapplication.R;

/**
 * Activity for creating and choosing a move to complete.
 *
 * @author Joni Ryynänen
 * @version 1.0
 * @since 2018-04-24
 */
public class MovesActivity extends AppCompatActivity {

    /**
     * ListView element which holds all the moves that can be chosen.
     */
    private ListView list;
    /**
     * List of move names.
     */
    private String moveList[] = {};
    /**
     * List of moves and all their data.
     */
    private String completeSet[];
    /**
     * Boolean value for checking if creating a move.
     */
    private boolean createMove = false;
    /**
     * Button elements for creating and adding a move.
     */
    private Button createMoveButton, addMoveButton;

    /**
     * OnCreate method of the activity. Initial initialization.
     *
     * @param savedInstanceState Saved bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moves);
        createMoveButton = findViewById(R.id.createMoveButton);
        addMoveButton = findViewById(R.id.addTheMove);
        readFile();

        if (moveList.length >= 1) {
            initList();
        }

    }

    /**
     * Reads the moves directory within the phone and loads them all
     * and populates an array with the data received.
     *
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
            completeSet = lines;
            String seperated[] = new String[lines.length];

            for (int i = 0; i < lines.length; i++) {
                seperated[i] = lines[i].split(":")[1];
            }
            moveList = seperated;

        }


    }

    /**
     * Initializes the View list element, method is called whenever a change is detected.
     *
     */
    public void initList() {
        list = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_list, R.id.textView, moveList);

        list.setAdapter(arrayAdapter);
        list.setOnItemClickListener((parent, view, position, id) -> {
            Intent i = new Intent(MovesActivity.this, WorkoutActivity.class);
            String name = completeSet[position];
            String max = completeSet[position];
            i.putExtra("name", name.split(":")[1]);
            i.putExtra("max", max.split(":")[2]);
            MovesActivity.this.startActivity(i);
        });

    }

    /**
     * Method for detecting a create move button click.
     *
     * Changes presentation of the app according to the state of the button.
     *
     * @param v View Element button
     */
    public void createAMove(View v) {
        if (createMove) {
            createMove = false;
            RelativeLayout lay = findViewById(R.id.createLayout);
            lay.setVisibility(RelativeLayout.GONE);
            createMoveButton.setText("Create a new move");
            EditText te = findViewById(R.id.newMoveName);
            te.setText("");

        } else {
            createMove = true;
            createMoveButton.setText("Cancel");
            RelativeLayout lay = findViewById(R.id.createLayout);
            lay.setVisibility(RelativeLayout.VISIBLE);
        }

    }

    /**
     * Method for adding a new move.
     *
     * Checks if the move is legal and then either adds it as a new file or asks the user for
     * fixes.
     *
     * @param v View Element button
     */
    public void addTheMove(View v) {
        EditText te = findViewById(R.id.newMoveName);
        String newName = te.getText().toString();
        boolean proceed = true;

        if (newName.isEmpty() || newName.replaceAll(" ", "").length() <= 0) {
            te.setError("You have to set a name for your move.");
            proceed = false;
        }

        if(newName.contains(":")) {
            te.setError("Move's name cannot contain illegal characters: ':'");
            proceed = false;
        }
        if (proceed) {
            for (String name : moveList) {
                if (newName.equals(name)) {
                    te.setError("A move with this name exists already.");
                    proceed = false;
                    break;
                }
            }
        }

        if (proceed) {

            File mydir = this.getDir("workouttracker", Context.MODE_PRIVATE);
            File movesDir = new File(mydir, "moves");
            Move newMove = new Move(newName);
            File fileWithinMyDir = new File(movesDir, newMove.getName() + ".txt");

            try (FileOutputStream out = new FileOutputStream(fileWithinMyDir)) {

                if (!fileWithinMyDir.exists()) {
                    fileWithinMyDir.createNewFile();
                }

                byte[] converions = newMove.toString().getBytes();
                out.write(converions);
                out.flush();

            } catch (IOException ex) {
            }


            createMove = false;
            RelativeLayout lay = findViewById(R.id.createLayout);
            lay.setVisibility(RelativeLayout.GONE);
            createMoveButton.setText("Create a new move");
            te.setText("");
            readFile();
            initList();

            Toast.makeText(this, "A new moved added!",
                    Toast.LENGTH_LONG).show();

        }

    }
}