package com.company.Helance.activity.viewTaskOrResume;

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
import com.company.Helance.R;
import com.company.Helance.activity.interfaces.Activity;
import com.company.Helance.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.viewModel.MyResumeViewModel;
import com.company.Helance.activity.viewTaskOrResume.contact.ContactDialogFragment;
import com.company.Helance.dto.ResumeDTO;
import com.company.Helance.dto.SocialNetworkDTO;
import com.company.Helance.networking.IsOnline;
import com.company.Helance.networking.retrofit.ApiService;
import com.company.Helance.networking.retrofit.RetrofitClient;
import com.company.Helance.settings.SharedPrefManager;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ViewResumeActivity extends AppCompatActivity implements Activity {

    private TextView subject;
    private TextView date;
    private TextView description;
    private LinearLayout linearLayout;
    private CircleImageView imageView;
    private TextView name;
    private TextView email;
    private TextView country;
    private Button contact;
    private String userEmail;
    private String userName;
    private String userPhone;
    private String userTelegram;
    private String userIntagram;
    private String userFacebook;
    private ResumeDTO resumeDTO;

    private MyResumeViewModel resumeViewModel;
    private CompositeDisposable disposable = new CompositeDisposable();

    private ApiService apiService = RetrofitClient.getInstance()
            .create(ApiService.class);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_view);
        toolbar();
        findViewById();
        setData();
        onListener();
        Slidr.attach(this);

        if(IsOnline.getInstance().isConnectingToInternet(getApplicationContext())) {
            updateViews();
        } else {
            Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void findViewById() {
        subject = findViewById(R.id.activity_resume_view_subject);
        date = findViewById(R.id.activity_resume_view_date);
        description = findViewById(R.id.activity_resume_view_description);
        linearLayout = findViewById(R.id.resume_view_activity_linearLayout);
        imageView = findViewById(R.id.resume_profile_image_view);
        name = findViewById(R.id.activity_resume_view_name);
        country = findViewById(R.id.activity_resume_view_country);
        email = findViewById(R.id.activity_resume_view_email);
        contact = findViewById(R.id.activity_resume_view_button);
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

    @SuppressLint("CheckResult")
    private void updateViews(){
        apiService.updateResumeViews(resumeDTO.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resumeDTO1 ->
                                Log.d("OK", String.valueOf(resumeDTO1.getId())),
                        throwable ->
                                Log.d("Error",throwable.getMessage()));
    }

    private void setData() {
        Intent intent = getIntent();
        resumeDTO = (ResumeDTO) intent.getSerializableExtra("ResumeObject");

        userPhone = resumeDTO.getTelephone();
        userEmail = resumeDTO.getUserEmail();
        userName = resumeDTO.getUserName();
        subject.setText(resumeDTO.getSubject());
        description.setText(resumeDTO.getOpportunities());
        name.setText(resumeDTO.getUserName());
        email.setText(resumeDTO.getUserEmail());
        country.setText(resumeDTO.getUserCountry());
        date.setText(resumeDTO.getDateStart());

        resumeViewModel = ViewModelProviders.of(this).get(MyResumeViewModel.class);

        // если резюме не принадлежит текущему пользователю, то кнопка связи ему не видна
        if(!resumeDTO.getUserLogin().equals(
                String.valueOf(SharedPrefManager.getInstance(getApplicationContext())
                        .getUserLogin())
        )) {
            contact.setVisibility(View.VISIBLE);
            if(IsOnline.getInstance().isConnectingToInternet(getApplicationContext())) {
                disposable.add(resumeViewModel.getUserNetworks(resumeDTO.getUserLogin())
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
            } else {
                Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onListener() {
        contact.setOnClickListener(v -> {
            ContactDialogFragment contactDialogFragment = new ContactDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putString(getString(R.string.phone), userPhone);
            bundle.putString(getString(R.string.email), userEmail);
            bundle.putString(getString(R.string.name), userName);
            bundle.putString(getString(R.string.telegram), userTelegram);
            bundle.putString(getString(R.string.instagram), userIntagram);
            bundle.putString(getString(R.string.facebook), userFacebook);
            contactDialogFragment.setArguments(bundle);
            contactDialogFragment.show(getSupportFragmentManager(), contactDialogFragment.getTag());
        });
    }
}
