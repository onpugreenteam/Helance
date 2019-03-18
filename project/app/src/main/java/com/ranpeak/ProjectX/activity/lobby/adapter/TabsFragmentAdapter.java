package com.ranpeak.ProjectX.activity.lobby.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ranpeak.ProjectX.activity.lobby.navigationFragment.mainNavFragment.tabFragment.FreeTaskFragment;
import com.ranpeak.ProjectX.activity.lobby.navigationFragment.mainNavFragment.tabFragment.ReadyAnswerFragment;
import com.ranpeak.ProjectX.dto.TaskDTO;
import com.ranpeak.ProjectX.fragment.AbstractTabFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabsFragmentAdapter extends FragmentPagerAdapter {

    private Map<Integer, AbstractTabFragment> tabs;
    private Context context;

    private List<TaskDTO> data;

    private FreeTaskFragment freeTaskFragment;

    public TabsFragmentAdapter(Context context, FragmentManager fm, List<TaskDTO> data) {
        super(fm);
        this.data = data;
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
        freeTaskFragment = FreeTaskFragment.getInstance(context, data);
        tabs.put(0, freeTaskFragment);
        tabs.put(1, ReadyAnswerFragment.getInstance(context));

    }

    public void setData(List<TaskDTO> data) {
        this.data = data;
        freeTaskFragment.refreshList(data);
    }
}