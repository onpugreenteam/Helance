package com.ranpeak.ProjectX.activity.creatingTask;

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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.creatingTask.fragment.LessonListFragment;
import com.ranpeak.ProjectX.activity.interfaces.Activity;
import com.ranpeak.ProjectX.activity.lobby.LobbyActivity;
import com.ranpeak.ProjectX.networking.Constants;
import com.ranpeak.ProjectX.request.RequestHandler;
import com.ranpeak.ProjectX.settings.SharedPrefManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import timber.log.Timber;

public class CreatingTaskActivity extends AppCompatActivity implements Activity {

    private EditText typeName;
    private EditText taskDescription;
    private TextView textViewDescriptionLength;
    private int descriptionLength = 0;
    private EditText taskPrice;
    private TextView datePicker;
    private TextView lessonPicker;
    private CheckBox checkBox;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_task);

        toolbar();
        findViewById();
        onListener();
        requestMultiplePermissions();
        // Спрашмвает пользователя разрешение на доступ к галерее(если он его не давал еще)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
        }
    }

    @Override
    public void findViewById() {
        // start fragmentActivity to choose lesson
        lessonPicker = findViewById(R.id.creating_task_lesson_picker);

        typeName = findViewById(R.id.creating_task_name);
        taskDescription = findViewById(R.id.creating_task_description);
        textViewDescriptionLength = findViewById(R.id.creating_task_description_length);

        taskPrice = findViewById(R.id.creating_task_price);
        checkBox = findViewById(R.id.creating_task_check_box);

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
        selectImages.setOnClickListener(v -> {
            startActivityForResult(new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI), GALLERY);
        });
        dateSetListener = (view, year, month, dayOfMonth) -> {
            month = month + 1;
            String date = dayOfMonth + "." + month + "." + year;
            datePicker.setText(date);
            datePicker.setTextColor(ContextCompat.getColor(this, R.color.darkText));
        };
        checkBox.setOnClickListener(v -> {
            if (checkBox.isChecked()) {
                taskPrice.setTextColor(ContextCompat.getColor(this, R.color.textOnPrimary));
                taskPrice.setEnabled(false);
            } else {
                taskPrice.setTextColor(ContextCompat.getColor(this, R.color.darkText));
                taskPrice.setEnabled(true);
            }
        });
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
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

//                Intent intent = new Intent(CreatingTaskActivity.this, LobbyActivity.class);
////                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                finish();

                // если пустых полей нет, то открывается диалог с потверждение закрытия окна
                if (!allFieldsEmpty()) {
                    openDialog();
                }
                // если ни одно из полей не заполнено, то окно закрывается без открытия диалога
                else finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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


    // Запрашивает у пользователя разрешение на доступ к галерее
    private void requestMultiplePermissions() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        // show alert dialog navigating to Settings
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(error -> Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show())
                .onSameThread()
                .check();
    }

    // устанавливает в поле LessonPicker выбранный пользователем предмет
    public void setLessonPicker(String lesson) {
        this.lessonPicker.setText(lesson);
    }

    // проверка всех полей на правильность
    // checking every field
    private void attemptCreatingTask() {
        // Reset errors.
        typeName.setError(null);
        taskDescription.setError(null);

        boolean cancel = false;
        View focusView = null;

        if (!stringContainsItemFromList(lessonPicker.getText().toString(), Constants.Values.LESSONS)) {
            cancel = true;
        } else if (typeName.getText().toString().isEmpty()) {
            cancel = true;
            focusView = typeName;
            typeName.setError(getString(R.string.error_field_required));
        } else if (taskDescription.getText().toString().isEmpty()) {
            cancel = true;
            focusView = taskDescription;
            taskDescription.setError(getString(R.string.error_field_required));
        } else if (taskPrice.getText().toString().isEmpty()) {
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
            /** Использовать это для создания задания*/
//            postTask();

            /** Убрать это когда добавление задания готово*/
            Intent intent = new Intent(getApplicationContext(), LobbyActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

//            Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
//            finish();
        }
    }


    // если все поля не тронуты(ни одно из них не заполнено), то возвращает true
    private boolean allFieldsEmpty() {
        return typeName.getText().toString().isEmpty()
                && taskDescription.getText().toString().isEmpty()
                && taskPrice.getText().toString().isEmpty()
                && !stringContainsItemFromList(lessonPicker.getText().toString(), Constants.Values.LESSONS)
                && datePicker.getText().toString().equals(getString(R.string.select_date));
    }

    //проверяет входит ли какое-либо значение в какой-либо указанный массив
    // check if selected lesson exists in Constants.Values.LESSONS
    private static boolean stringContainsItemFromList(String inputStr, String[] items) {
        for (String item : items) {
            if (inputStr.contains(item)) {
                return true;
            }
        }
        return false;
    }

    private void postTask() {
        final String headline = typeName.getText().toString().trim();
        final String text = taskDescription.getText().toString().trim();
        final String dateEnd = datePicker.getText().toString().trim();
        final String typeLesson = lessonPicker.getText().toString().trim();
        final String price = taskPrice.getText().toString().trim();


        DateFormat df = new SimpleDateFormat("d MMM yyyy");
        final String dateStart = df.format(Calendar.getInstance().getTime());

        Timber.d(dateStart);
        Timber.d(headline);
        Timber.d(text);
        Timber.d(typeLesson);
        Timber.d(dateEnd);


        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL.ADD_TASK,
                response -> {
                    Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();
//                        Intent intent = new Intent(getApplicationContext(), LobbyActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        startActivity(intent);
                    finish();
                },
                error -> Toast.makeText(getApplicationContext(), "Please on Internet", Toast.LENGTH_LONG).show()) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("headLine", headline);
                params.put("text", text);
                params.put("dateStart", dateStart);
                params.put("dateEnd", dateEnd);
                params.put("employee", String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserLogin()));
                params.put("subject", typeLesson);
                params.put("price", price);
                params.put("status", "Free");
                params.put("type", "Laba");
                return params;
            }

        };
        RequestHandler.getmInstance(this).addToRequestQueue(stringRequest);
    }
}
