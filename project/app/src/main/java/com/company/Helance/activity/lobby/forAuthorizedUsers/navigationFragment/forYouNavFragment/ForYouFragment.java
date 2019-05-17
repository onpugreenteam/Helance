package com.company.Helance.activity.lobby.forAuthorizedUsers.navigationFragment.forYouNavFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import com.company.Helance.R;
import com.company.Helance.activity.interfaces.Activity;
import com.company.Helance.activity.lobby.forAuthorizedUsers.navigationFragment.forYouNavFragment.adapter.ForYouListAdapter;
import com.company.Helance.dataBase.App;
import com.company.Helance.dataBase.local.LocalDB;
import com.company.Helance.dataBase.local.dao.TaskDAO;
import com.company.Helance.dto.TaskDTO;

public class ForYouFragment extends Fragment implements Activity {

    private View view;
    private LocalDB localDB;
    private TaskDAO taskDAO;
    private List<TaskDTO> data = new ArrayList<>();
    private ArrayList<String> imageUrls = new ArrayList<>();
    private RecyclerView recyclerView;
    private ForYouListAdapter adapter;
    final String subject = "Maths";
    private ImageView search;
    private ProgressBar progressDialog;

    public ForYouFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_for_you, container, false);
        findViewById();
        onListener();
        progressDialog = new ProgressBar(getContext());

        initImageBitmaps();

        localDB = App.getInstance().getLocalDB();
        taskDAO = localDB.taskDao();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ForYouListAdapter(data, imageUrls, recyclerView, getActivity());
        recyclerView.setAdapter(adapter);



        taskDAO.getAllTasksForYou(subject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(taskDTOS -> {
                    data = taskDTOS;
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new ForYouListAdapter(data, imageUrls, recyclerView, getActivity());
                    recyclerView.setAdapter(adapter);
                    Log.d("Data size ForYou", String.valueOf(taskDTOS.size()));
                });

        return view;
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
    public void findViewById() {
        recyclerView = view.findViewById(R.id.fragment_for_you_recycleView);
        search = view.findViewById(R.id.fragment_for_you_search);
    }

    @Override
    public void onListener() {
//        search.setOnClickListener(v -> startActivity(
//                new Intent(getContext(), SearchTaskAlertDialog.class)));

    }

    public static ForYouFragment newInstance() {
        return new ForYouFragment();
    }

}
