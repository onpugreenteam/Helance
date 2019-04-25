package com.ranpeak.ProjectX.activity.lobby.forGuestUsers;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.SearchTaskAlertDialog;
import com.ranpeak.ProjectX.activity.interfaces.Activity;
import com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.LobbyActivity;
import com.ranpeak.ProjectX.activity.lobby.forGuestUsers.adapter.ViewPagerAdapter;
import com.ranpeak.ProjectX.activity.lobby.forGuestUsers.fragments.FragmentResumes;
import com.ranpeak.ProjectX.activity.lobby.forGuestUsers.fragments.FragmentTasks;
import com.ranpeak.ProjectX.activity.logIn.LogInActivity;
import com.ranpeak.ProjectX.settings.SharedPrefManager;
import com.robertlevonyan.views.customfloatingactionbutton.FloatingActionButton;


public class LobbyForGuestActivity extends AppCompatActivity implements Activity {

    private final static int LOBBY_FOR_GUEST_ACTIVITY = R.layout.activity_lobby_for_guest;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView imageView;
    private FloatingActionButton floatingLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LOBBY_FOR_GUEST_ACTIVITY);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LobbyActivity.class));
        }

        findViewById();
        onListener();

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }


    @Override
    public void findViewById() {
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        imageView = findViewById(R.id.imageView2);
        floatingLayout = findViewById(R.id.custom_fab);
    }


    @Override
    public void onListener() {
        imageView.setOnClickListener(v -> startActivity(
                new Intent(getApplicationContext(), SearchTaskAlertDialog.class)));

        floatingLayout.setOnClickListener(v -> startActivity(
                new Intent(getApplicationContext(), LogInActivity.class)));
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentTasks(), "Tasks");
        adapter.addFragment(new FragmentResumes(), "Resumes");
        viewPager.setAdapter(adapter);
    }
}
