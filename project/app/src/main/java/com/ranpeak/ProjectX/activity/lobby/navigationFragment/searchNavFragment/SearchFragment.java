package com.ranpeak.ProjectX.activity.lobby.navigationFragment.searchNavFragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.interfaces.Activity;
import com.ranpeak.ProjectX.constant.Constants;
import com.ranpeak.ProjectX.dto.TaskDTO;
import com.ranpeak.ProjectX.settings.SharedPrefManager;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class SearchFragment extends Fragment implements Activity {


    private View view;
    private ViewPager viewPager;


    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_search, container, false);
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



    public static SearchFragment newInstance() {
        return new SearchFragment();
    }


}
