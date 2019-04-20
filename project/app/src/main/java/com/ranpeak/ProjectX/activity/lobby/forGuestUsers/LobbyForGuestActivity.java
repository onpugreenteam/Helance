package com.ranpeak.ProjectX.activity.lobby.forGuestUsers;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.interfaces.Activity;
import com.ranpeak.ProjectX.activity.lobby.forGuestUsers.adapter.ViewPagerAdapter;
import com.ranpeak.ProjectX.activity.lobby.forGuestUsers.fragments.FragmentResumes;
import com.ranpeak.ProjectX.activity.lobby.forGuestUsers.fragments.FragmentTasks;

public class LobbyForGuestActivity extends AppCompatActivity implements Activity {

    private final static int LOBBY_FOR_GUEST_ACTIVITY = R.layout.activity_lobby_for_guest;

    private TabLayout tabLayout;
    private ViewPagerAdapter adapter;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LOBBY_FOR_GUEST_ACTIVITY);
        findViewById();
        onListener();

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentTasks(),"Tasks");
        adapter.addFragment(new FragmentResumes(),"Resumes");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void findViewById() {
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
    }

    @Override
    public void onListener() {

    }
}
