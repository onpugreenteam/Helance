package com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.SettingsActivity;
import com.ranpeak.ProjectX.activity.editProfile.EditProfileActivity;
import com.ranpeak.ProjectX.activity.interfaces.Activity;
import com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.adapter.ProfileFragmentPagerAdapter;
import com.ranpeak.ProjectX.settings.SharedPrefManager;
import com.ranpeak.ProjectX.viewModel.ResumeViewModel;
import com.ranpeak.ProjectX.viewModel.TaskViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;


public class ProfileFragment extends Fragment implements Activity {

    private View view;
    private TabLayout tabLayout;
    private ImageView editProfile;
    private ImageView settings;
    private ViewPager viewPager;
    private TextView tasksCount;
    private TextView resumesCount;
    private TaskViewModel taskViewModel;
    private ResumeViewModel resumeViewModel;
    private io.reactivex.disposables.CompositeDisposable mDisposable = new CompositeDisposable();


    private final int[] imageResId = {
            R.drawable.my_profile, R.drawable.my_task, R.drawable.my_resume
    };

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
        initFragments(viewPager);
        return view;
    }

    @Override
    public void findViewById() {
        tasksCount = view.findViewById(R.id.fragment_profile_task_count);
        resumesCount = view.findViewById(R.id.fragment_profile_resumes_count);
        viewPager = view.findViewById(R.id.fragment_profile_viewPager);
        tabLayout = view.findViewById(R.id.fragment_profile_tabLayout);
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

        tabLayout.setupWithViewPager(viewPager);
    }

    private void initData() {
        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        taskViewModel.getCountOfUsersTask(
                String.valueOf(SharedPrefManager.getInstance(getContext()).getUserLogin())
        ).observe(this, integer -> {
            tasksCount.setText(String.valueOf(integer));
        });
//        mDisposable.add(taskViewModel.getCountOfUsersTask(
//                String.valueOf(SharedPrefManager.getInstance(getContext()).getUserLogin())
//        )
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(integer -> {
//                    tasksCount.setText("29");
//                })
//        );
//        mDisposable.dispose();
        resumeViewModel = ViewModelProviders.of(this).get(ResumeViewModel.class);
        resumeViewModel.getCountOfUsersResumes(
                String.valueOf(SharedPrefManager.getInstance(getContext()).getUserLogin())
        ).observe(this, integer -> {
            resumesCount.setText(String.valueOf(integer));
        });
//        mDisposable.add(resumeViewModel.getCountOfUsersResumes(
//                String.valueOf(SharedPrefManager.getInstance(getContext()).getUserLogin())
//        )
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(integer -> {
//                    resumesCount.setText("20");
//                }));
//        mDisposable.dispose();
        name.setText(String.valueOf(SharedPrefManager.getInstance(getContext()).getUserName()));
        login.setText(String.valueOf(SharedPrefManager.getInstance(getContext()).getUserLogin()));
    }

    private void initFragments(ViewPager viewPager) {
        ProfileFragmentPagerAdapter adapter = new ProfileFragmentPagerAdapter(getFragmentManager());
        viewPager.setAdapter(adapter);
        for (int i = 0; i < imageResId.length; i++) {
            Objects.requireNonNull(tabLayout.getTabAt(i)).setIcon(imageResId[i]);
        }
    }
}
