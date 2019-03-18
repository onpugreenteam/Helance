package com.ranpeak.ProjectX.activity.creatingTask;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.constant.Constants;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class CreatingTaskActivity extends AppCompatActivity {

    private EditText typeName;
    private EditText typeDescription;
    private TextView datePicker;
    private TextView lessonPicker;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private LinearLayout linearLayout;
    private Button selectImages;
    private Button create;
    private Button cancel;
    private static final int REQUEST_PERMISSION = 200;
    private final int GALLERY = 1;
    private ImageView buffImageView1 = null;
    private ImageView buffImageView2 = null;
    private ImageView buffImageView3 = null;
    private ImageView buffImageView4 = null;
    private ImageView buffImageView5 = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_task);

        findViewById();
        requestMultiplePermissions();
        // Спрашмвает пользователя разрешение на доступ к галерее(если он его не давал еще)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
        }
    }


    private void findViewById() {
        // start fragmentActivity to choose lesson
        final FragmentManager fm = getFragmentManager();
        final LessonListActivity lessonListActivity = new LessonListActivity();
        lessonPicker = findViewById(R.id.lesson_picker);
        lessonPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lessonListActivity.show(fm, "Country lists");
            }
        });

        typeName = findViewById(R.id.creating_task_type_name);
        typeDescription = findViewById(R.id.creating_task_type_description);

        create = findViewById(R.id.creating_task_create_button);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        attemptCreatingTask();
                    }
                });
                /* TODO: Create task and add it to server*/
            }
        });
        cancel = findViewById(R.id.creating_task_cancel_button);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        datePicker = findViewById(R.id.date_picker);
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
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "." + month + "." + year;
                datePicker.setText(date);
            }
        };
        linearLayout = findViewById(R.id.linear_layout_files);
        selectImages = findViewById(R.id.choose_image_for_task);
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
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                final Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    ImageView image = new ImageView(CreatingTaskActivity.this);

                    image.setLayoutParams(new android.view.ViewGroup.LayoutParams(350, 350));
                    image.setMaxHeight(350);
                    image.setMaxWidth(LinearLayout.LayoutParams.WRAP_CONTENT);

                    image.setImageBitmap(bitmap);
                    image.setVisibility(View.VISIBLE);
                    linearLayout.addView(image);
                    if (buffImageView1 == null) {
                        buffImageView1 = image;
                        buffImageView1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent fullScreenIntent = new Intent(CreatingTaskActivity.this, FullScreenImageActivity.class);
                                fullScreenIntent.setData(contentURI);
                                startActivity(fullScreenIntent);
                            }
                        });
                    } else if (buffImageView2 == null) {
                        buffImageView2 = image;
                        buffImageView2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent fullScreenIntent = new Intent(CreatingTaskActivity.this, FullScreenImageActivity.class);
                                fullScreenIntent.setData(contentURI);
                                startActivity(fullScreenIntent);
                            }
                        });
                    } else if (buffImageView3 == null) {
                        buffImageView3 = image;
                        buffImageView3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent fullScreenIntent = new Intent(CreatingTaskActivity.this, FullScreenImageActivity.class);
                                fullScreenIntent.setData(contentURI);
                                startActivity(fullScreenIntent);
                            }
                        });
                    } else if (buffImageView4 == null) {
                        buffImageView4 = image;
                        buffImageView4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent fullScreenIntent = new Intent(CreatingTaskActivity.this, FullScreenImageActivity.class);
                                fullScreenIntent.setData(contentURI);
                                startActivity(fullScreenIntent);
                            }
                        });
                    } else if (buffImageView5 == null) {
                        buffImageView5 = image;
                        buffImageView5.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent fullScreenIntent = new Intent(CreatingTaskActivity.this, FullScreenImageActivity.class);
                                fullScreenIntent.setData(contentURI);
                                startActivity(fullScreenIntent);
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(CreatingTaskActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
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


    public void setLessonPicker(String lesson) {
        this.lessonPicker.setText(lesson);
    }


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
            if (focusView!=null) {
                focusView.requestFocus();
            }
        } else {
            /* TODO: create new task */
            Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
        }
    }


    // check if selected lesson exists in Constants.Values.LESSONS
    private static boolean stringContainsItemFromList(String inputStr, String[] items) {
        for (int i = 0; i < items.length; i++) {
            if (inputStr.contains(items[i])) {
                return true;
            }
        }
        return false;
    }
}
