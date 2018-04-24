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
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import fi.tampere.rynzy.myapplication.R;

public class MovesActivity extends AppCompatActivity {

    private ListView list;
    private String moveList[] = {};
    private String completeSet[];
    private boolean createMove = false;
    private Button createMoveButton, addMoveButton;

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

    public void addTheMove(View v) {
        EditText te = findViewById(R.id.newMoveName);
        String newName = te.getText().toString();
        boolean proceed = true;

        if (newName.isEmpty() || newName.replaceAll(" ", "").length() <= 0) {
            te.setError("You have to set a name for your move.");
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