package com.company.Helance.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.myProfileFragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.company.Helance.R;
import com.company.Helance.interfaces.Activity;
import com.company.Helance.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.viewModel.MyProfileViewModel;
import com.company.Helance.dto.SocialNetworkDTO;
import com.company.Helance.networking.volley.Constants;
import com.company.Helance.settingsApp.SharedPrefManager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MyProfileFragment extends Fragment implements Activity {

    private final int layout = R.layout.fragment_my_profile;
    private View view;
    private TextView phoneNumber;
    private TextView email;
    private TextView telegram;
    private TextView instagram;
    private TextView facebook;
    private LinearLayout sociallNetworks;
    private LinearLayout linearTg;
    private LinearLayout linearInt;
    private LinearLayout linearFb;

    private MyProfileViewModel myProfileViewModel;
    private CompositeDisposable disposable = new CompositeDisposable();


    public MyProfileFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(layout, container, false);
        findViewById();
        onListener();
        initData();
        return view;
    }

    @Override
    public void findViewById() {
        sociallNetworks = view.findViewById(R.id.fragment_my_profile_social_networks);
        linearTg = view.findViewById(R.id.fragment_my_profile_linear_telegram);
        linearFb = view.findViewById(R.id.fragment_my_profile_linear_facebook);
        linearInt = view.findViewById(R.id.fragment_my_profile_linear_instagram);
        phoneNumber = view.findViewById(R.id.fragment_my_profile_phone_number);
        email = view.findViewById(R.id.fragment_my_profile_email);
        telegram = view.findViewById(R.id.fragment_my_profile_telegram);
        instagram = view.findViewById(R.id.fragment_my_profile_instagram);
        facebook = view.findViewById(R.id.fragment_my_profile_facebook);
    }

    @Override
    public void onListener() {

    }

    private void initData() {
        myProfileViewModel = ViewModelProviders.of(this)
                .get(MyProfileViewModel.class);
        disposable.add(myProfileViewModel.getAllSocialNetworks(
                String.valueOf(SharedPrefManager.getInstance(getContext()).getUserLogin())
                )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(socialNetworkDTOS -> {
                                    for (SocialNetworkDTO socialNetworkDTO : socialNetworkDTOS) {
                                        if (socialNetworkDTO.getNetworkName().equals(Constants.Values.TELEGRAM) && socialNetworkDTO != null) {
                                            telegram.setText(socialNetworkDTO.getNetworkLogin());
                                        } else if (socialNetworkDTO.getNetworkName().equals(Constants.Values.INSTAGRAM) && socialNetworkDTO != null) {
                                            instagram.setText(socialNetworkDTO.getNetworkLogin());
                                        } else if (socialNetworkDTO.getNetworkName().equals(Constants.Values.FACEBOOK) && socialNetworkDTO != null) {
                                            facebook.setText(socialNetworkDTO.getNetworkLogin());
                                        }
                                    }
                                    checkForEmptySocialNetworks();
                                }
                        )
        );
        phoneNumber.setText(SharedPrefManager.getInstance(getContext()).getUserTelephone());
        email.setText(String.valueOf(SharedPrefManager.getInstance(getContext()).getUserEmail()));
    }

    private void checkForEmptySocialNetworks() {
        Log.d("tg_length", String.valueOf(telegram.getText().toString().trim().length()));
        Log.d("fb_length", String.valueOf(facebook.getText().toString().trim().length()));
        if (telegram.getText().toString().trim().length() != 0
                || instagram.getText().toString().trim().length() != 0
                || facebook.getText().toString().trim().length() != 0
        ) {
            sociallNetworks.setVisibility(View.VISIBLE);
            if (telegram.getText().toString().trim().length() != 0) {
                linearTg.setVisibility(View.VISIBLE);
            }
            if (instagram.getText().toString().trim().length() != 0) {
                linearInt.setVisibility(View.VISIBLE);
            }
            if (facebook.getText().toString().trim().length() != 0) {
                linearFb.setVisibility(View.VISIBLE);
            }
        }
    }
}
