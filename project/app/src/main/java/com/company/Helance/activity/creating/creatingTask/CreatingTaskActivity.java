package com.company.Helance.activity.creating.creatingTask;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.company.Helance.R;
import com.company.Helance.activity.creating.fragment.LessonListFragment;
import com.company.Helance.interfaces.navigators.CreatingTaskNavigator;
import com.company.Helance.activity.creating.viewModel.CreatingTaskViewModel;
import com.company.Helance.interfaces.Activity;
import com.company.Helance.networking.IsOnline;
import com.company.Helance.networking.volley.Constants;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class CreatingTaskActivity extends AppCompatActivity implements Activity, CreatingTaskNavigator {

    private EditText typeName;
    private EditText taskDescription;
    private TextView textViewDescriptionLength;
    private EditText taskPrice;
    private TextView datePicker;
    private TextView lessonPicker;
//    private CheckBox checkBox;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private LinearLayout linearLayout;
    private ImageView selectImages;
    private Button create;
    private HorizontalScrollView horizontalScrollView;
    private static final int REQUEST_PERMISSION = 200;
    private final int GALLERY = 1;
    private List<ImageView> imageViewList = new ArrayList<>();
    private final FragmentManager fm = getFragmentManager();
    private final LessonListFragment lessonListFragment = new LessonListFragment();
    private CreatingTaskViewModel creatingTaskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_task);

        toolbar();
        findViewById();
        onListener();

//        // Спрашмвает пользователя разрешение на доступ к галерее(если он его не давал еще)
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
//                PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                    REQUEST_PERMISSION);
//        }

        creatingTaskViewModel = new CreatingTaskViewModel(getApplicationContext());
        creatingTaskViewModel.setNavigator(this);
    }

    @Override
    public void findViewById() {
        lessonPicker = findViewById(R.id.creating_task_lesson_picker);
        typeName = findViewById(R.id.creating_task_name);
        taskDescription = findViewById(R.id.creating_task_description);
        textViewDescriptionLength = findViewById(R.id.creating_task_description_length);
        taskPrice = findViewById(R.id.creating_task_price);
//        checkBox = findViewById(R.id.creating_task_check_box);
        create = findViewById(R.id.creating_task_button);
        datePicker = findViewById(R.id.date_picker);
        linearLayout = findViewById(R.id.creating_task_linear_layout_files);
        selectImages = findViewById(R.id.creating_task_choose_image_for_task);
        horizontalScrollView = findViewById(R.id.creating_task_scroll_view_files);
    }

    @Override
    public void onListener() {
        lessonPicker.setOnClickListener(v -> lessonListFragment.show(fm, "Country lists"));
        create.setOnClickListener(view -> attemptCreatingTask());
        datePicker.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(CreatingTaskActivity.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    dateSetListener,
                    year, month, day);
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        });
        selectImages.setVisibility(View.INVISIBLE);
//        selectImages.setOnClickListener(v -> {
//            startActivityForResult(new Intent(Intent.ACTION_PICK,
//                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI), GALLERY);
//        });
        dateSetListener = (view, year, month, dayOfMonth) -> {
            month = month + 1;
            String date = dayOfMonth + "." + month + "." + year;
            datePicker.setText(date);
            datePicker.setTextColor(ContextCompat.getColor(this, R.color.darkText));
        };
