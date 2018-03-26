package fi.tampere.rynzy.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        redirect();
    }

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
