package com.ranpeak.ProjectX.activity.splashscreen.viewModel;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.ranpeak.ProjectX.activity.splashscreen.commands.SplashNavigator;
import com.ranpeak.ProjectX.base.BaseViewModel;
import com.ranpeak.ProjectX.networking.volley.Constants;
import com.ranpeak.ProjectX.networking.volley.RequestHandler;

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
