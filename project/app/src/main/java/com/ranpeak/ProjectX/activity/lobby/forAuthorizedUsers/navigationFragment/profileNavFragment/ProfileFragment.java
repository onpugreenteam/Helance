package com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.SettingsActivity;
import com.ranpeak.ProjectX.activity.editProfile.EditProfileActivity;
import com.ranpeak.ProjectX.activity.interfaces.Activity;
import com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.myProfileFragment.MyProfileFragment;
import com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.myResumeFragment.MyResumeFragment;
import com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.myTaskFragment.MyTaskFragment;
import com.ranpeak.ProjectX.settings.SharedPrefManager;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class ProfileFragment extends Fragment implements Activity {

    private View view;
    private BottomNavigationView bottomNavigationView;
    private ImageView editProfile;
    private ImageView settings;
    private final FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
    private final Fragment myProfile = new MyProfileFragment();
    private final Fragment myTask = new MyTaskFragment();
    private final Fragment myResume = new MyResumeFragment();
    private Fragment activeFragment;

    // user info
    private TextView name;
    private TextView login;

    public ProfileFragment() {

    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        findViewById();
        onListener();
        initData();
        initFragments();
        return view;
    }

    @Override
    public void findViewById() {
        bottomNavigationView = view.findViewById(R.id.fragment_profile_bottomNavigationView);
        editProfile = view.findViewById(R.id.fragment_profile_edit_profile);
        settings = view.findViewById(R.id.fragment_profile_settings);
        name = view.findViewById(R.id.fragment_profile_name);
        login = view.findViewById(R.id.fragment_profile_login);
    }

    @Override
    public void onListener() {
        settings.setOnClickListener(v ->
                startActivity(new Intent(getActivity(), SettingsActivity.class)));
        editProfile.setOnClickListener(v ->
                startActivity(new Intent(getActivity(), EditProfileActivity.class)));
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.nav_my_profile:
                    fragmentManager.beginTransaction().hide(activeFragment).show(myProfile).commit();
                    activeFragment = myProfile;
                    return true;
                case R.id.nav_my_resumes:
                    fragmentManager.beginTransaction().hide(activeFragment).show(myResume).commit();
                    activeFragment = myResume;
                    return true;
                case R.id.nav_my_tasks:
                    fragmentManager.beginTransaction().hide(activeFragment).show(myTask).commit();
                    activeFragment = myTask;
                    return true;
                default:
                    return false;
            }
        });
    }

    private void initData() {
            name.setText(String.valueOf(SharedPrefManager.getInstance(getContext()).getUserName()));
            login.setText(String.valueOf(SharedPrefManager.getInstance(getContext()).getUserLogin()));
    }

    private void initFragments() {
        fragmentManager.beginTransaction().add(R.id.fragment_profile_navigation_container, myProfile, "1").commit();
        fragmentManager.beginTransaction().add(R.id.fragment_profile_navigation_container, myResume, "2").hide(myResume).commit();
        fragmentManager.beginTransaction().add(R.id.fragment_profile_navigation_container, myTask, "3").hide(myTask).commit();
        bottomNavigationView.setSelectedItemId(R.id.nav_my_profile);
    }
}
