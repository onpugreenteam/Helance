package com.ranpeak.ProjectX.activity.lobby.navigationFragment.resumesNavFragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.creatingResume.CreatingResumeActivity;
import com.ranpeak.ProjectX.activity.interfaces.Activity;
import com.ranpeak.ProjectX.constant.Constants;
import com.ranpeak.ProjectX.dto.TaskDTO;
import com.ranpeak.ProjectX.settings.SharedPrefManager;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.util.List;



public class ResumesFragment extends Fragment implements Activity {


    private View view;
    private FloatingActionButton fab;



    public ResumesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_resumes, container, false);
        findViewById();
        onListener();

        return view;
    }


    @Override
    public void findViewById() {
        fab = view.findViewById(R.id.floatingActionButton2);
    }

    @Override
    public void onListener() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CreatingResumeActivity.class));
            }
        });
    }


    public static ResumesFragment newInstance() {
        return new ResumesFragment();
    }

    public class GetTaskWhenUserCostumer extends AsyncTask<Void, Void, List<TaskDTO>> {

        @Override
        protected List<TaskDTO> doInBackground(Void... params) {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<List<TaskDTO>> response = restTemplate.exchange(
                    Constants.URL.GET_ALL_TASK_WHEN_USER_COSTUMER + String.valueOf(SharedPrefManager.getInstance(getContext()).getUserLogin()),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<TaskDTO>>() {
                    });
            List<TaskDTO> taskDTOS = response.getBody();

            return taskDTOS;
        }

        @Override
        protected void onPostExecute(List<TaskDTO> taskDTOS) {

        }
    }
}
