package fi.tampere.rynzy.myapplication;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText nameInput, weightInput;
    private boolean nameOk, weightOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);
        nameInput = (EditText) findViewById(R.id.nameInput);
        weightInput = (EditText) findViewById(R.id.weightInput);
        weightOk = true;
        nameOk = true;
        readFile();
    }

    public void checkInfo(View view) {
        String name = nameInput.getText().toString();
        double weight = 0;

        if (name.isEmpty()) {
            nameInput.setError("You have to set a name.");
            nameOk = false;
        }

        try {
            weight = Double.parseDouble(weightInput.getText().toString());
        } catch (NumberFormatException ex) {
            weightOk = false;
            weightInput.setError("Error in weight.");
        }

        if (nameOk && weightOk) {
            createAccount();
        }

    }

    public void createAccount() {
        File mydir = this.getDir("workouttracker", Context.MODE_PRIVATE);
        File fileWithinMyDir = new File(mydir, "account.txt");
        Date today = new Date();
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
        String date = DATE_FORMAT.format(today);
        String mycontent = nameInput.getText().toString() + "\n" + weightInput.getText() + "\n" + date;


        Log.d("fd", "" + fileWithinMyDir.length());

        try (FileOutputStream out = new FileOutputStream(fileWithinMyDir)) {

            if (!fileWithinMyDir.exists()) {
                fileWithinMyDir.createNewFile();
            }

            byte[] bytesArray = mycontent.getBytes();


            out.write(bytesArray);
            out.flush();
            Log.d("fd", "" + fileWithinMyDir.length());

        } catch (IOException ex) {

        }

        System.out.println(fileWithinMyDir.getAbsolutePath());
        System.out.println(fileWithinMyDir.getAbsoluteFile());
        readFile();
    }

    public void readFile() {

        File mydir = this.getDir("workouttracker", Context.MODE_PRIVATE);
        File fileWithinMyDir = new File(mydir, "account.txt");
        int ch;
        StringBuffer fileContent = new StringBuffer("");

        try (FileInputStream out = new FileInputStream(fileWithinMyDir)) {

            try {
                while ((ch = out.read()) != -1)
                    fileContent.append((char) ch);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException ex) {

        }

        System.out.println(fileContent.toString());

    }
}
