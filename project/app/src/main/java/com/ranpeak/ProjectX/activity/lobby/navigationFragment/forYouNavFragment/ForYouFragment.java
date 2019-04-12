package com.ranpeak.ProjectX.activity.lobby.navigationFragment.forYouNavFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.interfaces.Activity;

public class ForYouFragment extends Fragment implements Activity {


    private View view;
    private ViewPager viewPager;


    public ForYouFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_for_you, container, false);
        findViewById();
        onListener();

        return view;
    }


    @Override
    public void findViewById() {

    }

    @Override
    public void onListener() {

    }



    public static ForYouFragment newInstance() {
        return new ForYouFragment();
    }


}
