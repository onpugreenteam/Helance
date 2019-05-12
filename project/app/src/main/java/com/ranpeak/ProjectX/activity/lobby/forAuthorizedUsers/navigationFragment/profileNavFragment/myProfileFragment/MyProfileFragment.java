package com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.myProfileFragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.interfaces.Activity;
import com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.viewModel.MyProfileViewModel;
import com.ranpeak.ProjectX.dto.SocialNetworkDTO;
import com.ranpeak.ProjectX.networking.volley.Constants;
import com.ranpeak.ProjectX.settings.SharedPrefManager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class MyProfileFragment extends Fragment implements Activity {

    private final int layout = R.layout.fragment_my_profile;
    private View view;
    private TextView phoneNumber;
    private TextView email;
    private TextView country;
    private TextView telegram;
    private TextView instagram;
    private TextView facebook;

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
        phoneNumber = view.findViewById(R.id.fragment_my_profile_phone_number);
        email = view.findViewById(R.id.fragment_my_profile_email);
        country = view.findViewById(R.id.fragment_my_profile_country);
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
                                }
                        )
        );

        phoneNumber.setText(SharedPrefManager.getInstance(getContext()).getUserTelephone());
        email.setText(String.valueOf(SharedPrefManager.getInstance(getContext()).getUserEmail()));
        country.setText(String.valueOf(SharedPrefManager.getInstance(getContext()).getUserCountry()));
    }
}
