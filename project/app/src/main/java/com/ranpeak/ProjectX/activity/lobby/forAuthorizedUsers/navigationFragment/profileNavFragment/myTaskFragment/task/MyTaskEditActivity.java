package com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.myTaskFragment.task;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.creatingTask.fragment.LessonListFragment;
import com.ranpeak.ProjectX.activity.interfaces.Activity;
import com.ranpeak.ProjectX.dataBase.local.dto.Task;
import com.ranpeak.ProjectX.networking.volley.Constants;
import com.ranpeak.ProjectX.settings.SharedPrefManager;
import com.ranpeak.ProjectX.viewModel.TaskViewModel;

import java.util.Calendar;
import java.util.Objects;

public class MyTaskEditActivity extends AppCompatActivity implements Activity {

    private DatePickerDialog.OnDateSetListener dateSetListener;
    private final FragmentManager fm = getFragmentManager();
    private final LessonListFragment lessonListFragment = new LessonListFragment();

    private Task myTaskItem;
    private Task editedTask;

    private Button save;
    private TextView subject;
    private TextView deadline;
    private EditText header;
    private EditText description;
    private EditText price;
    private TextView descriptionLength;
    private TextView name;
    private TextView email;
    private TextView country;

    private TaskViewModel taskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_task_edit);
        toolbar();
        findViewById();
        initData();
        onListener();

        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
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
                // todo: goto back activity from here
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void findViewById() {
        save = findViewById(R.id.activity_my_task_edit_save);
        subject = findViewById(R.id.activity_my_task_edit_subject);
        deadline = findViewById(R.id.activity_my_task_edit_deadline);
        header = findViewById(R.id.activity_my_task_edit_header);
        description = findViewById(R.id.activity_my_task_edit_description);
        descriptionLength = findViewById(R.id.activity_my_task_edit_length);
        name = findViewById(R.id.activity_my_task_edit_user_name);
        email = findViewById(R.id.activity_my_task_edit_user_email);
        country = findViewById(R.id.activity_my_task_edit_user_country);
        price = findViewById(R.id.activity_my_task_edit_price);
    }

    @Override
    public void onListener() {
        save.setOnClickListener(v -> attemptEditingTask());
        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                descriptionLength.setText(description.getText().toString().length() + "/");
            }
        });

        subject.setOnClickListener(v -> lessonListFragment.show(fm, "Lesson lists"));

        deadline.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(MyTaskEditActivity.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    dateSetListener,
                    year, month, day);
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });
        dateSetListener = (view, year, month, dayOfMonth) -> {
            month = month + 1;
            String date = dayOfMonth + "." + month + "." + year;
            deadline.setText(date);
        };
    }

    private void initData() {
        myTaskItem = (Task) getIntent().getSerializableExtra("MyTask");

        subject.setText(myTaskItem.getSubject());
        deadline.setText(myTaskItem.getDeadline());
        header.setText(myTaskItem.getHeadLine());
        description.setText(myTaskItem.getDescription());
        price.setText(String.valueOf(myTaskItem.getPrice()));
        name.setText(String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserName()));
        email.setText(String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserEmail()));
        country.setText(String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserCountry()));
    }

    public void setLessonPicker(String lessonPicker) {
        subject.setText(lessonPicker);
    }

    private void attemptEditingTask() {

        // reset errors
        header.setError(null);
        description.setError(null);

        boolean cancel = false;
        boolean changed = true;
        View focusView = null;

        if (description.getText().toString().equals("")) {
            cancel = true;
            focusView = description;
        } else if (header.getText().toString().equals("")) {
            cancel = true;
            focusView = header;
        } else if (equalFields(new Task(
                        subject.getText().toString(),
                        header.getText().toString(),
                        description.getText().toString(),
                        ((Task) getIntent().getSerializableExtra("MyTask")).getDateStart(),
                        price.getText().toString(),
                        deadline.getText().toString(),
                        ((Task) getIntent().getSerializableExtra("MyTask")).getStatus(),
                        String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserLogin()),
                        String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserEmail()),
                        String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserName()),
                        String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserAvatar()),
                        String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserCountry()),
                        ((Task) getIntent().getSerializableExtra("MyTask")).getFileTasks(),
                        ((Task) getIntent().getSerializableExtra("MyTask")).getViews()
                ),

                (Task) getIntent().getSerializableExtra("MyTask")) ) {
            changed = false;
        }
        if (!changed) {
            Toast.makeText(getApplicationContext(), "nothing changed", Toast.LENGTH_SHORT).show();
            finish();
        }
        if (changed && cancel) {
            Toast.makeText(getApplicationContext(), "feel all required fields", Toast.LENGTH_SHORT).show();
            if (focusView != null) {
                focusView.requestFocus();
            }
        } else {

            //            if (fieldsAreCorrect()) {
            editedTask = new Task(
                    subject.getText().toString(),
                    header.getText().toString(),
                    description.getText().toString(),
                    myTaskItem.getDateStart(),
                    price.getText().toString(),
                    deadline.getText().toString(),
                    myTaskItem.getStatus(),
                    String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserLogin()),
                    String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserEmail()),
                    String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserName()),
                    String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserAvatar()),
                    String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserCountry()),
                    ((Task) getIntent().getSerializableExtra("MyTask")).getFileTasks(),
                    ((Task) getIntent().getSerializableExtra("MyTask")).getViews()
            );


            /** Использовать этот метод для обновления задания в бд (только в бд)*/
            editTask(editedTask);

//                );
            // if tasks are not equal (something changed) we pass all data to prev activity
//                if (!equalFields(myTaskItem, editedTask)){
//            Intent resultIntent = new Intent();
//            resultIntent.putExtra("Position", position);
//            resultIntent.putExtra("EditedTask", editedTask);
//            setResult(Constants.Codes.EDIT_CODE, resultIntent);
//            finish();
//                } else {
//                    Toast.makeText(getApplicationContext(), "Nothing changed", Toast.LENGTH_SHORT).show();
//                    finish();
//                }
//            }
//            }
        }


    }

    private boolean fieldsAreCorrect() {
        return stringContainsItemFromList(subject.getText().toString())
                && !deadline.getText().toString().equals(getString(R.string.date))
                && !header.getText().toString().equals("")
                && !description.getText().toString().equals("")
                && !price.getText().toString().equals("");
    }

    // check if objects fields are identical
    private boolean equalFields(Task item1, Task item2) {
        return item1.getSubject().equals(item2.getSubject())
                && item1.getHeadLine().equals(item2.getHeadLine())
                && item1.getDescription().equals(item2.getDescription())
                && item1.getDateStart().equals(item2.getDateStart())
                && item1.getPrice().equals(item2.getPrice())
                && item1.getDeadline().equals(item2.getDeadline())
                && item1.getStatus().equals(item2.getStatus());
//                && item1.getFileTasks().equals(item2.getFileTasks());
    }

    //проверяет входит ли какое-либо значение в какой-либо указанный массив
    // check if selected lesson exists in Constants.Values.LESSONS
    private static boolean stringContainsItemFromList(String inputStr) {
        for (String item : Constants.Values.LESSONS) {
            if (inputStr.contains(item)) {
                return true;
            }
        }
        return false;
    }

    private void editTask(Task editedTask) {
        editedTask.setId(myTaskItem.getId());
        taskViewModel.update(editedTask);
        Intent resultIntent = new Intent();
        resultIntent.putExtra("EditedTask", editedTask);
        setResult(Constants.Codes.EDIT_CODE, resultIntent);
        finish();
    }
}
