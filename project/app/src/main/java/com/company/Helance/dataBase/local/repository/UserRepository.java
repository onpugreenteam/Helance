package com.company.Helance.dataBase.local.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.company.Helance.dataBase.App;
import com.company.Helance.dataBase.local.LocalDB;
import com.company.Helance.dataBase.local.dao.UserDAO;
import com.company.Helance.dto.SocialNetworkDTO;
import com.company.Helance.dto.pojo.SocialNetworkPOJO;
import com.company.Helance.networking.retrofit.ApiService;
import com.company.Helance.networking.retrofit.RetrofitClient;
import com.company.Helance.networking.volley.Constants;
import com.company.Helance.networking.volley.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Observable;
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

    public boolean register(String login, String email, String name,
                            String password, String country, String phone,
                            List<SocialNetworkPOJO> list) {
        new RegisterAsyncTask(list).execute(login,
                email, name,password, country, phone);
        return registered;
    }

    public void register(String login, String email, String name, String password, String country, String phone) {
        new RegisterAsyncTask().execute(login,
                email, name,password, country, phone);
    }

    public void register(OnRegistrationUserFinished listener, String login, String email, String name, String password, String country, String phone) {
        new RegisterAsyncTask(listener).execute(login,
                email, name,password, country, phone);
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

    public interface OnRegistrationUserFinished {
        void addSocialNetwork();
    }

    public class RegisterAsyncTask extends AsyncTask<String, Void, Void> {
        private OnRegistrationUserFinished listener;
        private List<SocialNetworkPOJO> list;

        RegisterAsyncTask(OnRegistrationUserFinished listener) {
            this.listener = listener;
        }

        public RegisterAsyncTask(List<SocialNetworkPOJO> list) {
            this.list = list;
        }

        public RegisterAsyncTask() {
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
                    params.put("password", strings[3]);
                    params.put("country", strings[4]);
                    params.put("telephone", strings[5]);
                    return params;
                }
            };
            RequestHandler.getmInstance(application).addToRequestQueue(stringRequest);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(listener!=null) {
                listener.addSocialNetwork();
            }
            if(list!=null) {
                // добавляет соцсети пользователя через 4 секунды,
                // чтоб сервер мог успеть его зарегистрировать
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for(int i = 0; i < list.size(); i++) {
                            new AddNetworkAsyncTask(list.get(i)).execute();
                        }
//                        new AddNetworkAsyncTaskList(list).execute();
                    }
                }, 4000);
            }
        }
    }

    public class AddNetworkAsyncTask extends AsyncTask<String, Void, Void> {
        private SocialNetworkPOJO item;

        AddNetworkAsyncTask() {
        }

        AddNetworkAsyncTask(SocialNetworkPOJO item) {
            this.item = item;
        }

        @Override
        protected Void doInBackground(String... strings) {
            Call<SocialNetworkPOJO> call = apiService.addUserNetwork(
                    new SocialNetworkPOJO(
                            (int) (1000 * Math.random()) + 1,
                            item.getNetworkName(),
                            item.getNetworkLogin(),
                            item.getUser()
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
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
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
