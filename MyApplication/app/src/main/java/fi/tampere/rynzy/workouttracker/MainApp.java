package fi.tampere.rynzy.workouttracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import fi.tampere.rynzy.myapplication.R;

/**
 * Main menu of the app.
 * In this activity user can go to any module of the application.
 *
 * @author Joni Ryynänen
 * @version 1.0
 * @since 2018-04-24
 */
public class MainApp extends AppCompatActivity {


    /**
     * OnCreate method of the activity. Initial initialization.
     *
     * @param savedInstanceState Saved bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);
    }

    /**
     * Method for handling a button click.
     * Directs the user to another activity if clicked.
     *
     * @param view Saved bundle
     */
    public void movesButton(View view) {
        Intent myIntent = new Intent(MainApp.this, MovesActivity.class);
        MainApp.this.startActivity(myIntent);
    }

    /**
     * Method for handling a button click.
     * Directs the user to another activity if clicked.
     *
     * @param view Saved bundle
     */
    public void routinesButton(View view) {
        Intent myIntent = new Intent(MainApp.this, RoutinesActivity.class);
        MainApp.this.startActivity(myIntent);
    }

    /**
     * Method for handling a button click.
     * Directs the user to another activity if clicked.
     *
     * @param view Saved bundle
     */
    public void progressButton(View view) {
        Intent myIntent = new Intent(MainApp.this, ProgressActivity.class);
        MainApp.this.startActivity(myIntent);
    }

    /**
     * Method for handling a button click.
     * Directs the user to another activity if clicked.
     *
     * @param view Saved bundle
     */
    public void settingsButton(View view) {
        Intent myIntent = new Intent(MainApp.this, SettingsActivity.class);
        MainApp.this.startActivity(myIntent);
    }
}
