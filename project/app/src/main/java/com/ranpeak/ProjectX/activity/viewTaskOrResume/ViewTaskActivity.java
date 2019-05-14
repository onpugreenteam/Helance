package com.ranpeak.ProjectX.activity.viewTaskOrResume;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.r0adkll.slidr.Slidr;
import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.interfaces.Activity;
import com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.viewModel.MyTaskViewModel;
import com.ranpeak.ProjectX.activity.viewTaskOrResume.contact.ContactDialogFragment;
import com.ranpeak.ProjectX.dto.SocialNetworkDTO;
import com.ranpeak.ProjectX.dto.TaskDTO;
import com.ranpeak.ProjectX.networking.IsOnline;
import com.ranpeak.ProjectX.networking.retrofit.ApiService;
import com.ranpeak.ProjectX.networking.retrofit.RetrofitClient;
import com.ranpeak.ProjectX.networking.volley.Constants;
import com.ranpeak.ProjectX.settings.SharedPrefManager;
import java.util.Objects;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ViewTaskActivity extends AppCompatActivity implements Activity {

    private TextView subject;
    private TextView header;
    private TextView description;
    private LinearLayout linearLayout;
    private CircleImageView imageView;
    private TextView name;
    private TextView email;
    private TextView country;
    private Button contact;
    private String userEmail;
    private String userPhone;
    private String userName;
    private String userTelegram;
    private String userIntagram;
    private String userFacebook;

    TaskDTO taskDTO;

    private MyTaskViewModel taskViewModel;
    private CompositeDisposable disposable = new CompositeDisposable();

    private ApiService apiService = RetrofitClient.getInstance()
            .create(ApiService.class);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view);
        toolbar();
        findViewById();
        onListener();
        setData();
        Slidr.attach(this);

        if(IsOnline.getInstance().isConnectingToInternet(getApplicationContext())) {
            updateViews();
        } else {
            Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void findViewById() {
        subject = findViewById(R.id.activity_task_view_subject);
        header = findViewById(R.id.activity_task_view_header);
        description = findViewById(R.id.activity_task_view_description);
        linearLayout = findViewById(R.id.task_view_activity_linearLayout);
        imageView = findViewById(R.id.profile_image_view);
        name = findViewById(R.id.activity_task_view_name);
        country = findViewById(R.id.activity_task_view_country);
        email= findViewById(R.id.activity_task_view_email);
        contact = findViewById(R.id.activity_task_view_button);
    }

    @SuppressLint("CheckResult")
    private void updateViews(){
        apiService.updateTaskViews(taskDTO.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(taskDTO1 ->
                                Log.d("OK", String.valueOf(taskDTO1.getId())),
                           throwable ->
                                   Log.d("Error",throwable.getMessage()));
    }

    private void setData(){
        Intent intent = getIntent();
        taskDTO = (TaskDTO) intent.getSerializableExtra("TaskObject");

        userEmail = taskDTO.getUserEmail();
        userPhone = taskDTO.getTelephone();
        userName = taskDTO.getUserName();

        subject.setText(taskDTO.getSubject());
        header.setText(taskDTO.getHeadLine());
        description.setText(taskDTO.getDescription());
        name.setText(taskDTO.getUserName());
        email.setText(taskDTO.getUserEmail());
        country.setText(taskDTO.getUserCountry());

        taskViewModel = ViewModelProviders.of(this).get(MyTaskViewModel.class);

        // если таск не принадлежит текущему пользователю, то кнопка связи ему не видна
        if(!taskDTO.getUserLogin().equals(
                String.valueOf(SharedPrefManager.getInstance(getApplicationContext())
                .getUserLogin())
        )) {
            contact.setVisibility(View.VISIBLE);
            if(IsOnline.getInstance().isConnectingToInternet(getApplicationContext())
            ) {
                disposable.add(taskViewModel.getUserNetworks(taskDTO.getUserLogin())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(socialNetworkDTOS -> {
                            for (SocialNetworkDTO s :
                                    socialNetworkDTOS) {
                                if (s.getNetworkName().equals(getString(R.string.telegram))) {
                                    userTelegram = s.getNetworkLogin();
                                } else if (s.getNetworkName().equals(getString(R.string.instagram))) {
                                    userIntagram = s.getNetworkLogin();
                                } else if (s.getNetworkName().equals(getString(R.string.facebook))) {
                                    userFacebook = s.getNetworkLogin();
                                }
                            }
                        })
                );
            } else Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onListener() {
        contact.setOnClickListener(v -> {
            ContactDialogFragment contactDialogFragment = new ContactDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putString(getString(R.string.phone),userPhone);
            bundle.putString(getString(R.string.email),userEmail);
            bundle.putString(getString(R.string.name),userName);
            bundle.putString(getString(R.string.telegram), userTelegram);
            bundle.putString(getString(R.string.instagram), userIntagram);
            bundle.putString(getString(R.string.facebook), userFacebook);
            contactDialogFragment.setArguments(bundle);
            contactDialogFragment.show(getSupportFragmentManager(), contactDialogFragment.getTag());
        });
    }

    private void toolbar() {
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.app_name));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
