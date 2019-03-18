package com.ranpeak.ProjectX.activity.lobby.navigationFragment.mainNavFragment.tabFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.lobby.navigationFragment.mainNavFragment.adapter.TaskListAdapter;
import com.ranpeak.ProjectX.dto.TaskDTO;
import com.ranpeak.ProjectX.fragment.AbstractTabFragment;

import java.util.ArrayList;
import java.util.List;

public class FreeTaskFragment extends AbstractTabFragment {

    private static final int LAYOUT = R.layout.free_task_fragment;

    private List<TaskDTO> data;
    private ArrayList<String> imageUrls = new ArrayList<>();

    private TaskListAdapter adapter;

    public static FreeTaskFragment getInstance(Context context, List<TaskDTO> data) {
        Bundle args = new Bundle();
        FreeTaskFragment fragment = new FreeTaskFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setData(data);
        fragment.setTitle(context.getString(R.string.tab_item_free_task));

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);
        initImageBitmaps();
        RecyclerView rv = view.findViewById(R.id.recycleView);
        rv.setLayoutManager(new LinearLayoutManager(context));

        adapter = new TaskListAdapter(data,imageUrls,context);
        rv.setAdapter(adapter);

        return view;
    }

    public void refreshList(List<TaskDTO> data) {
        adapter.setData(data);
        adapter.notifyDataSetChanged();
    }


    public void setContext(Context context) {
        this.context = context;
    }


    public void setData(List<TaskDTO> data) {
        this.data = data;
    }

    private void initImageBitmaps(){
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
}