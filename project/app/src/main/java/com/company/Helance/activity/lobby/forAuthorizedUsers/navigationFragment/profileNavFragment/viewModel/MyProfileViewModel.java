package com.company.Helance.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.company.Helance.dataBase.local.repository.ProfileRepository;
import com.company.Helance.dto.SocialNetworkDTO;

import java.util.List;

import io.reactivex.Flowable;

public class MyProfileViewModel extends AndroidViewModel {
    private ProfileRepository repository;

    public MyProfileViewModel(@NonNull Application application) {
        super(application);
        repository = new ProfileRepository();
    }

    // update list of networks on server and return new list of net works
    public Flowable<List<SocialNetworkDTO>> getAllSocialNetworks(String userLogin) {
        return repository.getAllSocialNetworks(userLogin);
    }

    // only returns network from local db
    public Flowable<List<SocialNetworkDTO>> receiveNetworks(String userLogin) {
        return repository.receiveSocialNetworks(userLogin);
    }

    public void deleteAllSocialNetworks() {
        repository.deleteAllSocialNetworks();
    }

    public void updateUserInfo(String login, String name, String country, String email, String telephone) {
        repository.updateUserInfo(login,
                name, country, email, telephone);
    }

    public void updateNetworks(String userLogin, String networkName, String networkLogin) {
        repository.updateNetworks(userLogin, networkName, networkLogin);
    }

    public void addUserSocialNetwork(SocialNetworkDTO socialNetworkDTO) {
        repository.addUserSocialNetwork(socialNetworkDTO);
    }

    public void deleteNetwork(String socialNetworkPOJO, String login) {
        repository.deleteNetwork(socialNetworkPOJO, login);
    }
}
