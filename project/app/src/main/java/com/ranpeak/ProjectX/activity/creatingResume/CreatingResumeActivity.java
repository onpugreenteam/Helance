package com.ranpeak.ProjectX.activity.creatingResume;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.creatingTask.fragment.LessonListFragment;
import com.ranpeak.ProjectX.activity.interfaces.Activity;

import java.util.Objects;

public class CreatingResumeActivity extends AppCompatActivity implements Activity {

    private final static int CREATING_RESUME_ACTIVITY = R.layout.activity_creating_resume;

    private TextView lesson;
    private EditText description;
    private TextView descriptionLength;
    private int descriptionLengthResume = 0;
    private TextView textViewDescriptionLength;
    private TextView lessonPicker;
    private final FragmentManager fm = getFragmentManager();
    private final LessonListFragment lessonListFragment = new LessonListFragment();
    private Button create;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(CREATING_RESUME_ACTIVITY);

        findViewById();
        onListener();
        toolbar();
    }


    @Override
    public void findViewById() {
        lessonPicker = findViewById(R.id.lesson_list_in_resume);
        description = findViewById(R.id.creating_resume_description);
        descriptionLength = findViewById(R.id.creating_resume_description_length);
        create = findViewById(R.id.creating_resume_button);
    }

    @Override
    public void onListener() {
        lessonPicker.setOnClickListener(v -> lessonListFragment.show(fm, "Country lists"));
        create.setOnClickListener(view -> Toast.makeText(getApplicationContext(),"created",Toast.LENGTH_LONG).show());

    }

    private void toolbar() {
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.app_name));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
