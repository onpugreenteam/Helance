package com.company.Helance.activity.lobby.forGuestUsers.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.company.Helance.R;
import com.company.Helance.interfaces.Activity;
import com.company.Helance.interfaces.navigators.ResumeNavigator;
import com.company.Helance.activity.lobby.adaptersForList.ResumeListAdapter;
import com.company.Helance.activity.lobby.viewModel.ResumeViewModel;
import com.company.Helance.dto.ResumeDTO;
import java.util.ArrayList;
import java.util.List;

public class FragmentResumes extends Fragment implements Activity, ResumeNavigator {

    private View view;
    private RecyclerView recyclerView;
    private ResumeListAdapter resumeListAdapter;
    private List<ResumeDTO> data = new ArrayList<>();
    private ResumeViewModel resumeViewModel;
    private PullRefreshLayout pullRefreshLayout;
    private ProgressBar progressBar;

    public FragmentResumes() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fargment_lobby_list_resume,container,false);

        resumeViewModel = new ResumeViewModel(getContext());
        resumeViewModel.setNavigator(this);

        findViewById();
        typeRefresh();
        onListener();

        setupAdapter();
        getResumes();

        return view;
    }

    private void typeRefresh() {
        pullRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_SMARTISAN);
    }

    @Override
    public void findViewById() {
        pullRefreshLayout = view.findViewById(R.id.refresh_resumes);
        recyclerView = view.findViewById(R.id.recycler_resumes);
        progressBar = view.findViewById(R.id.loading_resumes_guest);
    }

    @Override
    public void onListener() {
        pullRefreshLayout.setOnRefreshListener(() -> {
            final Handler handler = new Handler();
            handler.postDelayed(() -> {
                getResumes();
                pullRefreshLayout.setRefreshing(false);
            }, 1000);
        });
    }

    @Override
    public void handleError(Throwable throwable) {
        Toast.makeText(getContext(),"Resumes don`t upload",Toast.LENGTH_LONG).show();
    }

    @Override
    public void getDataInAdapter(List<ResumeDTO> resumeDTOS) {
        data.clear();
        data.addAll(resumeDTOS);
        resumeListAdapter.notifyDataSetChanged();
    }

    @Override
    public void startLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopLoading() {
        progressBar.setVisibility(View.GONE);
    }


    private void setupAdapter(){
        resumeListAdapter = new ResumeListAdapter(data,recyclerView,getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(resumeListAdapter);
    }

    private void getResumes(){
        resumeViewModel.getResumesFromServer();
    }
}
