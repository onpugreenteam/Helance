package com.company.Helance.activity.lobby.forGuestUsers;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import com.company.Helance.R;
import com.company.Helance.interfaces.Activity;
import com.company.Helance.activity.lobby.forAuthorizedUsers.LobbyActivity;
import com.company.Helance.activity.lobby.forGuestUsers.adapter.ViewPagerAdapter;
import com.company.Helance.activity.lobby.forGuestUsers.fragments.FragmentResumes;
import com.company.Helance.activity.lobby.forGuestUsers.fragments.FragmentTasks;
import com.company.Helance.activity.logIn.LogInActivity;
import com.company.Helance.settingsApp.SharedPrefManager;

public class LobbyForGuestActivity extends AppCompatActivity implements Activity {

    private final static int LOBBY_FOR_GUEST_ACTIVITY = R.layout.activity_lobby_for_guest;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView imageView;
    private Button floatingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LOBBY_FOR_GUEST_ACTIVITY);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LobbyActivity.class));
            Log.d("Start new act", String.valueOf(SharedPrefManager.getInstance(this).isLoggedIn()));
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
        floatingLayout = findViewById(R.id.my_rounded_sign_in_button);
    }

    @Override
    public void onListener() {
//        imageView.setOnClickListener(v -> startActivity(
//                new Intent(getApplicationContext(), SearchTaskAlertDialog.class)));
        floatingLayout.setOnClickListener(v -> startActivity(
                new Intent(getApplicationContext(), LogInActivity.class)));
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentTasks(), getString(R.string.tasks));
        adapter.addFragment(new FragmentResumes(), getString(R.string.resumes));
        viewPager.setAdapter(adapter);
    }
}
