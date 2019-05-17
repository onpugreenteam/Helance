package com.company.Helance.activity.creating.viewModel;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.company.Helance.activity.creating.commands.CreatingResumeNavigator;
import com.company.Helance.base.BaseViewModel;
import com.company.Helance.networking.volley.Constants;
import com.company.Helance.networking.volley.RequestHandler;
import com.company.Helance.settings.SharedPrefManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CreatingResumeViewModel extends BaseViewModel<CreatingResumeNavigator> {

    private Context context;

    public CreatingResumeViewModel(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void postResume(String text, String typeLesson) {

        final DateFormat df = new SimpleDateFormat("d MMM yyyy");
        final String dateStart = df.format(Calendar.getInstance().getTime());
        final String status = "Active";
        final int views = 0;

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL.ADD_RESUME,
                response -> {
                      getNavigator().onComplete();
                },
                error -> getNavigator().handleError(error)) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("opportunities", text);
                params.put("dateStart", dateStart);
                params.put("users", String.valueOf(SharedPrefManager.getInstance(context).getUserLogin()));
                params.put("subject", typeLesson);
                params.put("status", status);
                params.put("views", String.valueOf(views));
                return params;
            }
        };
        RequestHandler.getmInstance(context).addToRequestQueue(stringRequest);
    }
}
