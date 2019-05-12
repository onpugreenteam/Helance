package com.ranpeak.ProjectX.activity.creating.viewModel;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.ranpeak.ProjectX.activity.creating.commands.CreatingTaskNavigator;
import com.ranpeak.ProjectX.base.BaseViewModel;
import com.ranpeak.ProjectX.networking.volley.Constants;
import com.ranpeak.ProjectX.networking.volley.RequestHandler;
import com.ranpeak.ProjectX.settings.SharedPrefManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CreatingTaskViewModel extends BaseViewModel<CreatingTaskNavigator> {

    private Context context;

    public CreatingTaskViewModel(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void postTask(String headline,String descrpiption,String dateEnd,String subject,float price) {

        final String status = "Active";

        DateFormat df = new SimpleDateFormat("d MMM yyyy");
        final String dateStart = df.format(Calendar.getInstance().getTime());
        final int views = 0;

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL.ADD_TASK,
                response -> {
                         getNavigator().onComplete();
                },
                error -> getNavigator().handleError(error)) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("headLine", headline);
                params.put("description", descrpiption);
                params.put("dateStart", dateStart);
                params.put("deadline", dateEnd);
                params.put("user", String.valueOf(SharedPrefManager.getInstance(context).getUserLogin()));
                params.put("subject", subject);
                params.put("price", String.valueOf(price));
                params.put("status", status);
                params.put("views", String.valueOf(views));
                return params;
            }
        };
        RequestHandler.getmInstance(context).addToRequestQueue(stringRequest);
    }
}