//        checkBox.setOnClickListener(v -> {
//            if (checkBox.isChecked()) {
//                taskPrice.setTextColor(ContextCompat.getColor(this, R.color.textOnPrimary));
//                taskPrice.setEnabled(false);
//            } else {
//                taskPrice.setTextColor(ContextCompat.getColor(this, R.color.darkText));
//                taskPrice.setEnabled(true);
//            }
//        });
        taskDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                textViewDescriptionLength.setText(taskDescription.getText().length() + "/");
            }
        });
    }

    private void toolbar() {
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.app_name));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {// если пустых полей нет, то открывается диалог с потверждение закрытия окна
            if (!allFieldsEmpty()) {
                openDialog();
            }
            // если ни одно из полей не заполнено, то окно закрывается без открытия диалога
            else finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // если нажата кнопка назад на устройстве
    @Override
    public void onBackPressed() {
        // если пустых полей нет, то открывается диалог с потверждение закрытия окна
        if (!allFieldsEmpty()) {
            openDialog();
        }
        // если ни одно из полей не заполнено, то окно закрывается без открытия диалога
        else super.onBackPressed();
    }

    // открытие подтверждающего диалога перед закрытием окна
    private void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false)
                .setTitle(getString(R.string.confirm_exit))
                .setMessage(getString(R.string.cancel_creating_task))
                .setPositiveButton(getString(R.string.yes), (dialog, which) -> finish())
                .setNegativeButton(getString(R.string.no), (dialog, which) -> dialog.cancel());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    // устанавливаем изображения в LinearLayout
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                horizontalScrollView.setVisibility(View.VISIBLE);
                final Uri contentURI = data.getData();
                ImageView buffImageView = new ImageView(CreatingTaskActivity.this);
                buffImageView.setLayoutParams(new android.view.ViewGroup.LayoutParams(350, 350));
                buffImageView.setMaxHeight(350);
                buffImageView.setMaxWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
                Glide.with(this)
                        .load(contentURI)
                        .into(buffImageView);
                imageViewList.add(buffImageView);
                linearLayout.addView(buffImageView);
                buffImageView.setVisibility(View.VISIBLE);
                buffImageView.setOnClickListener(v -> {
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(CreatingTaskActivity.this);
                    View mView = getLayoutInflater().inflate(R.layout.activity_full_screen_image, null);
                    PhotoView photoView = mView.findViewById(R.id.fullScreenImageView);
                    Glide.with(this)
                            .load(contentURI)
                            .into(photoView);
                    mBuilder.setView(mView);
                    AlertDialog mDialog = mBuilder.create();
                    mDialog.show();
                });
            }
        }
    }

    // устанавливает в поле LessonPicker выбранный пользователем предмет
    public void setLessonPicker(String lesson) {
        this.lessonPicker.setText(lesson);
    }

    // проверка всех полей на правильность
    // checking every field
    private void attemptCreatingTask() {

        if(IsOnline.getInstance().isConnectingToInternet(getApplicationContext())) {
            // Reset errors.
            typeName.setError(null);
            taskDescription.setError(null);

            boolean cancel = false;
            View focusView = null;

            if (!stringContainsItemFromList(lessonPicker.getText().toString())) {
                cancel = true;
            } else if (typeName.getText().toString().trim().isEmpty()) {
                cancel = true;
                focusView = typeName;
                typeName.setError(getString(R.string.error_field_required));
            } else if (taskDescription.getText().toString().trim().isEmpty()) {
                cancel = true;
                focusView = taskDescription;
                taskDescription.setError(getString(R.string.error_field_required));
            } else if (taskPrice.getText().toString().trim().isEmpty()) {
                cancel = true;
                focusView = taskPrice;
                taskPrice.setError(getString(R.string.error_field_required));
            } else if (datePicker.getText().toString().equals(getString(R.string.select_date))) {
                cancel = true;
            }

            if (cancel) {
                Toast.makeText(getApplicationContext(), "feel all required fields", Toast.LENGTH_SHORT).show();
                if (focusView != null) {
                    focusView.requestFocus();
                }
            } else {
                postTask();
                finish();
            }
        } else {
            Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }

    }

    // если все поля не тронуты(ни одно из них не заполнено), то возвращает true
    private boolean allFieldsEmpty() {

        return typeName.getText().toString().isEmpty()
                && taskDescription.getText().toString().isEmpty()
                && taskPrice.getText().toString().isEmpty()
                && !stringContainsItemFromList(lessonPicker.getText().toString())
                && datePicker.getText().toString().equals(getString(R.string.select_date));

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

    @Override
    public void handleError(Throwable throwable) {
        Log.d("Task post error",throwable.getMessage());
        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onComplete() {
        Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();
    }

    @Override
    public void postTask() {
        final String headline = typeName.getText().toString().trim();
        final String descrpiption = taskDescription.getText().toString().trim();
        final String dateEnd = datePicker.getText().toString().trim();
        final String subject = lessonPicker.getText().toString().trim();
        final float price = Float.parseFloat(taskPrice.getText().toString().trim());
        creatingTaskViewModel.postTask(headline,descrpiption,dateEnd,subject,price);
    }
}
