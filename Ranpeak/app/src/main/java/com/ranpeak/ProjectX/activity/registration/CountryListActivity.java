package com.ranpeak.ProjectX.activity.registration;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.constant.Constants;

public class CountryListActivity extends DialogFragment {
    private SearchView searchView;
    private ListView listView;
    private ArrayAdapter<String> adapter;
//    public OnInputListener onInputListener;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.activity_country_list, null);

        getDialog().setTitle("choose country");

        // listView properties
        listView = rootView.findViewById(R.id.country_list);
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,
                Constants.Values.COUNTRIES);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedFromList =(listView.getItemAtPosition(position).toString());

                // not best choice how to set country but it's working good
                if(!selectedFromList.equals("")){
                    ((RegistrationActivity2) getActivity()).setCountry(selectedFromList);
                }
                getDialog().dismiss();

                // best method
//                onInputListener.sendInput(selectedFromList);
//                getDialog().dismiss();
            }
        });

        // search view properties
        searchView = rootView.findViewById(R.id.search_countries);
        searchView.setQueryHint("Search...");
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

        return rootView;
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        try{
//            onInputListener = (OnInputListener) getActivity();
//        }catch (ClassCastException e){
//            // code that will handle exception
//        }
//    }
//
//    public interface OnInputListener{
//        void sendInput(String input);
//    }
}
