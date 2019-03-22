package com.ranpeak.ProjectX.activity.creatingTask;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.creatingTask.fragment.LessonListFragment;
import com.ranpeak.ProjectX.activity.interfaces.Activity;
import com.ranpeak.ProjectX.constant.Constants;
import com.ranpeak.ProjectX.request.RequestHandler;
import com.ranpeak.ProjectX.settings.SharedPrefManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CreatingTaskActivity extends AppCompatActivity implements Activity {

    private Toolbar toolbar;
    private EditText typeName;
    private EditText typeDescription;
    private TextView datePicker;
    private TextView lessonPicker;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private LinearLayout linearLayout;
    private Button selectImages;
    private Button create;
    private Button cancel;
    private HorizontalScrollView horizontalScrollView;
    private static final int REQUEST_PERMISSION = 200;
    private final int GALLERY = 1;
    private ImageView buffImageView1 = null;
    private ImageView buffImageView2 = null;
    private ImageView buffImageView3 = null;
    private ImageView buffImageView4 = null;
    private ImageView buffImageView5 = null;
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
        lessonPicker = findViewById(R.id.lesson_picker);

        typeName = findViewById(R.id.creating_task_type_name);
        typeDescription = findViewById(R.id.creating_task_type_description);

        create = findViewById(R.id.creating_task_button);

        datePicker = findViewById(R.id.date_picker);

        linearLayout = findViewById(R.id.linear_layout_files);
        selectImages = findViewById(R.id.choose_image_for_task);
        horizontalScrollView = findViewById(R.id.scroll_view_files);
    }

    @Override
    public void onListener(){
        lessonPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lessonListFragment.show(fm, "Country lists");
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        attemptCreatingTask();
                    }
                });
            }
        });
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

            }
        });
        selectImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buffImageView5 == null) {
                    startActivityForResult(new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI), GALLERY);
                } else {
                    Toast.makeText(getApplicationContext(), "It's only 5 pictures available", Toast.LENGTH_SHORT).show();
                }

            }
        });
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "." + month + "." + year;
                datePicker.setText(date);
            }
        };
    }


    private void toolbar() {
        // Toolbar
//        getSupportActionBar().setTitle(getString(R.string.app_name));
//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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
                .setMessage(getString(R.string.cancel_creating))
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
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
                if (buffImageView1 == null) {
                    buffImageView1 = new ImageView(CreatingTaskActivity.this);
                    buffImageView1.setLayoutParams(new android.view.ViewGroup.LayoutParams(350, 350));
                    buffImageView1.setMaxHeight(350);
                    buffImageView1.setMaxWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
                    Glide.with(this)
                            .load(contentURI)
                            .into(buffImageView1);
                    linearLayout.addView(buffImageView1);
                    buffImageView1.setVisibility(View.VISIBLE);
                    buffImageView1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent fullScreenIntent = new Intent(CreatingTaskActivity.this, FullScreenImageActivity.class);
                            fullScreenIntent.setData(contentURI);
                            startActivity(fullScreenIntent);
                        }
                    });
                } else if (buffImageView2 == null) {
                    buffImageView2 = new ImageView(CreatingTaskActivity.this);
                    buffImageView2.setLayoutParams(new android.view.ViewGroup.LayoutParams(350, 350));
                    buffImageView2.setMaxHeight(350);
                    buffImageView2.setMaxWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
                    Glide.with(this)
                            .load(contentURI)
                            .into(buffImageView2);
                    linearLayout.addView(buffImageView2);
                    buffImageView2.setVisibility(View.VISIBLE);
                    buffImageView2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent fullScreenIntent = new Intent(CreatingTaskActivity.this, FullScreenImageActivity.class);
                            fullScreenIntent.setData(contentURI);
                            startActivity(fullScreenIntent);
                        }
                    });
                } else if (buffImageView3 == null) {
                    buffImageView3 = new ImageView(CreatingTaskActivity.this);
                    buffImageView3.setLayoutParams(new android.view.ViewGroup.LayoutParams(350, 350));
                    buffImageView3.setMaxHeight(350);
                    buffImageView3.setMaxWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
                    Glide.with(this)
                            .load(contentURI)
                            .into(buffImageView3);
                    linearLayout.addView(buffImageView3);
                    buffImageView3.setVisibility(View.VISIBLE);
                    buffImageView3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent fullScreenIntent = new Intent(CreatingTaskActivity.this, FullScreenImageActivity.class);
                            fullScreenIntent.setData(contentURI);
                            startActivity(fullScreenIntent);
                        }
                    });
                } else if (buffImageView4 == null) {
                    buffImageView4 = new ImageView(CreatingTaskActivity.this);
                    buffImageView4.setLayoutParams(new android.view.ViewGroup.LayoutParams(350, 350));
                    buffImageView4.setMaxHeight(350);
                    buffImageView4.setMaxWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
                    Glide.with(this)
                            .load(contentURI)
                            .into(buffImageView4);
                    linearLayout.addView(buffImageView4);
                    buffImageView4.setVisibility(View.VISIBLE);
                    buffImageView4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent fullScreenIntent = new Intent(CreatingTaskActivity.this, FullScreenImageActivity.class);
                            fullScreenIntent.setData(contentURI);
                            startActivity(fullScreenIntent);
                        }
                    });
                } else if (buffImageView5 == null) {
                    buffImageView5 = new ImageView(CreatingTaskActivity.this);
                    buffImageView5.setLayoutParams(new android.view.ViewGroup.LayoutParams(350, 350));
                    buffImageView5.setMaxHeight(350);
                    buffImageView5.setMaxWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
                    Glide.with(this)
                            .load(contentURI)
                            .into(buffImageView5);
                    linearLayout.addView(buffImageView4);
                    buffImageView5.setVisibility(View.VISIBLE);
                    buffImageView5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent fullScreenIntent = new Intent(CreatingTaskActivity.this, FullScreenImageActivity.class);
                            fullScreenIntent.setData(contentURI);
                            startActivity(fullScreenIntent);
                        }
                    });
                }
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
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
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
        typeDescription.setError(null);

        boolean cancel = false;
        View focusView = null;

        if (!stringContainsItemFromList(lessonPicker.getText().toString(), Constants.Values.LESSONS)) {
            cancel = true;
        } else if (typeName.getText().toString().isEmpty()) {
            cancel = true;
            focusView = typeName;
            typeName.setError(getString(R.string.error_field_required));
        } else if (typeDescription.getText().toString().isEmpty()) {
            cancel = true;
            focusView = typeDescription;
            typeDescription.setError(getString(R.string.error_field_required));
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
            Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    // если все поля не тронуты(ни одно из них не заполнено), то возвращает true
    private boolean allFieldsEmpty() {
        return typeName.getText().toString().isEmpty()
                && typeDescription.getText().toString().isEmpty()
                && !stringContainsItemFromList(lessonPicker.getText().toString(), Constants.Values.LESSONS)
                && datePicker.getText().toString().equals(getString(R.string.select_date));
    }


    //проверяет входит ли какое-либо значение в какой-либо указанный массив
    // check if selected lesson exists in Constants.Values.LESSONS
    private static boolean stringContainsItemFromList(String inputStr, String[] items) {
        for (int i = 0; i < items.length; i++) {
            if (inputStr.contains(items[i])) {
                return true;
            }
        }
        return false;
    }


    private void postTask() {

        final String headline = typeName.getText().toString().trim();
        final String text = typeDescription.getText().toString().trim();
        final String dateEnd = datePicker.getText().toString().trim();
        final String typeLesson = lessonPicker.getText().toString().trim();
        final String price = "50";


        DateFormat df = new SimpleDateFormat("d MMM yyyy");
        final String dateStart = df.format(Calendar.getInstance().getTime());

        Log.d("DateStart", dateStart);
        Log.d("DateStart", headline);
        Log.d("DateStart", text);
        Log.d("DateStart", typeLesson);
        Log.d("DateStart", dateEnd);


        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL.ADD_TASK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Please on Internet", Toast.LENGTH_LONG).show();
                    }
                }) {

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
