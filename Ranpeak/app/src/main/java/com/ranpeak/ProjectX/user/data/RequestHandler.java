package com.ranpeak.ProjectX.user.data;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/** Этот класс отвечает за обработку запросов к серверу **/
public class RequestHandler {

    private static RequestHandler mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;


    public RequestHandler(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }


    public static synchronized RequestHandler getmInstance(Context context) {
        if(mInstance == null){
            mInstance = new RequestHandler(context);
        }
        return mInstance;
    }


    public RequestQueue getRequestQueue() {
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }


    public <T> void addToRequestQueue(Request<T> request){
        getRequestQueue().add(request);
    }
}
