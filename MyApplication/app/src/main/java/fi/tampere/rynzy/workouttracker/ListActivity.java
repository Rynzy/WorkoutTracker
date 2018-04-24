package fi.tampere.rynzy.workouttracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import fi.tampere.rynzy.myapplication.R;

/**
 * A dummy activity which is used to generate a list viewing activity.
 *
 * @author Joni Ryynänen
 * @version 1.0
 * @since 2018-04-24
 */
public class ListActivity extends AppCompatActivity {

    /**
     * OnCreate method of the activity. Initial initialization.
     *
     * @param savedInstanceState Saved bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
    }

}
