package com.company.Helance.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.myTaskFragment.task;

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

import com.company.Helance.R;
import com.company.Helance.activity.creating.fragment.LessonListFragment;
import com.company.Helance.interfaces.Activity;
import com.company.Helance.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.viewModel.MyTaskViewModel;
import com.company.Helance.dto.MyTaskDTO;
import com.company.Helance.networking.IsOnline;
import com.company.Helance.networking.retrofit.ApiService;
import com.company.Helance.networking.retrofit.RetrofitClient;
import com.company.Helance.networking.volley.Constants;
import com.company.Helance.settingsApp.SharedPrefManager;

import java.util.Calendar;
import java.util.Objects;

public class MyTaskEditActivity extends AppCompatActivity implements Activity {

    private DatePickerDialog.OnDateSetListener dateSetListener;
    private final FragmentManager fm = getFragmentManager();
    private final LessonListFragment lessonListFragment = new LessonListFragment();
    private ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);


    private MyTaskDTO myTaskItem;
    private MyTaskDTO editedTask;
    private MyTaskViewModel myTaskViewModel;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_task_edit);
        toolbar();
        findViewById();
        initData();
        onListener();

        myTaskViewModel = ViewModelProviders.of(this).get(MyTaskViewModel.class);
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
        myTaskViewModel = ViewModelProviders.of(this).get(MyTaskViewModel.class);
        myTaskItem = (MyTaskDTO) getIntent().getSerializableExtra("MyTask");
        descriptionLength.setText(description.getText().toString().length() + "/");
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
        if(IsOnline.getInstance().isConnectingToInternet(getApplicationContext())) {
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
            } else if (equalFields(

                    (MyTaskDTO) getIntent().getSerializableExtra("MyTask"))) {
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
                editedTask = new MyTaskDTO();


                editedTask.setSubject(subject.getText().toString());
                editedTask.setHeadLine(header.getText().toString());
                editedTask.setDescription(description.getText().toString());
                editedTask.setDateStart(((MyTaskDTO) getIntent().getSerializableExtra("MyTask")).getDateStart());
                editedTask.setPrice(Float.parseFloat(price.getText().toString()));
                editedTask.setDeadline(deadline.getText().toString());
                editedTask.setActive(((MyTaskDTO) getIntent().getSerializableExtra("MyTask")).isActive());
                editedTask.setUserLogin(String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserLogin()));
                editedTask.setUserEmail(String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserEmail()));
                editedTask.setUserName(String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserName()));
                editedTask.setUserCountry(String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserCountry()));
                editedTask.setUserAvatar(String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserAvatar()));
                editedTask.setFileTasks(((MyTaskDTO) getIntent().getSerializableExtra("MyTask")).getFileTasks());
                editedTask.setViews(((MyTaskDTO) getIntent().getSerializableExtra("MyTask")).getViews());
                /** Использовать этот метод для обновления задания в бд (только в бд)*/
                editTask(editedTask);

                updateTaskOnServer(editedTask);

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

        } else {
            Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
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
    private boolean equalFields(MyTaskDTO item2) {
        MyTaskDTO item1 = new MyTaskDTO();
        item1.setSubject(subject.getText().toString());
        item1.setHeadLine(header.getText().toString());
        item1.setDescription(description.getText().toString());
        item1.setDateStart(((MyTaskDTO) getIntent().getSerializableExtra("MyTask")).getDateStart());
        item1.setPrice(Float.parseFloat(price.getText().toString()));
        item1.setDeadline(deadline.getText().toString());
        item1.setActive(((MyTaskDTO) getIntent().getSerializableExtra("MyTask")).isActive());
        item1.setUserLogin(String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserLogin()));
        item1.setUserEmail(String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserEmail()));
        item1.setUserName(String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserName()));
        item1.setUserCountry(String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserCountry()));
        item1.setUserAvatar(String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserAvatar()));
        item1.setFileTasks(((MyTaskDTO) getIntent().getSerializableExtra("MyTask")).getFileTasks());
        item1.setViews(((MyTaskDTO) getIntent().getSerializableExtra("MyTask")).getViews());

        return item1.getSubject().equals(item2.getSubject())
                && item1.getHeadLine().equals(item2.getHeadLine())
                && item1.getDescription().equals(item2.getDescription())
                && item1.getDateStart().equals(item2.getDateStart())
                && item1.getPrice() == item2.getPrice()
                && item1.getDeadline().equals(item2.getDeadline())
                && item1.isActive() == item2.isActive();
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

    private void editTask(MyTaskDTO editedTask) {
        editedTask.setId(myTaskItem.getId());
//        Completable.fromRunnable(() -> {
//            myTaskViewModel.update(editedTask);
//        })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe();
        myTaskViewModel.update(editedTask);
        Intent resultIntent = new Intent();
        resultIntent.putExtra("EditedTask", editedTask);
        setResult(Constants.Codes.EDIT_CODE, resultIntent);
        finish();
    }

    private void updateTaskOnServer(MyTaskDTO task) {

    }
}
