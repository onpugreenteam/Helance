package com.ranpeak.ProjectX.dataBase.local.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.ranpeak.ProjectX.dataBase.App;
import com.ranpeak.ProjectX.dataBase.local.LocalDB;
import com.ranpeak.ProjectX.dataBase.local.dao.UserDAO;
import com.ranpeak.ProjectX.dto.SocialNetworkDTO;
import com.ranpeak.ProjectX.dto.pojo.SocialNetworkPOJO;
import com.ranpeak.ProjectX.networking.retrofit.ApiService;
import com.ranpeak.ProjectX.networking.retrofit.RetrofitClient;
import com.ranpeak.ProjectX.networking.volley.Constants;
import com.ranpeak.ProjectX.networking.volley.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private UserDAO userDAO;
    private Application application;
    private ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
    private boolean isLoginValid = true;
    private boolean isEmailValid = true;
    private boolean isEmailOnServer = true;
    private boolean isCodeRight = false;
    private boolean registered = false;
    private LocalDB localDB;

    public UserRepository(Application application) {
        localDB = App.getInstance().getLocalDB();
        userDAO = localDB.userDAO();
        this.application = application;
    }

    public boolean checkLogin(String login) {
        new CheckLoginAsyncTask().execute(login);
        return isLoginValid;
    }

    public boolean checkEmail(String email) {
        new CheckEmailAsyncTask().execute(email);
        return isEmailValid;
    }

    // used to create new password
    public boolean checkEmailOnServer(String email) {
        new CheckEmailOnServerAsyncTask().execute(email);
        return isEmailOnServer;
    }

    public void sendCodeOnEmail(String email) {
        Completable.fromRunnable(() -> {
            Call<Void> call = apiService.sendCodeOnEmail(email);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void addNetwork(String login, String networkName, String networkLogin) {
        Completable.fromRunnable(() -> {
            Call<SocialNetworkPOJO> call = apiService.addUserNetwork(
                    new SocialNetworkPOJO(
                            (int) (1000 * Math.random()) + 1,
                            networkName,
                            networkLogin,
                            login
                    )
            );
            call.enqueue(new Callback<SocialNetworkPOJO>() {
                @Override
                public void onResponse(Call<SocialNetworkPOJO> call, Response<SocialNetworkPOJO> response) {

                }

                @Override
                public void onFailure(Call<SocialNetworkPOJO> call, Throwable t) {

                }
            });
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();

    }

    public boolean register(String login, String email, String name, String password, String country, String phone) {
        new RegisterAsyncTask().execute(login,
                email, name,password, country, phone);
        return registered;
    }

    public boolean checkCode(String email, String code) {
        new CheckCodeAsyncTask().execute(email, code);
        return isCodeRight;
    }

    public void changePassword(String email, String password) {
        Completable.fromRunnable(() -> {
            Call<Void> call = apiService.changePassword(email, password);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {

                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void getSocialNetworks(String userLogin){
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
                    .subscribe();
        }
    }

    private class CheckLoginAsyncTask extends AsyncTask<String, Void, Void> {
        CheckLoginAsyncTask() {
        }

        @Override
        protected Void doInBackground(String... strings) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    Constants.URL.CHECK_LOGIN,
                    response -> {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("message").equals("no")) {
                                Toast.makeText(application, "This login already registered", Toast.LENGTH_LONG).show();
                                setLoginValid(false);

                            } else if (jsonObject.getString("message").equals("ok")) {
                                setLoginValid(true);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> Toast.makeText(application, "Please on Internet", Toast.LENGTH_LONG).show()) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("login", strings[0]);
                    return params;
                }
            };
            RequestHandler.getmInstance(application).addToRequestQueue(stringRequest);
            return null;
        }
    }

    private class CheckEmailAsyncTask extends AsyncTask<String, Void, Void> {
        CheckEmailAsyncTask() {
        }

        @Override
        protected Void doInBackground(String... strings) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    Constants.URL.CHECK_EMAIL,
                    response -> {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("message").equals("no")) {
                                Toast.makeText(application, "This email already registered", Toast.LENGTH_LONG).show();
                                setEmailValid(false);
                            } else if (jsonObject.getString("message").equals("ok")) {
                                setEmailValid(true);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> Toast.makeText(application, "Please turn on Internet", Toast.LENGTH_LONG).show()) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("email", strings[0]);
                    return params;
                }
            };
            RequestHandler.getmInstance(application).addToRequestQueue(stringRequest);
            return null;
        }
    }

    private class CheckEmailOnServerAsyncTask extends AsyncTask<String, Void, Void> {
        CheckEmailOnServerAsyncTask() {
        }

        @Override
        protected Void doInBackground(String... strings) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    Constants.URL.CHECK_EMAIL,
                    response -> {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("message").equals("no")) {
                                setEmailOnServer(true);
                            } else if (jsonObject.getString("message").equals("ok")) {
                                setEmailOnServer(false);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> Toast.makeText(application, "Please turn on Internet", Toast.LENGTH_LONG).show()) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("email", strings[0]);
                    return params;
                }
            };
            RequestHandler.getmInstance(application).addToRequestQueue(stringRequest);
            return null;
        }
    }

    private class RegisterAsyncTask extends AsyncTask<String, Void, Void> {
        RegisterAsyncTask() {
        }

        @Override
        protected Void doInBackground(String... strings) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    Constants.URL.POST_USER,
                    response -> {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("message").equals("Registered")) {
                                registered = true;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    },
                    error -> Toast.makeText(application, "Not registered", Toast.LENGTH_LONG).show()) {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("login", strings[0]);
                    params.put("email", strings[1]);
                    params.put("name", strings[2]);
                    params.put("country", strings[3]);
                    params.put("telephone", strings[4]);
                    params.put("password", strings[5]);
                    return params;
                }
            };
            RequestHandler.getmInstance(application).addToRequestQueue(stringRequest);
            return null;
        }
    }

    private class CheckCodeAsyncTask extends AsyncTask<String, Void, Void> {
        CheckCodeAsyncTask() {
        }

        @Override
        protected Void doInBackground(String... strings) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    Constants.URL.ACTIVATE_USER,
                    response -> {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("message").equals("ok")) {
                                setCodeRight(true);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> Toast.makeText(application, "Please on Internet", Toast.LENGTH_LONG).show()) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("email", strings[0]);
                    params.put("code", strings[1]);
                    return params;
                }
            };
            RequestHandler.getmInstance(application).addToRequestQueue(stringRequest);
            return null;
        }
    }

    private void setLoginValid(boolean loginValid) {
        isLoginValid = loginValid;
    }

    private void setEmailValid(boolean emailValid) {
        isEmailValid = emailValid;
    }

    private void setCodeRight(boolean codeRight) {
        isCodeRight = codeRight;
    }

    private void setEmailOnServer(boolean emailOnServer) {
        isEmailOnServer = emailOnServer;
    }

    private boolean isEmailOnServer() {
        return isEmailOnServer;
    }
}
