package com.company.Helance.activity.lobby.forAuthorizedUsers.navigationFragment.resumesNavFragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.company.Helance.R;
import com.company.Helance.activity.creating.creatingResume.CreatingResumeActivity;
import com.company.Helance.interfaces.Activity;
import com.company.Helance.interfaces.navigators.ResumeNavigator;
import com.company.Helance.activity.lobby.adaptersForList.ResumeListAdapter;
import com.company.Helance.activity.lobby.viewModel.ResumeViewModel;
import com.company.Helance.dto.ResumeDTO;
import java.util.ArrayList;
import java.util.List;

public class ResumesFragment extends Fragment implements Activity, ResumeNavigator {

    private View view;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private ResumeListAdapter resumeListAdapter;
    private List<ResumeDTO> data = new ArrayList<>();
    private ImageView search;
    private PullRefreshLayout mSwipeRefreshLayout;
    private ResumeViewModel resumeViewModel;
    private ProgressBar progressBar;

    public ResumesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_resumes, container, false);

        resumeViewModel = new ResumeViewModel(getContext());
        resumeViewModel.setNavigator(this);

        findViewById();
        typeRefresh();
        onListener();

        setupAdapter();
        getResumes();

        return view;
    }

    @Override
    public void findViewById() {
        fab = view.findViewById(R.id.fragment_resumes_floatingActionButton2);
        recyclerView = view.findViewById(R.id.fragment_resumes_recycleView);
        search = view.findViewById(R.id.fragment_resumes_search);
        mSwipeRefreshLayout = view.findViewById(R.id.fragment_resumes_swipeRefresh);
        progressBar = view.findViewById(R.id.loading_resumes);
    }

    private void typeRefresh() {
        mSwipeRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_SMARTISAN);
    }

    @Override
    public void onListener() {
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            final Handler handler = new Handler();
            handler.postDelayed( () -> {
                        getResumes();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }, 1000
            );
        });
        fab.setOnClickListener(v -> startActivity(
                new Intent(getContext(), CreatingResumeActivity.class)));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && fab.getVisibility() == View.VISIBLE) {
                    fab.hide();
                } else if (dy < 0 && fab.getVisibility() != View.VISIBLE) {
                    fab.show();
                }
            }
        });

//        search.setOnClickListener(v -> startActivity(
//                new Intent(getContext(), SearchResumeAlertDialog.class)));
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
