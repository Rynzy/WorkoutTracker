package fi.tampere.rynzy.workouttracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.File;
import fi.tampere.rynzy.myapplication.R;

/**
 * First activity that is launched with the application's startup.
 * Redirects the user to another activity based on the fact if the user has
 * used the application before.
 *
 * @author Joni Ryynänen
 * @version 1.0
 * @since 2018-04-24
 */
public class MainActivity extends AppCompatActivity {

    /**
     * OnCreate method of the activity. Initial initialization.
     *
     * @param savedInstanceState Saved bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        redirect();
    }

    /**
     * Redirects the user to another activity.
     */
    public void redirect() {
        Intent myIntent;
        File mydir = this.getDir("workouttracker", Context.MODE_PRIVATE);

        if (!new File(mydir, "account.txt").exists()) {
            myIntent = new Intent(MainActivity.this, CreateAccountActivity.class);
        } else {
            myIntent = new Intent(MainActivity.this, MainApp.class);
        }

        MainActivity.this.startActivity(myIntent);
    }
}
