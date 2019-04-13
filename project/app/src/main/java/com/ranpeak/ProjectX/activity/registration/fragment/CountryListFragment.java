package com.ranpeak.ProjectX.activity.registration.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.interfaces.Activity;
import com.ranpeak.ProjectX.activity.registration.RegistrationActivity1;
import com.ranpeak.ProjectX.constant.Constants;

public class CountryListFragment extends DialogFragment implements Activity {

    private static final int COUNTRY_LIST_FRAGMENT = R.layout.activity_country_list;

    private SearchView searchView;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private View rootView;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        rootView = inflater.inflate(COUNTRY_LIST_FRAGMENT, null);

        findViewById();
        onListener();




        getDialog().setTitle("choose country");

        // listView properties
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,
                Constants.Values.COUNTRIES);
        listView.setAdapter(adapter);


        // search view properties

        searchView.setQueryHint("Search...");


        return rootView;
    }

    @Override
    public void findViewById() {
        listView = rootView.findViewById(R.id.country_list);
        searchView = rootView.findViewById(R.id.search_countries);
    }

    @Override
    public void onListener() {
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedFromList =(listView.getItemAtPosition(position).toString());

            // not best choice how to set country but it's working good
            if(!selectedFromList.equals("")){
                ((RegistrationActivity1) getActivity()).setCountry(selectedFromList);
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
}
