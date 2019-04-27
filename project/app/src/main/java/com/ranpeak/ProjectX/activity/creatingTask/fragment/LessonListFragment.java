package com.ranpeak.ProjectX.activity.creatingTask.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.creatingResume.CreatingResumeActivity;
import com.ranpeak.ProjectX.activity.creatingTask.CreatingTaskActivity;
import com.ranpeak.ProjectX.activity.interfaces.Activity;
import com.ranpeak.ProjectX.networking.volley.Constants;

public class LessonListFragment extends DialogFragment implements Activity {

    private SearchView searchView;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private View rootView;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        rootView = inflater.inflate(R.layout.activity_lesson_list, null);
        getDialog().setTitle(R.string.choose_lesson);

        findViewById();
        onListener();

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,
                Constants.Values.LESSONS);
        listView.setAdapter(adapter);

        // search view properties
        searchView.setQueryHint("Search...");

        return rootView;
    }

    @Override
    public void onListener(){
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedFromList =(listView.getItemAtPosition(position).toString());
            // not best choice how to set country but it's working good
            if(!selectedFromList.equals("")){
                if (getActivity().getClass().getSimpleName().equals(CreatingTaskActivity.class.getSimpleName())) {
                    ((CreatingTaskActivity) getActivity()).setLessonPicker(selectedFromList);
                } else if (getActivity().getClass().getSimpleName().equals(CreatingResumeActivity.class.getSimpleName())) {
                    ((CreatingResumeActivity) getActivity()).setLessonPicker(selectedFromList);
                }
            }
            getDialog().dismiss();
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String txt) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String txt) {
                adapter.getFilter().filter(txt);
                return false;
            }
        });
    }

    @Override
    public void findViewById(){
        searchView = rootView.findViewById(R.id.search_lessons);
        listView = rootView.findViewById(R.id.lesson_list);
    }
}
