package com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.SettingsActivity;
import com.ranpeak.ProjectX.activity.editProfile.EditProfileActivity;
import com.ranpeak.ProjectX.activity.interfaces.Activity;


public class ProfileFragment extends Fragment implements Activity {

    private View view;
    private BottomNavigationView bottomNavigationView;
    private ImageView editProfile;
    private ImageView settings;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        findViewById();
        onListener();
        // Inflate the layout for this fragment
        return view;
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void findViewById() {
        bottomNavigationView = view.findViewById(R.id.fragment_profile_bottomNavigationView);
        editProfile = view.findViewById(R.id.fragment_profile_edit_profile);
        settings = view.findViewById(R.id.fragment_profile_settings);
    }

    @Override
    public void onListener() {
        settings.setOnClickListener(v-> {
            startActivity(new Intent(getActivity(), SettingsActivity.class));
        });
        editProfile.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), EditProfileActivity.class));
        });
    }
}
