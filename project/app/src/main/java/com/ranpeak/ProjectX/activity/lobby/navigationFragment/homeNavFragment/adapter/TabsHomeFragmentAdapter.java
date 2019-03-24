package com.ranpeak.ProjectX.activity.lobby.navigationFragment.homeNavFragment.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ranpeak.ProjectX.activity.lobby.navigationFragment.homeNavFragment.tabFragment.GaveTaskFragment;
import com.ranpeak.ProjectX.activity.lobby.navigationFragment.homeNavFragment.tabFragment.TookTaskFragment;
import com.ranpeak.ProjectX.activity.lobby.navigationFragment.homeNavFragment.tabFragment.ReadyAnswerFragment;
import com.ranpeak.ProjectX.dto.TaskDTO;
import com.ranpeak.ProjectX.fragment.AbstractTabFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabsHomeFragmentAdapter extends FragmentPagerAdapter {

    private Map<Integer, AbstractTabFragment> tabs;
    private Context context;
    private ArrayList<String> images = new ArrayList<>();

    private List<TaskDTO> data;

    private GaveTaskFragment gaveTaskFragment;

    public TabsHomeFragmentAdapter(Context context, FragmentManager fm, List<TaskDTO> data, ArrayList<String> images) {
        super(fm);
        this.data = data;
        this.images = images;
        this.context = context;
        initTabsMap(context);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position).getTitle();
    }

    @Override
    public Fragment getItem(int position) {
        return tabs.get(position);
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    private void initTabsMap(Context context) {
        tabs = new HashMap<>();
        gaveTaskFragment = GaveTaskFragment.getInstance(context, data);
        tabs.put(0, TookTaskFragment.getInstance(context));
        tabs.put(1, gaveTaskFragment);
        tabs.put(2, ReadyAnswerFragment.getInstance(context));

    }

    public void setData(List<TaskDTO> data) {
        this.data = data;
        gaveTaskFragment.refreshList(data);
    }
}