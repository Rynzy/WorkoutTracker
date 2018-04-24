package fi.tampere.rynzy.workouttracker;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import fi.tampere.rynzy.myapplication.R;

public class ProgressActivity extends AppCompatActivity {

    private TextView start, min, max, current, change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        start = findViewById(R.id.startWeight);
        min = findViewById(R.id.lowestWeight);
        max = findViewById(R.id.highestWeight);
        current = findViewById(R.id.currentWeight);
        change = findViewById(R.id.changeWeight);
        readWeight();
    }

    public void readWeight() {

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

            String[] lines = sb.toString().split(":");
            ArrayList<Double> cut = new ArrayList<>();

            double startW = Double.parseDouble(lines[2].split("\n")[0]);
            start.setText("" + startW);
            double minW = Double.parseDouble(lines[2].split("\n")[0]);
            double maxW = Double.parseDouble(lines[2].split("\n")[0]);

            for (int i = 2; i < lines.length; i += 2) {
                String[] another = lines[i].split("\n");
                double w = Double.parseDouble(another[0]);

                if (minW > w) {
                    minW = w;
                }

                if (maxW < w) {
                    maxW = w;
                }

                cut.add(w);

            }

            DecimalFormat df = new DecimalFormat("#.##");
            min.setText("" + df.format(minW));
            max.setText("" + df.format(maxW));
            current.setText("" + cut.get(cut.size() - 1));
            double c = startW - cut.get(cut.size() - 1);

            change.setText("" + df.format(c));

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
