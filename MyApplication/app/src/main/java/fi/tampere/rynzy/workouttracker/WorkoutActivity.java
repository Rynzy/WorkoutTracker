package fi.tampere.rynzy.workouttracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import fi.tampere.rynzy.myapplication.R;

public class WorkoutActivity extends AppCompatActivity {

    private Timer timer;
    private TextView moveName, timerView;
    private boolean timerOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        timer = new Timer();
        moveName = findViewById(R.id.moveName);
        timerView = findViewById(R.id.timer);

        Bundle extras = getIntent().getExtras();
        String data = extras.getString("name");
        moveName.setText(data);
        timerView.setText(timer.toString());
        timerOn = false;
        updateUi();
    }


    public void startTimer(View view) {
        if (!timerOn) {
            timerOn = true;
            timer.startTimer();
        } else {
            timer.setRunning(false);
            timerOn = false;
        }

    }

    public void updateUi() {
        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                timerView.setText(timer.toString());
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();

    }

}
