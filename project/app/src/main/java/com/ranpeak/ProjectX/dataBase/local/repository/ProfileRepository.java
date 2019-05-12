package com.ranpeak.ProjectX.dataBase.local.repository;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.databinding.ObservableList;
import android.util.Log;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.dataBase.App;
import com.ranpeak.ProjectX.dataBase.local.LocalDB;
import com.ranpeak.ProjectX.dataBase.local.dao.ProfileDAO;
import com.ranpeak.ProjectX.dto.SocialNetworkDTO;
import com.ranpeak.ProjectX.dto.pojo.SocialNetworkPOJO;
import com.ranpeak.ProjectX.dto.pojo.UserPOJO;
import com.ranpeak.ProjectX.networking.retrofit.ApiService;
import com.ranpeak.ProjectX.networking.retrofit.RetrofitClient;
import com.ranpeak.ProjectX.settings.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileRepository {

    private ProfileDAO profileDAO;
    private ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
    private Context context;

    public ProfileRepository() {
        LocalDB database = App.getInstance().getLocalDB();
        profileDAO = database.profileDAO();
    }

    @SuppressLint("CheckResult")
    public Flowable<List<SocialNetworkDTO>> getAllSocialNetworks(String userLogin) {
        apiService.getAllUserNetworks(userLogin)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<SocialNetworkDTO>>() {
                    @Override
                    public void onNext(List<SocialNetworkDTO> socialNetworkDTOS) {
                        refreshAllUsersNetworks(socialNetworkDTOS);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return profileDAO.getAllSocialNetworks();
    }

    @SuppressLint("CheckResult")
    public Flowable<List<SocialNetworkDTO>> receiveSocialNetworks(String userLogin) {
        return profileDAO.getAllSocialNetworks();
    }

    // used to contact user
    @SuppressLint("CheckResult")
    public Observable<List<SocialNetworkDTO>> getUserNetworks(String userLogin) {
        return apiService.getAllUserNetworks(userLogin);
//        return socialNetworkDTOSList;
    }

    public void addUserSocialNetwork(SocialNetworkDTO socialNetworkDTO) {
        Completable.fromRunnable(() ->
                profileDAO.insert(socialNetworkDTO))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
        Call<SocialNetworkPOJO> call = apiService.addUserNetwork(
                new SocialNetworkPOJO(
                        socialNetworkDTO.getIdNetwork(),
                        socialNetworkDTO.getNetworkName(),
                        socialNetworkDTO.getNetworkLogin(),
                        socialNetworkDTO.getUserLogin()
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
    }

    private void refreshAllUsersNetworks(List<SocialNetworkDTO> socialNetworkDTOS) {
        Completable.fromRunnable(() -> {
            profileDAO.deleteAllSocialNetworks();
            profileDAO.insertAllSocialNetworks(socialNetworkDTOS);
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void deleteAllSocialNetworks() {
        Completable.fromRunnable(() -> {
            profileDAO.deleteAllSocialNetworks();
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void updateUserInfo(String login, String name, String country, String email, String telephone) {
        Call<UserPOJO> call = apiService.updateUserInfo(login,
                name, country, email, telephone);
        call.enqueue(new Callback<UserPOJO>() {
            @Override
            public void onResponse(Call<UserPOJO> call, Response<UserPOJO> response) {

            }

            @Override
            public void onFailure(Call<UserPOJO> call, Throwable t) {

            }
        });
    }

    public void updateNetworks(String userLogin, String networkName, String networkLogin) {
        Call<SocialNetworkPOJO> call = apiService.updateSocialNetwork(
                userLogin, networkName, networkLogin
        );
        call.enqueue(new Callback<SocialNetworkPOJO>() {
            @Override
            public void onResponse(Call<SocialNetworkPOJO> call, Response<SocialNetworkPOJO> response) {

            }

            @Override
            public void onFailure(Call<SocialNetworkPOJO> call, Throwable t) {

            }
        });
    }

    public void deleteNetwork(String socialNetwork, String login) {
        Call<SocialNetworkPOJO> call = apiService.deleteSocialNetwork(socialNetwork, login);
        call.enqueue(new Callback<SocialNetworkPOJO>() {
            @Override
            public void onResponse(Call<SocialNetworkPOJO> call, Response<SocialNetworkPOJO> response) {

            }

            @Override
            public void onFailure(Call<SocialNetworkPOJO> call, Throwable t) {

            }
        });
    }
}
