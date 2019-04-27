package com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.navigationFragment.resumesNavFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.SearchResumeAlertDialog;
import com.ranpeak.ProjectX.activity.creatingResume.CreatingResumeActivity;
import com.ranpeak.ProjectX.activity.interfaces.Activity;
import com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.navigationFragment.resumesNavFragment.adapter.ResumeListAdapter;
import com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.navigationFragment.tasksNavFragment.TasksFragment;
import com.ranpeak.ProjectX.dataBase.App;
import com.ranpeak.ProjectX.dataBase.local.LocalDB;
import com.ranpeak.ProjectX.dataBase.local.dao.ResumeDAO;
import com.ranpeak.ProjectX.dto.ResumeDTO;
import com.ranpeak.ProjectX.networking.retrofit.ApiService;
import com.ranpeak.ProjectX.networking.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


public class ResumesFragment extends Fragment implements Activity {

    private View view;
    private FloatingActionButton fab;
    private LocalDB localDB;
    private ResumeDAO resumeDAO;
    private RecyclerView recyclerView;
    private ResumeListAdapter resumeListAdapter;
    private List<ResumeDTO> data = new ArrayList<>();
    private ArrayList<String> imageUrls = new ArrayList<>();
    private ImageView search;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private ApiService apiService = RetrofitClient.getInstance()
            .create(ApiService.class);

    public ResumesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_resumes, container, false);
        findViewById();
        onListener();
        initImageBitmaps();

        localDB = App.getInstance().getLocalDB();
        resumeDAO = localDB.resumeDAO();

//        addResumesToLocalDB(mockResumes());
        getResumesFromLocalDB();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        resumeListAdapter = new ResumeListAdapter(data, imageUrls, recyclerView, getActivity());
        recyclerView.setAdapter(resumeListAdapter);

        getResumesFromServer();

        return view;
    }

    private List<ResumeDTO> mockResumes() {
        List<ResumeDTO> resumeDTOS = new ArrayList<>();

        ResumeDTO resumeDTO1 = new ResumeDTO();
        resumeDTO1.setId(1);
        resumeDTO1.setOpportunities("Gjitk yf[ ns fjelpdifqebnwifunqweo eoqfnweuiofh qef uqweif nqewiuf  iuqef iwuefbn weiuf iqeuf qiuewf qnieuwf qeiwf nq");
        resumeDTO1.setDateStart("21.08.19");
        resumeDTO1.setStatus("Active");
        resumeDTO1.setUserLogin("Fantastic");
        resumeDTO1.setSubject("Programming");

        ResumeDTO resumeDTO2 = new ResumeDTO();
        resumeDTO1.setId(2);
        resumeDTO2.setOpportunities("Gjitk yf[ ns fjelpdifqebnwifunqweo eoqfnweuiofh qef uqweif nqewiuf  iuqef iwuefbn weiuf iqeuf qiuewf qnieuwf qeiwf nq");
        resumeDTO2.setDateStart("21.10.19");
        resumeDTO2.setStatus("Active");
        resumeDTO2.setUserLogin("Jenia12CM");
        resumeDTO2.setSubject("Programming");

        ResumeDTO resumeDTO3 = new ResumeDTO();
        resumeDTO1.setId(3);
        resumeDTO3.setOpportunities("Gjitk yf[ ns fjelpdifqebnwifunqweo eoqfnweuiofh qef uqw  sa d sa asd eif nqewiuf  iuqef iwuefbn weiuf iqeuf qiuewf qnieuwf qeiwf nq");
        resumeDTO3.setDateStart("21.10.19");
        resumeDTO3.setStatus("Active");
        resumeDTO3.setUserLogin("Fantastic");
        resumeDTO3.setSubject("Programming");

        ResumeDTO resumeDTO4 = new ResumeDTO();
        resumeDTO1.setId(4);
        resumeDTO4.setOpportunities("Gjitk yf[ ns fjelpdifqebnwifunqweo eoqfAS DASD ASD d asnweuiofh qef uqweif nqewiuf  iuqef iwuefbn weiuf iqeuf qiuewf qnieuwf qeiwf nq");
        resumeDTO4.setDateStart("21.10.19");
        resumeDTO4.setStatus("Active");
        resumeDTO4.setUserLogin("Azik13");
        resumeDTO4.setSubject("Programming");

        ResumeDTO resumeDTO5 = new ResumeDTO();
        resumeDTO1.setId(5);
        resumeDTO5.setOpportunities("Gjitk yf[ ns fsadas sad asd as d asd ASjelpdifqebnwifunqweo eoqfnweuiofh qef uqweif nqewiuf  iuqef iwuefbn weiuf iqeuf qiuewf qnieuwf qeiwf nq");
        resumeDTO5.setDateStart("21.11.19");
        resumeDTO5.setStatus("Active");
        resumeDTO5.setUserLogin("Andrey21CM");
        resumeDTO5.setSubject("Programming");

        resumeDTOS.add(resumeDTO1);
        resumeDTOS.add(resumeDTO2);
        resumeDTOS.add(resumeDTO3);
        resumeDTOS.add(resumeDTO4);
        resumeDTOS.add(resumeDTO5);

        return resumeDTOS;
    }


    public void addResumesToLocalDB(List<ResumeDTO> resumeDTOS) {
        Observable.fromCallable(() -> localDB.resumeDAO().insertAll(resumeDTOS))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new TasksFragment.DefaultSubscriber<List<Long>>(){
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        super.onSubscribe(d);
                    }

                    @Override
                    public void onNext(@NonNull List<Long> longs) {
                        super.onNext(longs);
                        Timber.d("insert countries transaction complete");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        super.onError(e);
                        Timber.d("error storing countries in db"+e);
                    }

                    @Override
                    public void onComplete() {
                        Timber.d("insert countries transaction complete");
                    }
                });
    }

    @Override
    public void findViewById() {
        fab = view.findViewById(R.id.fragment_resumes_floatingActionButton2);
        recyclerView = view.findViewById(R.id.fragment_resumes_recycleView);
        search = view.findViewById(R.id.fragment_resumes_search);
        mSwipeRefreshLayout = view.findViewById(R.id.fragment_resumes_swipeRefresh);
    }

    @Override
    public void onListener() {

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            getResumesFromServer();
            mSwipeRefreshLayout.setRefreshing(false);
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

        search.setOnClickListener(v -> startActivity(
                new Intent(getContext(), SearchResumeAlertDialog.class)));
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

    @SuppressLint("CheckResult")
    private void getResumesFromServer(){
        apiService.getAllResumes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<ResumeDTO>>() {
                    @Override
                    public void onNext(List<ResumeDTO> resumeDTOS) {
                        data.addAll(resumeDTOS);
                        addResumesToLocalDB(data);
                        resumeListAdapter.notifyDataSetChanged();
                        Log.d("Resume size from server", String.valueOf(resumeDTOS.size()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Error",e.getMessage());

                    }

                    @Override
                    public void onComplete() {
                        // Received all notes
                    }
                });
    }


    private void getResumesFromLocalDB(){
        resumeDAO.getAllResumes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resumeDTOS -> {
                    Log.d("Resumes size in LocalDB", String.valueOf(resumeDTOS.size()));
                    data = resumeDTOS;
                    resumeListAdapter = new ResumeListAdapter(data, imageUrls, recyclerView, getActivity());
                    recyclerView.setAdapter(resumeListAdapter);
                });
    }

    public static ResumesFragment newInstance() {
        return new ResumesFragment();
    }

}
