package com.company.Helance.activity.splashscreen.viewModel;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.company.Helance.interfaces.navigators.SplashNavigator;
import com.company.Helance.base.BaseViewModel;
import com.company.Helance.networking.volley.Constants;
import com.company.Helance.networking.volley.RequestHandler;

public class SplashViewModel extends BaseViewModel<SplashNavigator> {

    private Context context;

    public SplashViewModel(Context context) {
        this.context = context;
    }

    public void getAllTask() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL.GET_ALL_TASK,
                response -> {
                    getNavigator().completeLoad();
                },
                error -> {
                    getNavigator().handleError(error);
                });
        RequestHandler.getmInstance(context).addToRequestQueue(stringRequest);
    }
}
