package com.ranpeak.ProjectX.activity.lobby.navigationFragment.homeNavFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.creatingTask.CreatingTaskActivity;

public class HomeFragment extends Fragment {

    private FloatingActionButton fab;
    private View view;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
        findViewById();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), CreatingTaskActivity.class));
            }
        });
        return view;
    }


    private void findViewById(){
        fab = view.findViewById(R.id.floatingActionButton);
    }


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }
}
