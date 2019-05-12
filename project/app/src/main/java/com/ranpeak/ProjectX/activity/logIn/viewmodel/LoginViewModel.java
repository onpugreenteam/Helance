package com.ranpeak.ProjectX.activity.logIn.viewmodel;

import android.content.Context;
import android.databinding.ObservableInt;
import android.util.Log;
import android.view.View;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.ranpeak.ProjectX.activity.lobby.DefaultSubscriber;
import com.ranpeak.ProjectX.activity.logIn.commands.LoginNavigator;
import com.ranpeak.ProjectX.base.BaseViewModel;
import com.ranpeak.ProjectX.dataBase.local.LocalDB;
import com.ranpeak.ProjectX.dataBase.local.dao.NetworkDAO;
import com.ranpeak.ProjectX.dto.SocialNetworkDTO;
import com.ranpeak.ProjectX.networking.retrofit.ApiService;
import com.ranpeak.ProjectX.networking.retrofit.RetrofitClient;
import com.ranpeak.ProjectX.networking.volley.Constants;
import com.ranpeak.ProjectX.networking.volley.RequestHandler;
import com.ranpeak.ProjectX.settings.SharedPrefManager;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends BaseViewModel<LoginNavigator> {

    private Context context;
    private ObservableInt textViewError;
    private LocalDB localDB;
    private NetworkDAO networkDAO;

    private ApiService apiService = RetrofitClient.getInstance()
            .create(ApiService.class);

    public LoginViewModel(Context context) {
        this.context = context;
        textViewError = new ObservableInt(View.GONE);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ObservableInt getTextViewError() {
        return textViewError;
    }

    // Запрос на аунтификацию по (логину или почте) с паролем
    public void sendLoginRequest(String log, String pass, Context context){
        final String login = log;
        final String password = pass;

        setIsLoading(true);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL.LOGIN_USER,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.d("Name",jsonObject.getString("name"));
                        Log.d("Login",jsonObject.getString("login"));
                        Log.d("Email",jsonObject.getString("email"));
                        Log.d("Active", String.valueOf(jsonObject.getBoolean("active")));
                        // если аккаунт не активирован, то открывается активность где надо ввести код
                        if ((jsonObject.getString("login").equals(login)
                                || jsonObject.getString("email").equals(login))
                                && !jsonObject.getBoolean("active")) {

                            setIsLoading(false);
                            getNavigator().openRegistrationActivity(
                                    jsonObject.getString("login"),
                                    jsonObject.getString("name"),
                                    jsonObject.getString("email"),
                                    jsonObject.getString("country")
                            );
                        } else if ((jsonObject.getString("login").equals(login)
                                || jsonObject.getString("email").equals(login))
                                && jsonObject.getBoolean("active")) {

                            SharedPrefManager.getInstance(context)
                                    .userLogin(
                                            jsonObject.getString("login"),
                                            jsonObject.getString("name"),
                                            jsonObject.getString("email"),
                                            jsonObject.getString("country"),
                                            jsonObject.getString("avatar"),
                                            jsonObject.getString("telephone")
                                    );
                            getUserNetworks(jsonObject.getString("login"));
                            setIsLoading(false);
                            getNavigator().openLobbyActivity();
                        } else if (jsonObject.getString("message").equals("error")) {
//                            mEmailView.getText().clear();
//                            mPasswordView.getText().clear();
                            setIsLoading(false);
                            textViewError.set(View.VISIBLE);
                        }
                    } catch (JSONException e) {
                        Log.d("ERROR",e.getMessage());
                        e.printStackTrace();
                        setIsLoading(false);
                        getNavigator().handleError(e);
                    }
                },
                error -> {
                    setIsLoading(false);
                    getNavigator().handleError(error);
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("login", login);
                params.put("password", password);
                return params;
            }
        };
        RequestHandler.getmInstance(context).addToRequestQueue(stringRequest);
    }

    public Context getContext() {
        return context;
    }

    public void onLoginClick() {
        getNavigator().loginClicked();
    }

    private void getUserNetworks(String userLogin){
        apiService.getAllUserNetworks(userLogin)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<List<SocialNetworkDTO>>() {
                    @Override
                    public void onNext(List<SocialNetworkDTO> socialNetworkDTOS) {
                        addNetworksToLocalDB(socialNetworkDTOS);
                        Log.d("Network taken", String.valueOf(socialNetworkDTOS.size()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("ERROR upload networks",e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void addNetworksToLocalDB(List<SocialNetworkDTO> socialNetworkDTOS) {
        if(socialNetworkDTOS!=null) {
            Observable.fromCallable(() -> localDB.networkDAO().insertNetworks(socialNetworkDTOS))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DefaultSubscriber<List<Long>>() {
                        @Override
                        public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                            super.onSubscribe(d);
                        }

                        @Override
                        public void onNext(@io.reactivex.annotations.NonNull List<Long> longs) {
                            super.onNext(longs);
                            Log.d("AddNetwork", "insert countries transaction complete");
                        }

                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                            Log.d("AddNetwork", "error storing countries in db" + e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                            Log.d("AddNetwork", "insert countries transaction complete");
                        }
                    });
        }
    }

}
