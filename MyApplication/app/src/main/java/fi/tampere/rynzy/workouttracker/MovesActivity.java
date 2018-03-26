package fi.tampere.rynzy.workouttracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import fi.tampere.rynzy.myapplication.R;

public class MovesActivity extends AppCompatActivity {

    private ListView list;
    private String moveList[] = {""};
    private String completeSet[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moves);
        readFile();
        list = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_list, R.id.textView, moveList);
        list.setAdapter(arrayAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MovesActivity.this, WorkoutActivity.class);
                String name = completeSet[position];
                String max = completeSet[position];

                i.putExtra("name", name.split(":")[1]);
                i.putExtra("max", max.split(":"));
                MovesActivity.this.startActivity(i);
            }
        });


    }

    public void readFile() {

        File mydir = this.getDir("workouttracker", Context.MODE_PRIVATE);
        File fileWithinMyDir = new File(mydir, "moves.txt");
        int ch;
        StringBuffer fileContent = new StringBuffer("");

        try (FileInputStream out = new FileInputStream(fileWithinMyDir)) {

            try {
                while ((ch = out.read()) != -1) {
                    fileContent.append((char) ch);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException ex) {

        }

        String lines[] = fileContent.toString().split("\\r?\\n");
        completeSet = lines;
        String seperated[] = new String[lines.length];

        for (int i = 0; i < lines.length; i++) {
            seperated[i] = lines[i].split(":")[1];
        }
        moveList = seperated;


    }
}