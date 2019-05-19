package com.company.Helance.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.viewModel;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.company.Helance.base.BaseViewModel;
import com.company.Helance.interfaces.navigators.ProfileNavigator;
import com.company.Helance.networking.volley.Constants;
import com.company.Helance.networking.volley.request.VolleyMultipartRequest;
import com.company.Helance.settingsApp.SharedPrefManager;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class ProfileViewModel extends BaseViewModel<ProfileNavigator> {

    private Context context;
    private RequestQueue rQueue;

    public ProfileViewModel(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    // Запрос на загрузку, на сервер...
    public void uploadImage(final Bitmap bitmap) {

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Constants.URL.UPLOAD_AVATAR,
                response -> {
                    rQueue.getCache().clear();
                    try {
                        JSONObject jsonObject = new JSONObject(new String(response.data));
                        Log.d("AvatarUrl",jsonObject.getString("message"));
                        SharedPrefManager.getInstance(getContext()).userUpdateImage(jsonObject.getString("message"));
                        getNavigator().onComplete();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error ->
                       getNavigator().handleError(error)){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("login", String.valueOf(SharedPrefManager.getInstance(getContext()).getUserLogin()));
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imageName = System.currentTimeMillis();
               params.put("file", new VolleyMultipartRequest.DataPart(imageName + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };
        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rQueue = Volley.newRequestQueue(getContext());
        rQueue.add(volleyMultipartRequest);
    }

    // Преобразует картинку пользователя в массив байтов(для передачи на сервер)
    private byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

}

