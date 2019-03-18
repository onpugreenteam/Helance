package com.ranpeak.ProjectX.activity.lobby.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.lobby.adapter.TaskListAdapter;
import com.ranpeak.ProjectX.dto.TaskDTO;
import com.ranpeak.ProjectX.fragment.AbstractTabFragment;

import java.util.List;

public class FreeTaskFragment extends AbstractTabFragment {

    private static final int LAYOUT = R.layout.free_task_fragment;

    private List<TaskDTO> data;

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

        RecyclerView rv = view.findViewById(R.id.recycleView);
        rv.setLayoutManager(new LinearLayoutManager(context));

        adapter = new TaskListAdapter(data);
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
}