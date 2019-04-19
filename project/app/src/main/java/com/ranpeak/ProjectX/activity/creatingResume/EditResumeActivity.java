package com.ranpeak.ProjectX.activity.creatingResume;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.interfaces.Activity;

import java.util.Objects;

public class EditResumeActivity extends AppCompatActivity implements Activity {

    private final static int EDIT_RESUME_ACTIVITY = R.layout.activity_edit_resume;

    private TextView resumeCategory;
    private EditText resumeDescription;
    private Button editResumeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(EDIT_RESUME_ACTIVITY);

        toolbar();
    }

    @Override
    public void findViewById() {
        resumeCategory = findViewById(R.id.activity_edit_resume_category_text);
        resumeDescription = findViewById(R.id.activity_edit_resume_descriptions_input_text);
        editResumeButton = findViewById(R.id.activity_edit_resume_button);
    }

    @Override
    public void onListener() {
        editResumeButton.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(),"saved",Toast.LENGTH_LONG).show();
            finish();
        });
    }

    private void toolbar() {
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.edit_resume_toolbar_name);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
