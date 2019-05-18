package com.company.Helance.activity.creating.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.company.Helance.R;
import com.company.Helance.activity.creating.creatingResume.CreatingResumeActivity;
import com.company.Helance.activity.creating.creatingTask.CreatingTaskActivity;
import com.company.Helance.interfaces.Activity;
import com.company.Helance.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.myResumeFragment.resume.MyResumeEditActivity;
import com.company.Helance.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.myTaskFragment.task.MyTaskEditActivity;

public class LessonListFragment extends DialogFragment implements Activity {

    private SearchView searchView;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private View rootView;

    public static String[] LESSONS_LIST;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        rootView = inflater.inflate(R.layout.activity_lesson_list, null);
        getDialog().setTitle(R.string.choose_lesson);

        findViewById();
        onListener();

        LESSONS_LIST = new String[]{
                getString(R.string.art),
                getString(R.string.maths),
                getString(R.string.physics),
                getString(R.string.programming),
                getString(R.string.economics),
                getString(R.string.phylosophy),
                getString(R.string.english),
                getString(R.string.history),
                getString(R.string.other)
        };

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,
                LESSONS_LIST);
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
                } else if (getActivity().getClass().getSimpleName().equals(MyResumeEditActivity.class.getSimpleName())) {
                    ((MyResumeEditActivity) getActivity()).setLessonPicker(selectedFromList);
                } else if (getActivity().getClass().getSimpleName().equals(MyTaskEditActivity.class.getSimpleName())) {
                    ((MyTaskEditActivity) getActivity()).setLessonPicker(selectedFromList);
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
