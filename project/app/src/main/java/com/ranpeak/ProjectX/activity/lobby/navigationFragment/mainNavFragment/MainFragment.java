package com.ranpeak.ProjectX.activity.lobby.navigationFragment.mainNavFragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.interfaces.Activity;
import com.ranpeak.ProjectX.activity.lobby.navigationFragment.mainNavFragment.adapter.TaskListAdapter;
import com.ranpeak.ProjectX.constant.Constants;
import com.ranpeak.ProjectX.dto.TaskDTO;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class MainFragment extends Fragment implements Activity {


    private View view;
    private List<TaskDTO> data = new ArrayList<>();
    private ArrayList<String> imageUrls = new ArrayList<>();
    private RecyclerView recyclerView;
    private TaskListAdapter adapter;

    public MainFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        findViewById();
        onListener();
        initImageBitmaps();

        new GetFreeTask().execute();

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new TaskListAdapter(data, imageUrls, recyclerView, getActivity());
        recyclerView.setAdapter(adapter);


        adapter.setLoadMore(new ILoadMore() {
            @Override
            public void onLoadMore() {
                if(data.size() <= 5){
                    data.add(null);
                    adapter.notifyItemInserted(data.size()-1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            data.remove(data.size()-1);
                            adapter.notifyItemRemoved(data.size());
                            adapter.notifyDataSetChanged();
                            adapter.setLoaded();

                        }
                    },4000);
                }else {
                    Toast.makeText(getApplicationContext(),"Completed",Toast.LENGTH_SHORT).show();
                }
            }
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
        imageUrls.add("http://cn15.nevsedoma.com.ua/photo/12/1217/300_files/devushka-s-potryasayushhim-cvetom-kozhi-stala-samoj-yark_004.jpg");
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
    public void findViewById(){
        recyclerView = view.findViewById(R.id.recycleView_main);
    }

    @Override
    public void onListener(){

    }


    public static MainFragment getInstance(List<TaskDTO> data) {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        fragment.setData(data);

        return fragment;
    }


    public static MainFragment newInstance() {
        return new MainFragment();
    }


    public void setData(List<TaskDTO> data) {
        this.data = data;
    }


    public class GetFreeTask extends AsyncTask<Void, Void, List<TaskDTO>> {

        @Override
        protected List<TaskDTO> doInBackground(Void... params) {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<List<TaskDTO>> response = restTemplate.exchange(
                    Constants.URL.GET_ALL_TASK,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<TaskDTO>>(){});
            List<TaskDTO> taskDTOS = response.getBody();

            return taskDTOS;
        }

        @Override
        protected void onPostExecute(List<TaskDTO> taskDTOS) {
            data = taskDTOS;

            adapter = new TaskListAdapter(data, imageUrls, recyclerView, getActivity());
            recyclerView.setAdapter(adapter);
        }
    }
}
