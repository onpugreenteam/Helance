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
import com.company.Helance.activity.lobby.forAuthorizedUsers.navigationFragment.resumesNavFragment.adapter.ResumeListAdapter;
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
    private ArrayList<String> imageUrls = new ArrayList<>();
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
        initImageBitmaps();

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

    private void initImageBitmaps() {
        imageUrls.add("https://cdn.fishki.net/upload/post/2017/03/19/2245758/01-beautiful-white-cat-imagescar-wallpaper.jpg");
        imageUrls.add("https://usionline.com/wp-content/uploads/2016/02/12-4.jpg");
        imageUrls.add("http://bm.img.com.ua/nxs/img/prikol/images/large/1/2/308321_879390.jpg");
        imageUrls.add("http://bm.img.com.ua/nxs/img/prikol/images/large/1/2/308321_879389.jpg");
        imageUrls.add("http://www.radionetplus.ru/uploads/posts/2013-05/1369460621_panda-26.jpg");
        imageUrls.add("http://v.img.com.ua/b/1100x999999/1/fc/409a3eebc81a4d8dc4a2437cbe07afc1.jpg");
        imageUrls.add("http://ztb.kz/media/imperavi/59cb70c479d20.jpg");
        imageUrls.add("https://bryansktoday.ru/uploads/common/dcbf021231e742e6_XL.jpg");
        imageUrls.add("https://ki.ill.in.ua/m/670x450/24227758.jpg");
        imageUrls.add("https://cs9.pikabu.ru/post_img/2017/10/06/7/1507289738144386744.jpg");
        imageUrls.add("https://placepic.ru/uploads/posts/2014-03/1396234652_podborka-realnyh-pacanov-2.jpg");
        imageUrls.add("http://2queens.ru/Uploads/Yelizaveta/%D1%80%D0%B5%D0%B0%D0%BB%D1%8C%D0%BD%D1%8B%D0%B9%20%D0%BF%D0%B0%D1%86%D0%B0%D0%BD%20%D1%80%D0%B6%D0%B0%D0%B2%D1%8B%D0%B9.jpg");
        imageUrls.add("http://bidla.net/uploads/posts/2017-06/thumbs/1496943807_urodru20170608ku7564.jpeg");
        imageUrls.add("https://www.baikal-daily.ru/upload/resize_cache/iblock/ca6/600_500_1/vovan.png");
        imageUrls.add("https://www.vokrug.tv/pic/person/b/3/6/d/b36d3d2f4c263fc18eba1a464eb942d2.jpeg");
        imageUrls.add("https://i.mycdn.me/image?id=877079192648&t=35&plc=WEB&tkn=*85PLfcQAXU8Glv9V8-xzIyJxZF4");
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
        resumeListAdapter = new ResumeListAdapter(data,imageUrls,recyclerView,getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(resumeListAdapter);
    }

    private void getResumes(){
        resumeViewModel.getResumesFromServer();
    }
}
