package fi.tampere.rynzy.workouttracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import fi.tampere.rynzy.myapplication.R;

public class MainApp extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);
    }

    public void movesButton(View view) {
        Intent myIntent = new Intent(MainApp.this, MovesActivity.class);
        MainApp.this.startActivity(myIntent);
    }

    public void routinesButton(View view) {
        Intent myIntent = new Intent(MainApp.this, RoutinesActivity.class);
        MainApp.this.startActivity(myIntent);
    }

    public void progressButton(View view) {
        Intent myIntent = new Intent(MainApp.this, ProgressActivity.class);
        MainApp.this.startActivity(myIntent);
    }

    public void settingsButton(View view) {
        Intent myIntent = new Intent(MainApp.this, SettingsActivity.class);
        MainApp.this.startActivity(myIntent);
    }
}
